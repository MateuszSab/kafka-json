# Spark streaming application that reads records from a Kafka topic and transforms them


## This application does the following transformations:

1. Read records from Kafka in JSON format
    1. JSON example:
       {
       "name": "John",
       "surname": "Doe",
       "cats": 1,
       "dogs": 2
       }
2. Parses the JSONs
3. Surname to upper case letters
4. Sum cats and dogs fields and write the result to the new field “animals”.
5. The result is written as a parquet file in which every record consists of 3 columns: name (String), surname (String), animals (Integer).
6. Output directory can be passed as an argument (default is `"hdfs://localhost:9000/data"`).
7. Kafka topic can be passed as a second argument (default is "json")



## How to run:

- create a jar via `sbt package`
- `bin/spark-submit --master yarn /mnt/c/scala-projects/kafka-json/target/scala-2.12/kafka-json_2.12-0.1.0-SNAPSHOT.jar <output_file_path> <topic_name>`

### How to send a kafka message:

- run kafka zookeeper
- run kafka broker
- start kafka producer `kafka-console-producer.bat --topic json --bootstrap-server localhost:9092`
- send message in JSON format. For example: `{"name": "John", "surname": "Doe", "cats": 1, "dogs": 2}`

