package se.liu.ida.tdp024.account.logic.util;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;

import java.util.Properties;

public class KafkaLogger {

    private static KafkaProducer<String,String> kafkaProducer;

    public static Producer<String,String> createProducer(){
        if (kafkaProducer == null) {
            Properties props = new Properties();
            props.put("bootstrap.servers", "localhost:9092");
            props.put("acks", "all");
            props.put("retries", 3);
            props.put("batch.size", 16384);
            props.put("linger.ms", 1);
            props.put("buffer.memory", 33554432);
            props.put("key.serializer", "org.apache.kafka.common.serialization.StringSerializer");
            props.put("value.serializer", "org.apache.kafka.common.serialization.StringSerializer");

            kafkaProducer = new KafkaProducer<String, String>(props);
        }
        return kafkaProducer;
    }

    public static void sendToKafka(String topic, String message){
        try{
            Producer<String, String> producer = createProducer();
            producer.send(new ProducerRecord<>(topic, message));
        } catch (Exception e){
            System.out.println(e.getMessage());
        }
    }

}
