package com.example.demo.consumer;

import org.apache.kafka.clients.consumer.Consumer;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.kafka.annotation.DltHandler;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.annotation.RetryableTopic;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.stereotype.Component;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.retry.annotation.Backoff;


@Component
public class NonBlockingConsumer {
	
	private static final Logger logger = LoggerFactory.getLogger(NonBlockingConsumer.class);
	
	@RetryableTopic(
			attempts ="4",
			backoff = @Backoff(value =3000L,multiplier = 2))
	@KafkaListener(
			id="retryable-consumer",
			groupId = "retryable-group",
			topics = "retryable-topic"
			)
	public void listen(
			@Payload String payload,
			@Header(KafkaHeaders.RECEIVED_TOPIC) String topic,
			@Header("trace_id") String traceId,
			@Header(KafkaHeaders.DELIVERY_ATTEMPT) String delivery,
			@Header(KafkaHeaders.GROUP_ID) String groupId,
			@Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment
			) {
		
		logger.info("topic: {},ThreadId:{},delivery: {},groupId:{}, payload:{}",
				topic,Thread.currentThread().getId(),delivery,groupId,payload);
		
		throw new RuntimeException("some error occured");
				
		
	}
	
	@DltHandler
	public void dltHandler(@Payload String payload) {
		logger.error("Received some event in dlt topic {}",payload);
		
	}

}
