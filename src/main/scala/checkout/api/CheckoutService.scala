package checkout.api

/**
  * Created by daniellegourgey1 on 21/01/2018.
  */

case class SpecialPrice(unit: Int,
                        price: Float)

case class SKU(unit: String,
               price: Float,
               specialPrice: Option[SpecialPrice])

object SKU {

  def apply(unit: String,
            price: Float,
            specialPrice: Option[Float],
            specialNumber: Option[Int]): SKU = {

    val formattedSpecial = (specialNumber, specialPrice) match {
      case (Some(n), Some(p)) => Some(SpecialPrice(n, p))
      case _ => None
    }

    SKU(unit, price, formattedSpecial)
  }

  def hasSpecial(sku: SKU): Boolean = (sku.specialPrice) match {
    case Some(n) => true
    case _ => false
  }


}


trait CheckoutService {

  def generateCheckoutProgram(starterList: List[SKU]): Unit

}
