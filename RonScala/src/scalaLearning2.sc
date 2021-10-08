object scalaLearning2 {
  

  //class Person(name:String,age:Int)
  case class Person( name:String,age:Int)
  val p = new Person("ron",33)                    //> p  : scalaLearning2.Person = Person(ron,33)
  println(p.name)//  **parameter is automatically promoted to field without adding val when case is used.
                                                  //> ron

println(p.toString())//**case classes hv sensible toString() , println(p)gives same result.
                                                  //> Person(ron,33)
val p3 = new Person("ron",33)                     //> p3  : scalaLearning2.Person = Person(ron,33)
  println(p==p3) //**gives true as equals nd hashcode method already implemented in case class.
                                                  //> true
  
  val p4 = Person.apply("ron",33)//** have companion objects already.no need to create.
                                                  //> p4  : scalaLearning2.Person = Person(ron,33)
  println(p4)                                     //> Person(ron,33)
  val p5 = Person("ron",33)                       //> p5  : scalaLearning2.Person = Person(ron,33)
  println(p5)                                     //> Person(ron,33)
  
  val p6 = p.copy()                               //> p6  : scalaLearning2.Person = Person(ron,33)
  println(p6.age)                                 //> 33
  
  //case classes are serializable and transferrable to network.
  //used extensively in spark.
}