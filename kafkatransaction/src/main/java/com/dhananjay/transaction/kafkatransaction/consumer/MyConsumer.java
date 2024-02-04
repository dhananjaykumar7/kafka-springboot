package com.dhananjay.transaction.kafkatransaction.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.core.KafkaTemplate;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyConsumer {
	
	private final ObjectMapper mapper;
	
	private final KafkaTemplate<String,String> kafkaTemplate;
	
	private final static Logger logger = LoggerFactory.getLogger(MyConsumer.class);
	
	public MyConsumer(ObjectMapper mapper,KafkaTemplate<String,String> kafkaTemplate) {
		this.mapper = mapper;
		this.kafkaTemplate = kafkaTemplate;
		
	}
	//consumer -> processing -> write (Exactly one processing)
	
	@KafkaListener(id ="my-transactionconsumer",
			topics="transaction-topic",
			groupId = "transaction-group",
			concurrency = "1")
	public void listen(
			@Payload String payload
			) throws Exception {
		
		
			
				logger.info("===============================  payload:{}",payload);

				System.out.println("Received message "+payload);
			} 
	@KafkaListener(id ="my-transaction-log-consumer",
			topics="log-topic",
			groupId = "transaction-group",
			concurrency = "1")
	public void listenLog(
			@Payload String payload
			) throws Exception {
				logger.info("===============================  payload:{}",payload);

				System.out.println("Received message "+payload);
				
				// send data to other topic 
				kafkaTemplate.send("log-topics","processed successfully"+payload);
				
				Thread.sleep(1000L);
				
				throw new RuntimeException("Runtime exception ");
			} 
			
				
		
		
	}
