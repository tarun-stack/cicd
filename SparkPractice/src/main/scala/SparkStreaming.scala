
import org.apache.spark.streaming.StreamingContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.streaming.Seconds
import org.apache.spark.SparkConf
import org.apache.log4j.Level
import org.apache.log4j.Logger

object SparkStreaming extends App {
 Logger.getLogger("org").setLevel(Level.ERROR)
 def updatefunc(newValues:Seq[Int], prevValues: Option[Int]) ={
   val newCount = prevValues.getOrElse(0)+newValues.sum
   Some(newCount)
 }
 
 //publisher nc -lk 9995
val spark = SparkSession.builder().config(new SparkConf().setMaster("local[*]")).enableHiveSupport().getOrCreate()
  //val ssc = new StreamingContext(spark.sparkContext,Seconds(10))
val ssc = new StreamingContext(spark.sparkContext,Seconds(2)) //batch interval updated for reduceBykey and Window
val lines = ssc.socketTextStream("127.0.0.1",9995) //lines is DStream
ssc.checkpoint(".")
val words = lines.flatMap(x=>x.split(" "))
val pairs = words.map(x=>(x,1))
//val wordCount = pairs.reduceByKey(_+_) //stateless
//val wordCount = pairs.updateStateByKey(updatefunc) //stateful
val wordCount = pairs.reduceByKeyAndWindow(_+_,_-_,Seconds(10),Seconds(4)).filter(_._2>0)
//gives last 10 sec summary and then count decreses after sliding window of every 2 secs
//Summary Func (x+y)--add new RDDs, Inverse Func(x-y)-- substract RDDs which go away
wordCount.print
ssc.start()
ssc.awaitTermination()
}
