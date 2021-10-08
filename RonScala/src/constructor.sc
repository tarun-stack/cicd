object Tutorial {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet

class Student{  //stumarks is part of default primary constructor and  will hv getter/setter.
val stumarks = 2
 private def  func (x:Int)={x*stumarks} //protected available inside sub class only
 
 class B{
 val b = new Student
 b.func(5)
 }
 
  }

val a = new Student                               //> a  : Tutorial.Student = Tutorial$Student$1@2a742aa2
 //a.func(4) //error as private def is accessible inside base class only.

class Student1(d:Int){ //only d is part of default primary constructor. Gettr/setter generated for d
val stumarks = 2
 private def  func (x:Int)={x*stumarks}
}
class Animal(name:String,types:String,location:String){
 def this()
 {
 this("sheep","mammal","grassland")
 print("called zero arg auxillary constructor")
 }
 println("primary constructor")
 def this(name:String)
 {
 this()
 print("called one arg auxillary constructor")
 }
  def this(name:String,types:String)
 {
 this("cow")
 print("called two arg auxillary constructor")
 }
 
 }
 
 val ani = new Animal()                           //> primary constructor
                                                  //| called zero arg auxillary constructorani  : Tutorial.Animal = Tutorial$Anima
                                                  //| l$1@3cb1ffe6
 
 val ani2 = new Animal("zebra")                   //> primary constructor
                                                  //| called zero arg auxillary constructorcalled one arg auxillary constructoran
                                                  //| i2  : Tutorial.Animal = Tutorial$Animal$1@3dfc5fb8
 
 
 
 
 
 
 
 } //object closed here