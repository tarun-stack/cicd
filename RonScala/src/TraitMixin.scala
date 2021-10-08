


//class TraitMixinEx extends Fruit with types1{
class TraitMixinEx extends Fruit{
  def getName()=println("apple, banana are fruits")
  def getTypes() = println("These are fruits")
}
abstract class Fruit{
  def getName()
}
trait types1{
  def getTypes()
}
object mainobj2{
  def main(args:Array[String]){
   // val t1 = new TraitMixinEx
    val t1 = new TraitMixinEx with types1 // when we want trait for a specific object, not all objects
    t1.getName()
    t1.getTypes()
  }
}