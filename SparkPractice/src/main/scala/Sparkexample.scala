

import org.apache.spark.SparkContext
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.log4j.Logger
import org.apache.log4j.Level


object SDFS23 extends App{
  case class Logging(level:String,datetime:String)
  Logger.getLogger("org").setLevel(Level.ERROR)
  def mapper(line:String):Logging={
  val fields = line.split(",")
  val logging:Logging=Logging(fields(0),fields(1))
  return logging
}
   val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
  import spark.implicits._
  val myList=List("WARN,2016-12-21 05:45:21",
                 "FATAL,2017-11-18 07:35:21",
                 "INFO,2016-12-21 03:28:21",
                 "WARN,2016-12-21 05:45:21",
                 "WARN,2017-11-18 05:45:21",
                 "FATAL,2016-12-21 05:45:21"
    
  )
  val rdd1 = spark.sparkContext.parallelize(myList)
  val rdd2 =  rdd1.map(mapper)
  val df1 =  rdd2.toDF()
  
  df1.createOrReplaceTempView("logging_table")
  //spark.sql("select * from logging_table").show
  
  //spark.sql("select level,collect_list(datetime) from logging_table group by level order by level").show(false)
  
  //spark.sql("select level,count(datetime) from logging_table group by level order by level").show(false)
  
  val df2 = spark.sql("select level,date_format(datetime,'MMMM') as month from logging_table")
  df2.createOrReplaceTempView("new_logging_table")
  spark.sql("select level,month,count(1) from new_logging_table group by level,month").show
  
  val df3 = spark.read
.option("header",true)
.csv("file:///home/ron/biglog.txt")
  
  df3.createOrReplaceTempView("my_new_logging_table")
  val results= spark.sql("""select level,date_format(datetime,'MMMM') as month, count(1) as total from 
    my_new_logging_table group by level,month""")
    //results.createOrReplaceTempView("results_table")
  val result2 = spark.sql("""select level,date_format(datetime,'MMMM') as month, cast(first(date_format(datetime,'M'))as int) as monthnum,count(1) as total from 
    my_new_logging_table group by level,month order by monthnum""")
    result2.drop("monthnum")
    
    //result2.show(60)
    //spark.sql("""select level,date_format(datetime,'MMMM') as month, cast(date_format(datetime,'M')as int) as monthnum from 
    //my_new_logging_table""").groupBy("level").pivot("monthnum").count().show()
    
   val cols=List("January","February","March","April","May","June","July","August","September","October","November","December") 
    spark.sql("""select level,date_format(datetime,'MMMM') as month from 
    my_new_logging_table""").groupBy("level").pivot("month",cols).count().show(100)
    //static cols list helps optimize by avoiding calculation to get pivot elements. Since we r sure there will always be 12 months
    
}