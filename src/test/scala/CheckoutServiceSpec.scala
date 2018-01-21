import checkout.CheckoutService
import checkout.api.SKU
import org.scalatest.FlatSpec

/**
  * Created by daniellegourgey1 on 21/01/2018.
  */
class CheckoutServiceSpec extends FlatSpec {

  val checkoutService = new CheckoutService


  "addRule" should "add An SKU rule to List" in {
    val currentList = List(SKU("A", 2.0.toFloat, Some(3.0.toFloat), Some(2)), SKU("B", 1.5.toFloat, Some(3.0.toFloat), Some(3)))
    val newRule = SKU("C", 2.0.toFloat, Some(3.0.toFloat), Some(2))

    val newList = checkoutService.addRule(currentList, newRule.unit, newRule.price, Some(3.0.toFloat), Some(2))

    assert(currentList != newList)
    assert((currentList :+ newRule) == newList)
  }


  "calculateItemsPriceByUnit" should "Calculate the price for a Unit given a rule, adjusting for special price" in {
    val number = 4
    val rule = SKU("A", 2.0.toFloat, Some(3.0.toFloat), Some(2))
    val cost = checkoutService.calculateItemsPriceByUnit(number, rule)
    assert(cost == 6.toFloat)
  }

  "calculateMap" should "Calcualte the sum of any items checked out at that point" in {
    val items = Map("A" -> 1, "B" -> 3)
    val rules = List(SKU("A", 2.0.toFloat, Some(3.0.toFloat), Some(2)), SKU("B", 1.5.toFloat, Some(3.0.toFloat), Some(3)))
    val costSoFar = checkoutService.calculateMap(items, rules)
    assert(costSoFar == 5)
  }

  "processScan" should "increment map value" in {
    val item = "A"
    val mapOfItems = Map("A" -> 1, "B" -> 2)
    val newMap = checkoutService.processScan(item, mapOfItems)
    assert(newMap(item) == 2)

  }

  "processScan" should "throw an exception if the input item does not exist in the map" in {
    val itemC = "C"
    val mapOfItems = Map("A" -> 1, "B" -> 2)
    val newMap = checkoutService.processScan(itemC, mapOfItems)
    assertThrows[NoSuchElementException] {
      newMap(itemC)
    }

  }






}
