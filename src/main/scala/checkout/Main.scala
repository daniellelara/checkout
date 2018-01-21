package checkout

import checkout.api._


/**
  * Created by daniellegourgey1 on 21/01/2018.
  */



object Main extends App {

  val checkoutService = new CheckoutService
  //add rules
  val emptyList: List[SKU] = List[SKU]()
  checkoutService.generateCheckoutProgram(emptyList)

}

