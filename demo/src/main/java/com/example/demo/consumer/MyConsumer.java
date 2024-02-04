package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.stereotype.Component;

import com.example.demo.event.CustomerEvent;
import com.example.demo.event.Event;
import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

@Component
public class MyConsumer {
	
	private final ObjectMapper mapper;
	
	private final static Logger logger = LoggerFactory.getLogger(MyConsumer.class);
	
	public MyConsumer(ObjectMapper mapper) {
		this.mapper = mapper;
		
	}
	
	@KafkaListener(id ="my-firstconsumer",
			topics="sping-topic",
			groupId = "first-group",
			concurrency = "2")
	public void listen(
			@Payload String payload,
			@Header(KafkaHeaders.RECEIVED_KEY) String key,
			@Header("trace_id") String traceId,
			@Header(KafkaHeaders.RECEIVED_PARTITION) String partionedId,
			@Header(KafkaHeaders.CONSUMER) Consumer<String, String> consumer,
			@Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
			) throws Exception {
		
		
			try {
				TypeReference<Event<CustomerEvent>> event  = mapper.readValue(payload, new TypeReference<>() {
				});
				logger.info("=============================== key:{} partionId:{} event:{} consumerId:{}  ThreadId:{}",
						key,partionedId,event,consumer.groupMetadata().memberId(),Thread.currentThread().getId());
				acknowledgment.acknowledge();

				System.out.println("Received message "+key);
				System.out.println("Received message "+traceId);
				System.out.println("Received message "+payload);
			} catch (Exception e) {
				// TODO Auto-generated catch block
				e.printStackTrace();
				throw e;
			}
			
		} 
		
		
	}
