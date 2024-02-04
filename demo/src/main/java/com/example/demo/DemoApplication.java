package com.example.demo;

import java.sql.Timestamp;
import java.time.Instant;
import java.util.UUID;
import java.util.concurrent.ThreadLocalRandom;

import org.springframework.boot.ApplicationRunner;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.context.annotation.Bean;

import com.example.demo.event.CustomerEvent;
import com.example.demo.event.Event;
import com.example.demo.producer.MyProducer;

@SpringBootApplication
public class DemoApplication {

	public static void main(String[] args) {
		SpringApplication.run(DemoApplication.class, args);
	}
	
	@Bean
	public ApplicationRunner runner(MyProducer myProducer) {
		return args ->{
			publishEventToRetryableTopic(myProducer);
			/*var customer_1 = UUID.randomUUID();
			var customer_2 = UUID.randomUUID();
			var customer_3 = UUID.randomUUID();
			
			UUID[] customerIds = new UUID[] {customer_1,customer_2,customer_3};
			
			while(true)
			{
				//0,1,2
				int index = ThreadLocalRandom.current().nextInt(0,3);
				var customer = new CustomerEvent(customerIds[index],"fake data","fake address");
				
				var event = new Event<CustomerEvent>(
						UUID.randomUUID(),
						"ADD-CUSTOMER",
						Timestamp.from(Instant.now()),
						customer);
				myProducer.sendMessage("sping-topic",customer.customerId().toString(),event);

				//myProducer.sendMessage("sping-topic", "New Message"+UUID.randomUUID());
			}*/
		};
		
	}
	
	private void publishEventToRetryableTopic(MyProducer myProducer) {
		myProducer.sendMessage("retryable-topic", "myCustom-event-1");
	}

}
