 class ScalaOOP {
 

 //class Person(name:String,age:Int)
   class Person(val name:String,age:Int)
  val p = new Person("ron",33)
  println(p.name)// name is not a field, its a param. Error is gone if parameter is promoted to field by adding val to param name.

val p2=new Person("ron",33)
   println(p==p2) //gives false as it compares references. compare with scalaLearning2
}
 