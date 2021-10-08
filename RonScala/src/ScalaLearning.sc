object ScalaLearning {
  println("Welcome to the Scala worksheet")       //> Welcome to the Scala worksheet
  def squareIt(x:Int): Int = {
  x*x //since its last statement we dont hv to explicitly ention return
}                                                 //> squareIt: (x: Int)Int

 def cubeIt(x:Int):Int = x*x*x                    //> cubeIt: (x: Int)Int
println(squareIt(4))                              //> 16


def transform(x:Int, f:Int=>Int) : Int ={ f(x) }  //> transform: (x: Int, f: Int => Int)Int
transform(2,squareIt)                             //> res0: Int = 4
transform(2,cubeIt)                               //> res1: Int = 8


val b = List(1,2,3,4)                             //> b  : List[Int] = List(1, 2, 3, 4)
for (i<-b) println(i)                             //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
b.reverse                                         //> res2: List[Int] = List(4, 3, 2, 1)

10::b                                             //> res3: List[Int] = List(10, 1, 2, 3, 4)


val x = ("tarun",1000,true)                       //> x  : (String, Int, Boolean) = (tarun,1000,true)
x._1                                              //> res4: String = tarun
x._2                                              //> res5: Int = 1000


val rng= 1 to 10                                  //> rng  : scala.collection.immutable.Range.Inclusive = Range 1 to 10
for(i<-rng) print(i)                              //> 12345678910

val rng2= 1 until 10                              //> rng2  : scala.collection.immutable.Range = Range 1 until 10

for (i <- rng2) println(i)                        //> 1
                                                  //| 2
                                                  //| 3
                                                  //| 4
                                                  //| 5
                                                  //| 6
                                                  //| 7
                                                  //| 8
                                                  //| 9

val z = Set(1,2,2,4,4,5)                          //> z  : scala.collection.immutable.Set[Int] = Set(1, 2, 4, 5)

val xy = Map(1->"ron",2->"sam")                   //> xy  : scala.collection.immutable.Map[Int,String] = Map(1 -> ron, 2 -> sam)
xy.get(2)                                         //> res6: Option[String] = Some(sam)

val xyz = Map(1->"ron",2->"sam",1->"srk")         //> xyz  : scala.collection.immutable.Map[Int,String] = Map(1 -> srk, 2 -> sam)
xyz.get(1) //first element discarded. No duplicate//> res7: Option[String] = Some(srk)

//First class funcn
def doubler(i:Int):Int = {
return 2*i

}                                                 //> doubler: (i: Int)Int
var d = doubler(_)                                //> d  : Int => Int = ScalaLearning$$$Lambda$19/221036634@4f933fd1
d(2)                                              //> res8: Int = 4

def func(i:Int,f:Int=>Int) = {
f(i)
}                                                 //> func: (i: Int, f: Int => Int)Int

func(2,doubler)                                   //> res9: Int = 4

//funcn returns another func
def func_2 = {
x:Int=>x*x
}                                                 //> func_2: => Int => Int

func_2(4)                                         //> res10: Int = 16

//func and func_2 are higher order funcns. map is also higher order

var a = 1 to 10                                   //> a  : scala.collection.immutable.Range.Inclusive = Range 1 to 10
a.map(doubler)                                    //> res11: scala.collection.immutable.IndexedSeq[Int] = Vector(2, 4, 6, 8, 10, 1
                                                  //| 2, 14, 16, 18, 20)

//or
a.map(x=>x*2)//anonymous funcn used for one time. Also called lambda in python
                                                  //> res12: scala.collection.immutable.IndexedSeq[Int] = Vector(2, 4, 6, 8, 10, 
                                                  //| 12, 14, 16, 18, 20)

//Loop
def factorial(input:Int):Int = {
var result:Int=1
for(i<-1 to input){
result=result*i
}//i and result are mutable. Not good practice. Use recursion
result
}                                                 //> factorial: (input: Int)Int
factorial(5)                                      //> res13: Int = 120

//recursion

def factorial_rec(input:Int):Int = {
if(input==1) 1
else
input*factorial_rec(input-1)
}                                                 //> factorial_rec: (input: Int)Int
factorial_rec(15)                                 //> res14: Int = 2004310016













}