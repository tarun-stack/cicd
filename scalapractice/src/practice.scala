import org.apache.spark.SparkContext
import org.apache.spark.SparkConf
import scala.io.Source
import org.apache.spark.broadcast.Broadcast
import org.apache.spark.sql.SparkSession
import org.apache.spark.sql.types.StructType
import org.apache.spark.sql.types.StringType
import org.apache.spark.sql.types.IntegerType
import org.apache.spark.sql.types.StructField
import org.apache.spark.sql.Row


object practice extends App{
  val sconf = new SparkConf().setAppName("demo").setMaster("local[2]")
   val sc = new SparkContext(sconf)
  val spark = SparkSession.builder().config(sconf).getOrCreate()
  
  def func1()={
  val rdd1= sc.textFile("hdfs://localhost:9000/user/ron/data/search_data.txt")
  val rd2= rdd1.flatMap(x=>(x.split(" ")))
  val rd3 = rd2.map(x=>(x.toLowerCase(),1)).reduceByKey(_+_).map(x=>(x._2,x._1)).sortBy(_._1,false)
  rd3
  }
   
  def func2:Set[String]={
    var wordlist:Set[String]=Set()
    val lk= Source.fromFile("src/boringwords.txt").getLines
    for(line<-lk)
      wordlist++line
      wordlist
    
  }
  //val rd4= func1.filter(x=> !func2.contains(x._2)).collect.foreach(println)
  //attach schema
  case class wc(count:Int, word:String)
  val custSchema = StructType(List(StructField("count",IntegerType),
                                  StructField("word",StringType)))
  
case class Match(matchId: Int, name: String, player2: String)
case class Player(name: String, birthYear: Int)
import spark.implicits._
val matches = Seq(
  Match(1, "John Wayne", "John Doe"),
  Match(2, "Ive Fish", "San Simon")
).toDF()

val players = Seq(
  Player("John Wayne", 1986),
  Player("Ive Fish", 1990),
  Player("San Simon", 1974),
  Player("John Doe", 1995)
).toDF("name","birthYear")

     //matches.join(players, matches("name")===players("name"),"inner").show  
    // matches.join(players, Seq("name"),"left").show   
                  //                RDD TO DF with schema
  
  //val df1 = func1.toDF().as[wc].show // doesn't wor as func1 returned RDD doesn't hv header
  
  
  //spark.createDataFrame(func1).toDF("count","word").show //works
  
  val rd5 = func1.map(x=>Row(x._1,x._2))//gives RDD of row object, 2nd approach 
  val df2 = spark.createDataFrame(rd5,custSchema)
   //val df4 = spark.createDataFrame(rd5,("count","word"))//error
  val df4 = func1.map(x=>wc(x._1,x._2))//gives RDD of wc object 
  df4.toDF().show//no cust schema reqd in ths case
 
  //rd5.toDF("count","word") //doesnt work since rdd doesn't hv header
  //df2.show
 // df2.map(x=>(x.count,x._word+("hi"))) //gives error as DF still has Row type object
   
  val df3 = df2.as[wc] //dataframe to datframe of type
  //df3.show() 
  //df3.map(x=>(x.count,x.word +("hi"))).show //runs fine as DF as wc type object

  val c = df3.isEmpty
  
  //df2.map(x=>(x.getInt(0),x.getString(1).concat("hi"))).toDF("count","word").show  // toDF("count","word") works as df2 has schema
  df3.columns.foreach(println)
  spark.stop
  
  
  
  
  
  
  
  
  
}