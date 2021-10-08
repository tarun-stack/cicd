

 //abstract class AbstractEx {
abstract class AbstractEx(name:String,type1:String) { //unimpl variables
  //val name = "Cow"
  //val type1 ="domestic"
  def getName()//unimplemented method
}
 
 //class Types extends AbstractEx {
 class Types(name:String,type1:String) extends AbstractEx(name,type1) {
   def getName()={
     println("The name of animal is " +name)
   }
   def getType()={
     println("The type of animal is" +type1)
   }
 }
 //mostly abs classes used where hv to pass parameters to primary constructor, else same thing is done by trait
 object mainobj{
   def main(args:Array[String])={
     val t:Types= new Types("cow","domestic")
     t.getName()
     t.getType()
   }
 }