package com.example.demo.DTO;

import java.util.UUID;

public record CustomerEvent(
	
	UUID customerId,String name,String address
)
{
	
}
