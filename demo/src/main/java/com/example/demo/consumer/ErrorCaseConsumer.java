package com.example.demo.consumer;

import javax.management.RuntimeErrorException;

import org.apache.kafka.clients.consumer.Consumer;
import org.springframework.kafka.annotation.KafkaListener;
import org.springframework.kafka.support.Acknowledgment;
import org.springframework.kafka.support.KafkaHeaders;
import org.springframework.messaging.handler.annotation.Header;
import org.springframework.messaging.handler.annotation.Payload;
import org.springframework.messaging.handler.annotation.SendTo;
import org.springframework.stereotype.Component;

import com.example.demo.exception.RetryableExceptions;

@Component
public class ErrorCaseConsumer {
	
	@KafkaListener(
			id = "error-consumer",
			topics ="error-topic",
			groupId = "error-group",
			errorHandler = "errorHandler"
			)
	@SendTo("failed-topic")
	public void listen(
			@Header(KafkaHeaders.DELIVERY_ATTEMPT) int delivery,
			@Header(KafkaHeaders.CONSUMER) Consumer<String, String> consumer,
			@Payload String payload,
			@Header(KafkaHeaders.ACKNOWLEDGMENT) Acknowledgment acknowledgment) {
		System.out.println("Received Event "+payload);
		System.out.println("Received Event "+delivery);
		if(delivery >=3) {
			consumer.pause(consumer.assignment());
		}
		else {
			throw new RetryableExceptions("error occured");
		}
		acknowledgment.acknowledge();
		
		// if we don't throw the exception from here then kafka will commit the offset for that event
		//throw new RuntimeException("error occured");
		
	}

}
