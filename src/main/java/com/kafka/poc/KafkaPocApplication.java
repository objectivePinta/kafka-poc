package com.kafka.poc;

import org.apache.kafka.clients.consumer.KafkaConsumer;
import org.apache.kafka.clients.producer.KafkaProducer;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.boot.context.web.SpringBootServletInitializer;
import org.springframework.context.annotation.Bean;

import java.util.Arrays;
import java.util.Properties;

@SpringBootApplication
public class KafkaPocApplication extends SpringBootServletInitializer {

	public static void main(String[] args) {
		SpringApplication.run(KafkaPocApplication.class, args);
	}


	@Value("${bootstrap.servers}")
	private String broker;

	@Value("${key.serializer}")
	private String keySerializer;

	@Value("${value.serializer}")
	private String valueSerializer;


	@Value("${key.deserializer}")
	private String keyDeserializer;

	@Value("${value.deserializer}")
	private String valueDeserializer;
//	acks=all
//			retries=0
//	batch.size=16384
//	auto.commit.interval.ms=1000
//	linger.ms=0
//	key.serializer=org.apache.kafka.common.serialization.StringSerializer
//	value.serializer=org.apache.kafka.common.serialization.StringSerializer
//	block.on.buffer.full=true
//	broker=localhost:9092


	@Bean
	KafkaProducer<String, String> kafkaProducer() {

		Properties props = new Properties();
		props.put("bootstrap.servers",broker);
		props.put("key.serializer",keySerializer);
		props.put("value.serializer",valueSerializer);
		props.put("group.id","test");


		KafkaProducer producer = new KafkaProducer(props);

		return  producer;
	}

	@Bean
	KafkaConsumer<String, String> kafkaConsumer() {
		Properties props = new Properties();
		props.put("bootstrap.servers",broker);
		props.put("key.deserializer",keyDeserializer);
		props.put("value.deserializer",valueDeserializer);
		props.put("group.id","test");
		props.put("enable.auto.commit", "false");
		props.put("auto.commit.interval.ms", "1000");
		props.put("session.timeout.ms", "30000");
		KafkaConsumer consumer = new KafkaConsumer(props);
		consumer.subscribe(Arrays.asList("test"));
		return consumer;
	}


}
