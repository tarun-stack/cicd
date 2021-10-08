import scala.annotation._

object AnnotationEx extends App {
  //explicit instruction to compiler
  @deprecated("Use this method in flow1")
  def getSum(a:Int)= a+a
  
  getSum(4)
  
  //@serializable //if want class or obj to be serialized
  
}