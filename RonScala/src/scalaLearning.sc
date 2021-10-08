object scalaLearning {
  class Animal {
  def eat = println("Animals eat a lot")
  private def eat2=println("cats stink")
  protected def eat3=println("cats are clever")
  }
  class Cat extends Animal{
  def preferredMeal=println("milk")
  eat3
  }
  
  val cat = new Cat                               //> cats are clever
                                                  //| cat  : scalaLearning.Cat = scalaLearning$Cat@57829d67
  cat.eat                                         //> Animals eat a lot
  cat.preferredMeal                               //> milk
  //cat.eat2 gives error..pvt member cant be accessed
  //cat.eat3 gives error

abstract class WildAnimal{
val creatureType:String //unimplemented method and undefined values. Puropose is to inherit later in children class. Abstract class instance cant be created
def eat
def sleep = println("Animal sleep lot")
}

class Dog extends WildAnimal{
val creatureType:String="canine"
def eat:Unit=println("Dog eat bone")
}
val dog = new Dog                                 //> dog  : scalaLearning.Dog = scalaLearning$Dog$1@19dfb72a
dog.sleep                                         //> Animal sleep lot

trait carnivore {//traits are like interface in java. Interfaces are unimplemented, while traits can hv implemented methods.
def preferredMeal

}

trait coldblooded //no constructor parameters allowed

//both abstract class and traits can have implemented and unimplemented methods

class Crocodile extends WildAnimal with carnivore with coldblooded{ // multiple inheritance possible through 'with'
val creatureType:String="canine"
def eat:Unit=println("croc eats flesh")
def preferredMeal=println("croc likes seafood")
}
val croc = new Crocodile                          //> croc  : scalaLearning.Crocodile = scalaLearning$Crocodile$1@17c68925
croc.eat                                          //> croc eats flesh
croc.preferredMeal                                //> croc likes seafood



}