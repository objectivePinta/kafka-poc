package com.kafka.poc;

import org.apache.kafka.clients.consumer.ConsumerRecord;
import org.apache.kafka.clients.consumer.ConsumerRecords;
import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.apache.kafka.clients.producer.ProducerRecord;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestMethod;
import org.springframework.web.bind.annotation.RestController;

import java.util.Iterator;

/**
 * Created by Andrei on 26/03/16.
 */
@RestController
@RequestMapping("/")
public class KafkaController {

    static final Logger logger = LoggerFactory.getLogger(KafkaController.class);

    @Autowired
    KafkaProducer kafkaProducer;

    @Autowired
    KafkaConsumer kafkaConsumer;

    @RequestMapping(value = "/send/{text}", method = RequestMethod.GET)
    public void putSomethingOnQueue(@PathVariable String text)
    {
        kafkaProducer.send(new ProducerRecord<>("test", "key", text));

    }


    @RequestMapping(value = "/get", method = RequestMethod.GET)
    public String getSomethingFromQueue()
    {
        ConsumerRecords<String, String> records =  kafkaConsumer.poll(100);
        StringBuilder sb = new StringBuilder();
        logger.info("Consumer received this number of records:"+records.count());
        for (ConsumerRecord<String, String> record : records) {
            logger.info(record.topic()+" "+record.key()+" "+record.value()+" "+record.offset());
            sb.append(record.topic()+" "+record.key()+" "+record.value()+" "+record.offset());
            sb.append("\n");
        }
    return sb.toString();
    }

    @RequestMapping(value = "/commit", method = RequestMethod.GET)
    public void commitMessagesAsUsed() {
        kafkaConsumer.commitSync();
    }


}
