package checkout

import java.util.logging.Logger

import checkout.api._

import scala.io.StdIn._

/**
  * Created by daniellegourgey1 on 21/01/2018.
  */



class CheckoutService extends api.CheckoutService {

  def calculateItemsPriceByUnit(number: Int, rule: SKU): Float = {
    rule.specialPrice match {
      case Some(v) => if(number >= v.unit) {
        //how many at special price eg if special price for 3 items and there are 8 numbers you should get 8/3
        val remainder = number % v.unit
        val multiplier = (number-remainder)/v.unit
        //val left = number - v.unit
        (v.price * multiplier) + (remainder * rule.price)
      } else number * rule.price
      case None => number * rule.price
    }
  }

  //calculates current cost of items checked out
  def calculateMap(items: Map[String, Int], rules: List[SKU]): Float = {
    println(s"calculating map $items")
    items.toList.map(item => {
      val rule = rules.filter(_.unit == item._1 ).head
      println(s"point ${item._2}, ${rule.price}")
      calculateItemsPriceByUnit(item._2, rule)
    }).sum

  }

  //@todo deal with exceptions if incorrect input

  def askQuestion: String =  {
    println("add an SKU? y/n")
    readLine()
  }

  //increments map value for every item checked out
 def processScan(item: String,
                 items: Map[String, Int]
                ): Map[String, Int] = {
    if(items.exists(_._1 == item)) {
      val number = items(item)
      println(s"adding item $item to list")
      items + (item -> (number + 1))
    } else {
      println(s"$item does not exist please enter another")
      items
    }

  }


  //@todo deal with exceptions if incorrect input
  def checkThroughUnit(itemMap: Map[String, Int], rules: List[SKU]):Float = {
    println("enter unit e.g A, B or C otherwise type 'end' to termninate and calculate total")
    val input = readLine().toUpperCase
    input match {
      case "END" => {
        println(s"total cost: ${calculateMap(itemMap, rules)}")
        calculateMap(itemMap, rules)

      }
      case i:String => {
        val newMap: Map[String, Int] = processScan(i, itemMap)
        calculateMap(newMap, rules)
        checkThroughUnit(newMap, rules)
      }
    }

  }


  //checkout an item which then gets added to a map
  //items kept count of my updating may of unit -> numberOfItemsForUnit
  def checkOutItems(rules: List[SKU]): Float = {
    //initialise Map from SKU's that have rules attached to them
    val starterMap: Map[String, Int] = rules.map(_.unit -> 0).toMap
    checkThroughUnit(starterMap, rules)
  }



  //@todo need to deal with case if user inputs multiple rules for the same item
  def addRule(currentList: List[SKU],
              item: String,
              price: Float,
              specialPrice: Option[Float],
              specialNumber: Option[Int]
                      ): List[SKU] = {


    val newList = currentList :+ SKU(item, price, specialPrice, specialNumber)
    println(newList)
    newList
  }


  //@todo deal with exceptions if incorrect input
  def addRules(skuList: List[SKU]): List[SKU] = {

    println("Enter unit Name, it must be a single letter of the alphabet: ")
    val input = readLine().toUpperCase
    if(skuList.map(_.unit).contains(input)) {
      println("that unit already exists, please provide another")
      val input = readLine()
    }
    val unitName = input
    println("Price: ")
    val price = readFloat()
    println("Is there a special Price? y/n")
    val isThere = readLine()

    isThere match {
      case "y" => {
        println("please input the number of items needed to get the special price")
        val number = readInt()
        println(s"please input special price for $number")
        val priceBulk = readFloat()
        addRule(skuList, unitName, price, Some(priceBulk), Some(number))

      }
      case "n" => addRule(skuList, unitName, price, None, None)
    }
  }



  //prompts user to add SKU's until all rules have been added
  //upon which checkout items can be added
  override def generateCheckoutProgram(starterList: List[SKU]): Unit = {
    askQuestion match {
      case "y" => {
        val rules = addRules(starterList)
        generateCheckoutProgram(rules)
      }
      case "n" => checkOutItems(starterList)
    }

  }

}
