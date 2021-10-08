import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level
//Low level transformations
//map, filter, groupByKey -- using raw rdds. Supported by some DF,DS
//High level tx- select,where,groupby. Supported by DF , DS
//Load an unstr file using raw rdd. Each line of rdd is of string type. Then use map tx as it accepts lambda tx and regular expr. Associate o/p with case class to associate str to convert rdd->DS
//Input: 1 2020-09-23     1178,CLOSED   Output: 1,2020-09-23,1178,CLOSED  //structured.
//i can then do higher lvl tx on o/p DS

object UnstrToStr extends App {
  Logger.getLogger("org").setLevel(Level.ERROR)
val myregex = """^(\S+) (\S+)\t(\S+)\,(\S+)""".r
case class Orders(order_id:Int,customer_id:Int,order_status:String)

def parser(line : String)={
    line match{
        case myregex(order_id,date,customer_id,order_status)=>
        Orders(order_id.toInt,customer_id.toInt,order_status)
        
        
    }
}

val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
    val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .enableHiveSupport()
 .getOrCreate()
 
val lines = spark.sparkContext.textFile("file:///home/ron/orders_new.csv")
import spark.implicits._
val orderDS = lines.map(parser).toDS().cache()
orderDS.printSchema
orderDS.select("order_id").show()
orderDS.groupBy("order_status").count().show()
//orderDS.filter(x=>x.order_status) fields available only in toDS, not in toDF
spark.stop()
}