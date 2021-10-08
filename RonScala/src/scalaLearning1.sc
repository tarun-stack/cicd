object scalaLearning1 extends App {

 class Person(name:String,age:Int)
  //class Person(val name:String,age:Int)
   val p = new Person("ron",33)
   //println(p.name)// name is not a field, its a param. Error is gone if parameter is promoted to field by adding val to param name.
   val p2 = new Person("ron",33)
   println(p2==p) //gives false as it compares references. compare with scalaLearning2}
  
  /*For now you can think of case class as a means for creating domain objects where the Scala compiler
  does the hardwork of adding your getters and setters!*/
  
  case class Mobile(name:String , company:String, model:Double)
  
  var mob = Mobile("iphone","Apple",11)
  println(s"The phone is: $mob.name")
  //println(f"The phone model is: $mob.model%.2f")
  
  def f (num:Int, f:Int=>Int):Int={f(num)}
val l : List[Int] =List(10,20,16,25,30,40,36)
var count=0
for (i<-l) if (Math.sqrt(i).ceil-Math.sqrt(i) == 0) count=count+1
println(count)



var count1=0
val array1:Array[String]= Array("5","40")//readLine
val arr1 =array1.map((x:String)=>x.toInt)
val array2:Array[String]= Array("5","7","25","90")
val arr2= array2.map(_.toInt)

for (i<- arr2)
if(i < arr1(1)) count1=count1+1
print(count)




val li:Array[Int]= Array(1,2,3,5,6,7)
val i = li.length+1
val sum = i*((i+1)/2)
val r = li.reduce(_+_)//map and reduce applied to any collection
print(s"Missing number: ${sum-r}")
  }