import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import org.apache.log4j.Level
import org.apache.log4j.Logger

object StrStream extends App {
 Logger.getLogger("org").setLevel(Level.ERROR)
 def updatefunc(newValues:Seq[Int], prevValues: Option[Int]) ={
   val newCount = prevValues.getOrElse(0)+newValues.sum
   Some(newCount)
 }
 
 //publisher nc -lk 9995
val spark = SparkSession.builder().config(new SparkConf().setMaster("local[*]")).enableHiveSupport().getOrCreate()
  spark.readStream
  .format("socket")
  .option("host","localhost")
  .option("port","9986")


ssc.awaitTermination()
}