

object ScalaOOP2 {
  class Person(name:String,age:Int){//instance elevel functionality
    val x=20
    def ageDoubler = age*2
    def salaryDoubler(sal:Int)=sal*2
    
  }
val person = new Person("Ron",32) //creates new instance of class Person
println(person.x)
person.ageDoubler
person.salaryDoubler(1000)

//object in scala used to get class level functionality.
object Person{ //only once instance of name Person can be created. Class level functionality like static
  val N_eyes=2
  def canFly:Boolean=false
  
} //singleton object can extend class and trait as well
print(Person.N_eyes)//uses singleton instance of object Person

//This is companion design pattern. Both object and class can access each other's private members


}