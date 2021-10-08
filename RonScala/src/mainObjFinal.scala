

object mainObjFinal {
   def main(args:Array[String]){
   val b1 = new B
   println(b1.a)
   }
}
class A{
  final val a =120 //cant be overriden by child class
  final def getValue()=println("The number is" +a)
}

class B extends A{
  //override val a = 100
 // override def getValue()=println("Hi")
}
//final class C{
  class Z{  
}
class D extends Z{ //throws error if C is final
  
}
  
  //private is only accessible within class and to companion obj
//protected accessible in class, compnion and sub class
  
