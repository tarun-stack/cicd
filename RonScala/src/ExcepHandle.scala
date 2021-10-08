import java.lang.StringIndexOutOfBoundsException
import java.io.FileNotFoundException
import scala.util.control.Exception
import java.io.FileReader



object ExcepHandle extends App {
  
  val me = new MyException
  try{
    val f1 = new FileReader("sample.txt")
    
  }catch{
    case fn: FileNotFoundException => ;println("File not found" +fn)
  }
  try{
    val a1="India"
    val b1 = -4
    if(b1>0){
      val pos = getCharPosition(a1,b1)
      println(pos)
    }
  else
    throw me //custom exception
  } catch{
      case s1:StringIndexOutOfBoundsException=> ; println("Enter suitable length")
      case me => println(me)
      case _=>println("Unknown exceptin")
    }finally
    println("Executin complted")
    def getCharPosition(a:String,b:Int)={
      val pos = a.charAt(b)
      pos
    }
    
    class MyException extends Exception{
      println("Index sud be positive num")
    }
    
    
    
    
    
    
    
}