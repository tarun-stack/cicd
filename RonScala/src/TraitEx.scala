

class TraitEx extends Domestic1 with wild1 with waterAnimal{
  def getName() = println("Cow is domestic") 
  override def getFoodType()=println("Cow eats straw too")
   def getWildanimal()=println("Lion is wild")
   def getwaterAnimal = println("Shark is amphibian")
}

trait Domestic1{ //has option of both abs and non abs since its extended
  //abstract
  def getName()
  
  //non abstract
  def getFoodType()=println("cow eats grass") //can be overriden in traitex class too
}
trait wild1{ //mandatorily implemented in class
  def getWildanimal()
}

trait waterAnimal{
  def getwaterAnimal()
}
object mainObj1{
  def main(args:Array[String]){
    val t1= new TraitEx
    t1.getName()
    t1.getFoodType()
    t1.getWildanimal()
    t1.getwaterAnimal()
  }
}