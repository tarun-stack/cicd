


class CompanionExample {
  CompanionExample.display() //companin obj and class can share pvt members and func, uses apply method internally
  C.getName("kangaroo") //error if getName is private
}
//companion object
object CompanionExample{
  val a ="Apple"
  val b ="Orange"
  
 private  def display()={
    println(a+" "+b)
  }
}

object C {
  def getName(str1:String)={
    println(str1 + "is an animal")
  }
}

object E{
  def main(args:Array[String])={
    val ex = new CompanionExample  //no static declaration and no object creation using new () like java
  }
}