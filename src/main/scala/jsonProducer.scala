import java.util.Properties
import org.apache.kafka.clients.producer.{KafkaProducer, ProducerConfig, ProducerRecord}

class jsonProducer {

  val props: Properties = new Properties()
  props.put(ProducerConfig.BOOTSTRAP_SERVERS_CONFIG, "localhost:9092")
  props.put(ProducerConfig.KEY_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer")
  props.put(ProducerConfig.VALUE_SERIALIZER_CLASS_CONFIG,
    "org.apache.kafka.common.serialization.StringSerializer")
  props.put("acks", "all")

  val producer = new KafkaProducer[String, String](props)
  val topic = "json_topic"
  try {
    for (i <- 0 to 5) {
      val message = ???
      val record = new ProducerRecord[String, String](topic, message, "another_topic")

    }
  } catch {
    case e: Exception => e.printStackTrace()
  } finally {
    producer.close()
  }

}
