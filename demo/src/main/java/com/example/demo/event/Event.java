package com.example.demo.event;

import java.sql.Timestamp;
import java.util.UUID;

public record Event<T>(UUID eventId,String eventType,Timestamp creationDate,T payload) {

}
