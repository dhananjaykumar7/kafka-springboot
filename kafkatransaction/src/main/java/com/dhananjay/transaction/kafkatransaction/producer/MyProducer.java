package com.dhananjay.transaction.kafkatransaction.producer;

import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.stereotype.Component;
import org.springframework.transaction.annotation.Transactional;

import com.fasterxml.jackson.databind.ObjectMapper;


@Component
public class MyProducer {
	
	private final KafkaTemplate<String, String> kafkaTemplate;
	private final ObjectMapper mapper;
	
	public MyProducer(KafkaTemplate<String, String> kafkaTemplate) {
		this.kafkaTemplate =kafkaTemplate;
		this.mapper = new ObjectMapper();
		
	}
	
	@Transactional("KafkaTransactionManager")
	public void sendMessage(String topic,String message) throws InterruptedException {
		
		kafkaTemplate.send(topic,message);
		
		kafkaTemplate.send("log-topic","Received "+ message+ " from: "+topic);
		
		Thread.sleep(1000L);
		
		throw new RuntimeException("Runtime exception ");
		
	}
	


}
