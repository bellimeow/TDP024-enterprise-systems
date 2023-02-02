package se.liu.ida.tdp024.account.util.logger;

import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.Producer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.apache.kafka.common.KafkaException;
import org.apache.kafka.common.errors.AuthorizationException;
import org.apache.kafka.common.errors.OutOfOrderSequenceException;
import org.apache.kafka.common.errors.ProducerFencedException;
import org.apache.kafka.common.serialization.StringSerializer;

import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Properties;

public class AccountLoggerKafka implements AccountLogger{

    @Override
    public void log(Throwable throwable) {
        throwable.printStackTrace();        
    }

    @Override
    public void log(AccountLoggerLevel accountLoggerLevel, String shortMessage, String longMessage) {
        if (accountLoggerLevel == AccountLoggerLevel.CRITICAL || accountLoggerLevel == AccountLoggerLevel.ERROR){
            this.sendToKafka("transactions", "Error", shortMessage + ":" + longMessage);
        }  else if(accountLoggerLevel == AccountLoggerLevel.NOTIFY){
            this.sendToKafka("rest-requests", "Request", formatMessage(shortMessage, longMessage));
        } else if(accountLoggerLevel == AccountLoggerLevel.DEBUG){
            this.sendToKafka("transactions", "Event", formatMessage(shortMessage, longMessage));
        } else {
            this.sendToKafka("transactions",  "Event", formatMessage(shortMessage, longMessage));
        }
        
    }


    public void sendToKafka(String topic, String key, String message){

        Properties properties = new Properties();
        properties.put("bootstrap.servers", "localhost:9092");
        properties.put("transactional.id", "id");
        Producer<String, String> producer = new KafkaProducer<>(properties, new StringSerializer(), new StringSerializer());
        producer.initTransactions();

        try {
            producer.beginTransaction();
            producer.send(new ProducerRecord<String,String>(topic,key, message ));
            producer.commitTransaction();
        } catch (ProducerFencedException | OutOfOrderSequenceException | AuthorizationException exception) {
            // We can't recover from these exceptions, so our only option is to close the producer and exit
            producer.close();
        } catch (KafkaException exception){
            // For all other exceptions, just abort the transaction and try again
            producer.abortTransaction();
        }

        producer.close();
    }


    public String formatMessage(String shortmessage, String longMessage){

        LocalDateTime now = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("dd-MM-yyyy HH:mm:ss");
        String time = now.format(formatter);
        String message = shortmessage + ":" + longMessage;

        return "Message: " + message + " , Time: " + time;

    }
}
