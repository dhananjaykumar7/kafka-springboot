package com.example.demo.event;

import java.util.UUID;

public record CustomerEvent(
	
	UUID customerId,String name,String address
)
{
	
}
