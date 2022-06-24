# Spark application that reads records from a Kafka topic. !work in progress!

## This application does the following transformations:


1. Writes records to Kafka in JSON format
    1. JSON example:
       {
       "name": "John",
       "surname": "Doe",
       "cats": 1,
       "dogs": 2
       }
2. Reads the data from the topic
3. Parses the JSONs
4. Surname to upper case letters
5. Sum cats and dogs fields and write the result to the new field “animals”.
6. The result should be written as a parquet file in which every record consists of 3 columns: name (String), surname (String), animals (Integer).



### How to parse JSON in Scala:
* https://alvinalexander.com/scala/how-to-create-scala-object-instance-from-json-string/
* https://sparkbyexamples.com/spark/spark-parse-json-from-text-file-string/