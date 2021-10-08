import com.sun.org.apache.xalan.internal.xsltc.compiler.ForEach
import okio.Util
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.SparkSession

object Practice1 {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
 // def sum(a:Int,b:Int=5,c:Int)={a+b}
  def sum(a:Int,b:Int=5)={a+b}                    //> sum: (a: Int, b: Int)Int
  println(sum(4))                                 //> 9
  //pritln(sum(4,6))
  
  def func1(x:Int)={
   def findEven(x:Int)={
   if(x%2==0)
   println("Its even")
   else
   println("Its odd")
  }
  findEven(x)
  }                                               //> func1: (x: Int)Unit
  func1(4)                                        //> Its even
  
  val a = (x:Int)=>x*x                            //> a  : Int => Int = Practice1$$$Lambda$8/525571@4d50efb8
  a(10)                                           //> res0: Int = 100
 
  def multiParam(args:Int*):Int={
  var res = 2
  for(i<-args)
  res  = res * i
   
  res
  }                                               //> multiParam: (args: Int*)Int
  val rsult = multiParam(2,3,4,5)                 //> rsult  : Int = 240
  print(rsult)                                    //> 240
  
  
  val fruits = ("apple"->"sour","guava"->"hard","orange"->"sweet") //1 based
                                                  //> fruits  : ((String, String), (String, String), (String, String)) = ((apple,s
                                                  //| our),(guava,hard),(orange,sweet))
  println(fruits._1._2)                           //> sour
  
  val fruit = ("apple",150,"kg")                  //> fruit  : (String, Int, String) = (apple,150,kg)
  fruit.productIterator.foreach(f=> println(f))   //> apple
                                                  //| 150
                                                  //| kg
  
  val list1 =List("ravi", "rakesh", "ram")        //> list1  : List[String] = List(ravi, rakesh, ram)
  val list2 = list1.map(x=>x.reverse)             //> list2  : List[String] = List(ivar, hsekar, mar)
  for(j<-list2)println(j)                         //> ivar
                                                  //| hsekar
                                                  //| mar
  
 val list3=List(1,4,7,4,7,9)                      //> list3  : List[Int] = List(1, 4, 7, 4, 7, 9)
 
  list3.splitAt(3)                                //> res1: (List[Int], List[Int]) = (List(1, 4, 7),List(4, 7, 9))
  list3.takeRight(2)                              //> res2: List[Int] = List(7, 9)
  
  list3.filter(x=>x!=4)                           //> res3: List[Int] = List(1, 7, 7, 9)
  
  list3.size                                      //> res4: Int = 6
  list3.sorted                                    //> res5: List[Int] = List(1, 4, 4, 7, 7, 9)
  list3.product                                   //> res6: Int = 7056
  list3.contains(7)                               //> res7: Boolean = true
  
  val res = for(i<-list3 if i%2==0)yield i        //> res  : List[Int] = List(4, 4)
  println(res)                                    //> List(4, 4)
  
  val aMap = Map("A"->"Apple","B"->"banana","c"->1)
                                                  //> aMap  : scala.collection.immutable.Map[String,Any] = Map(A -> Apple, B -> b
                                                  //| anana, c -> 1)
  aMap("c")                                       //> res8: Any = 1
  
  
  aMap.contains("A")                              //> res9: Boolean = true
  val banana= util.Try(aMap("B")) getOrElse "Not there"
                                                  //> banana  : Any = banana
  println(banana)                                 //> banana
  
  aMap.count(p=>p._2=="banana")                   //> res10: Int = 1
  aMap.get("A")                                   //> res11: Option[Any] = Some(Apple)
  
   aMap.filter(p=>p._1=="B")                      //> res12: scala.collection.immutable.Map[String,Any] = Map(B -> banana)
 val bMap= Map(1->"A", 2->"B", 1->"C")            //> bMap  : scala.collection.immutable.Map[Int,String] = Map(1 -> C, 2 -> B)
  //considers latest value for a key
  
  val cMap:scala.collection.mutable.Map[Any,Any] = scala.collection.mutable.Map("A"->1, 2->"two")
                                                  //> cMap  : scala.collection.mutable.Map[Any,Any] = Map(2 -> two, A -> 1)
  
  aMap.keys.foreach(println)                      //> A
                                                  //| B
                                                  //| c
  
  aMap.keys.foreach(key => println(key))          //> A
                                                  //| B
                                                  //| c
  aMap.keys.foreach(key => println(aMap(key)))    //> Apple
                                                  //| banana
                                                  //| 1
  
  
  aMap ++ cMap                                    //> res13: scala.collection.immutable.Map[Any,Any] = Map(A -> 1, B -> banana, c
                                                  //|  -> 1, 2 -> two)
  
  
  val sq = Seq(1,2,3,4,5)                         //> sq  : Seq[Int] = List(1, 2, 3, 4, 5)
  for(i<-sq)println(i)                            //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
  
  val liist = List(2,6,3,4)                       //> liist  : List[Int] = List(2, 6, 3, 4)
  val arr = Array(2,7,5,3)                        //> arr  : Array[Int] = Array(2, 7, 5, 3)
  val vec = Vector(3,8,9,2)                       //> vec  : scala.collection.immutable.Vector[Int] = Vector(3, 8, 9, 2)
   def squared (x:Seq[Int])= {x.map(f=>f*f)}      //> squared: (x: Seq[Int])Seq[Int]
 println(squared(liist))                          //> List(4, 36, 9, 16)
 println(squared(arr))                            //> ArrayBuffer(4, 49, 25, 9)
 println(squared(vec))                            //> Vector(9, 64, 81, 4)
 println(squared(sq))                             //> List(1, 4, 9, 16, 25)
 
 sq.apply(2)                                      //> res14: Int = 3
 sq.count(x=>x!=1)                                //> res15: Int = 4
 sq.isEmpty                                       //> res16: Boolean = false
 sq.indexOf(3)                                    //> res17: Int = 2
 
 var set1 = Set(1,2,4,6)                          //> set1  : scala.collection.immutable.Set[Int] = Set(1, 2, 4, 6)
 set1+= 3
 set1 -=2
 println(set1)                                    //> Set(1, 6, 3, 4)

 //val set2 = Set(4,8,9,3)
 //set2+=5 gives error
  val set2 = collection.mutable.Set(4,8,9,3)      //> set2  : scala.collection.mutable.Set[Int] = Set(9, 3, 4, 8)
  set2+=7                                         //> res18: Practice1.set2.type = Set(9, 3, 7, 4, 8)
  set1 ++ set2                                    //> res19: scala.collection.immutable.Set[Int] = Set(1, 6, 9, 7, 3, 8, 4)
  val li = List(1,2,2,4,5,6,6,6,7,8)              //> li  : List[Int] = List(1, 2, 2, 4, 5, 6, 6, 6, 7, 8)
  val set3 = collection.mutable.Set()             //> set3  : scala.collection.mutable.Set[Nothing] = Set()
  println((set3++li).size)                        //> 7
  
  set2 ++ vec                                     //> res20: scala.collection.mutable.Set[Int] = Set(9, 2, 3, 7, 4, 8)
  set2.filter(x=>x!=5).map(x=>x*x)                //> res21: scala.collection.mutable.Set[Int] = Set(81, 9, 16, 49, 64)
  set1.union(set2)                                //> res22: scala.collection.immutable.Set[Int] = Set(1, 6, 9, 7, 3, 8, 4)
  //Sets amd maps have mutable and imutable types, not list
  import scala.collection._
  //Set by default mutable
  val set4 = mutable.Set(2,7,3)                   //> set4  : scala.collection.mutable.Set[Int] = Set(2, 3, 7)
  var set5 = immutable.Set(3,8,4,6)               //> set5  : scala.collection.immutable.Set[Int] = Set(3, 8, 4, 6)
  
  set4 +=4                                        //> res23: Practice1.set4.type = Set(2, 3, 7, 4)
  set5-=3
  
    val l = List.empty                            //> l  : List[Nothing] = List()
  l eq Nil                                        //> res24: Boolean = true
  
  
  val l2=Nil                                      //> l2  : scala.collection.immutable.Nil.type = List()
  //Nothing can't be a return type. It has no type associated
  //Option class uses Null
  
  val s1:Option[String] = Some("Tarun")           //> s1  : Option[String] = Some(Tarun)
  val s2:Option[String] = None                    //> s2  : Option[String] = None
  //val s2:String = null//gives null ptr exception
  //s1.length
  //s2.length
  
  s1.map(_.length)                                //> res25: Option[Int] = Some(5)
  s2.map(_.length)                                //> res26: Option[Int] = None
 
  val mapA = Map("A"->"AApple","B"->"Banana","C"->"Citrus")
                                                  //> mapA  : scala.collection.Map[String,String] = Map(A -> AApple, B -> Banana,
                                                  //|  C -> Citrus)
  
 mapA("D")                                        //> java.util.NoSuchElementException: key not found: D
                                                  //| 	at scala.collection.immutable.Map$Map3.apply(Map.scala:156)
                                                  //| 	at Practice1$.$anonfun$main$1(Practice1.scala:153)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$anonfun$$ex
                                                  //| ecute$1(WorksheetSupport.scala:76)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.redirected(W
                                                  //| orksheetSupport.scala:65)
                                                  //| 	at org.scalaide.worksheet.runtime.library.WorksheetSupport$.$execute(Wor
                                                  //| ksheetSupport.scala:76)
                                                  //| 	at Practice1$.main(Practice1.scala:8)
                                                  //| 	at Practice1.main(Practice1.scala)
 
 mapA.get("A")
 mapA.get("D")
 val case1 = mapA.get("B")
  
  case1 match{
  case Some(case1)=>case1
  case None=> "NA"
  }
  
  def fibonacci(n:Int):Int={
  @scala.annotation.tailrec
  def fib(n:Int,a:Int,b:Int):Int={
  n match {
  case 0 =>  0
  case 1 => 1
  case _=> println(a +" ");fib(n-1,b, a+b)
  }
 
  }
   fib(n,0,1)
  }
  fibonacci(5)
  
  
}