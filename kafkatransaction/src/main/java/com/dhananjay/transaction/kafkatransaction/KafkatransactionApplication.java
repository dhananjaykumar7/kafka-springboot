package com.dhananjay.transaction.kafkatransaction;

import org.apache.kafka.common.Uuid;
import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.dhananjay.transaction.kafkatransaction.producer.MyProducer;

@SpringBootApplication
public class KafkatransactionApplication {

	public static void main(String[] args) {
		SpringApplication.run(KafkatransactionApplication.class, args);
	}
	@Bean
	public ApplicationRunner runner(MyProducer myProducer) {
		
		return args ->{
			try {
				myProducer.sendMessage("transaction-topic", "Message "+Uuid.randomUuid());

			}
			catch (Exception e) {
				
			}
		};
	}

}
