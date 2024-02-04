package com.example.demo.config;

import org.apache.kafka.clients.admin.NewTopic;
import org.springframework.context.annotation.Bean;
import org.springframework.kafka.config.TopicBuilder;
import org.springframework.kafka.listener.DefaultErrorHandler;
import org.springframework.kafka.listener.KafkaListenerErrorHandler;
import org.springframework.stereotype.Component;
import org.springframework.util.backoff.FixedBackOff;
import org.xml.sax.helpers.DefaultHandler;

@Component
public class KafkaConfig {
	@Bean
	public NewTopic topic() {
		return TopicBuilder.name("spring-topic")
		.partitions(3).replicas(1).build();
		
	}
	
	@Bean
	public NewTopic errorTopic()
	{
		return TopicBuilder.name("error-topic")
				.partitions(3)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic failedTopic()
	{
		return TopicBuilder.name("failed-topic")
				.partitions(3)
				.replicas(1)
				.build();
	}
	@Bean
	public NewTopic retryableTopicTopic()
	{
		return TopicBuilder.name("retryable-topic")
				.partitions(3)
				.replicas(1)
				.build();
	}
	@Bean
	public KafkaListenerErrorHandler errorHandler() {
		return (message,exception) ->{
			System.out.println("inside error handler + message");
			
			return "FAILED " + message;
		};
		
	}
	
	public DefaultErrorHandler defaultErrorHandler() {
		
		var defaultHandler = new DefaultErrorHandler(
				(consumerRecord,e) ->{
					System.out.println("");
				},
				new FixedBackOff(100L,2)
				);
		defaultHandler.setAckAfterHandle(false);
		defaultHandler.addNotRetryableExceptions(null);
		defaultHandler.addRetryableExceptions(null);
		return defaultHandler;
		
	}
}
