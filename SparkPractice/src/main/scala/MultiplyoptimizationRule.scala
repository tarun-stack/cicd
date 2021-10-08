import org.apache.spark.sql.catalyst.plans.logical.LogicalPlan
import org.apache.spark.sql.catalyst.rules.Rule
import org.apache.spark.sql.catalyst.expressions.Literal
import org.apache.spark.sql.SparkSession
import org.apache.spark.SparkConf
import org.apache.spark.sql.catalyst.expressions.Multiply


object MultiplyoptimizationRule extends Rule[LogicalPlan] {
   val sparkconf= new SparkConf()
  sparkconf.set("spark.app,name","my 1st app")
   sparkconf.set("spark.master","local[2]")
   
  val spark = SparkSession.builder() //treat spark session like driver
 .config(sparkconf)
 .getOrCreate()
 
  def apply(plan:LogicalPlan):LogicalPlan=plan  transformAllExpressions {
    case Multiply(left,right) if right.isInstanceOf[Literal]
    &&
    right.asInstanceOf[Literal].value.asInstanceOf[Integer]==1 =>
      println("Optimization of one applied")
      left
  }



spark.experimental.extraOptimizations = Seq(MultiplyoptimizationRule)
//sum(score*1) in sql query will be treated as sum(score) in optimized and physical execution plans. But shows (score*1) in parsed and analysed plans.



}