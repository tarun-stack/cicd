
import org.apache.log4j.Logger
import org.apache.log4j.Level



object practice {
  
  def main(args:Array[String]){
   Logger.getLogger("org").setLevel(Level.ERROR)
    val  year ="This is year 2021"
    val pattern = """.* ([\d]+).*""".r
    val pattern(currYear) = year
    print(currYear.toInt)
    
    
  }

}