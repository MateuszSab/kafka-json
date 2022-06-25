
object Main extends App {

  import org.apache.spark.sql.SparkSession

  val spark = SparkSession
    .builder()
    .appName("kafka json")
    .master("local[*]")
    .getOrCreate()

  import spark.implicits._
  val path = "src/main/resources/data.json"

  val data = spark.read.json(path)
  data
//    .selectExpr("to_json(struct(*)) AS value")
    .selectExpr("CAST(key AS STRING)", "CAST(value AS STRING)")
    .writeStream
    .format("kafka")
    .option("kafka.bootstrap.servers", ":9092")
    .option("topic", "topic1")
    .start()
    .awaitTermination()

}