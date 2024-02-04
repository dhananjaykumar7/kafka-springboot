package com.example.demo.producer;

import java.nio.charset.StandardCharsets;
import java.util.UUID;

import org.apache.kafka.clients.producer.ProducerRecord;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;

import com.example.demo.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyProducer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper mapper;
	
	public MyProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate =kafkaTemplate;
		this.mapper = new ObjectMapper();
		
	}
	
	public void sendMessage(String topic,String message) {
		
		kafkaTemplate.send(topic,message);
		
	}
	
public <T> void sendMessage(String topic,String key,Event<T> event) throws JsonProcessingException {
		
		String payload = mapper.writeValueAsString(event);
		
		ProducerRecord<String, String> producerRecord = new ProducerRecord<String, String>
		(topic, key, payload);
		producerRecord.headers().add("trace-id",UUID.randomUUID().toString().getBytes(StandardCharsets.UTF_8));
		
		kafkaTemplate.send(producerRecord);
	}

}
