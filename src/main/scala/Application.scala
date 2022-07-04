import org.apache.spark.sql.functions.{col, from_json, upper}
import org.apache.spark.sql.types.{IntegerType, StringType, StructType}

object Application {
  def main(args: Array[String]): Unit = {

    if (args.length < 1) {
      println("ERROR: lack of argument")
      sys.exit(-1)
    }
    val outputpath = if (args.length > 0) args(0) else "hdfs://localhost:9000/data"
    val topic = if (args.length > 1) args(1) else "json"

    import org.apache.spark.sql.SparkSession

    val spark = SparkSession
      .builder()
      .appName("kafka json")
      //      .master("local[*]")
      .getOrCreate()

    import spark.implicits._

    val df = spark.readStream
      .format("kafka")
      .option("kafka.bootstrap.servers", ":9092")
      .option("subscribe", topic)
      .option("startingOffsets", "earliest") // From starting
      .load()

    val data = df
      .selectExpr("CAST(value AS STRING)")

    val schema = new StructType()
      .add("name", StringType)
      .add("surname", StringType)
      .add("cats", IntegerType)
      .add("dogs", IntegerType)


    val dataRaw = data
      .select(from_json(col("value"), schema).as("data"))
      .select("data.*")

    val dataTransformed = dataRaw
      .withColumn("Surname", upper($"Surname"))
      .withColumn("animals", $"cats" + $"dogs")
      .drop($"cats")
      .drop($"dogs")

    val block_size = 1024

    dataTransformed
      .writeStream
      .outputMode("append")
      .format("parquet")
      .queryName("data")
      .option("parquet.block.size", block_size)
      .option("checkpointLocation", "src/main/resources/checkpoint_dir")
      .start(outputpath)
      .awaitTermination()
  }
}