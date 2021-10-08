import scala.concurrent._
import ExecutionContext.Implicits.global
import scala.concurrent.duration._

object futureEx extends App{
  val a1 = Future{Thread.sleep(1000);10}
  val a2 = a1.map(_*5)
  
  a1.isCompleted
  a1.value
  a2.isCompleted
  a2.value
   Thread.sleep(5000)
   a1.isCompleted
  a1.value
  a2.isCompleted
  a2.value
  
  val a3 = Future(1/10)
   Thread.sleep(5000)
   a3.value
   //await.ready --substitute for thread sleep
   val a4 = Await.result(a3,5.seconds)
   
   
   
   
   
  
}