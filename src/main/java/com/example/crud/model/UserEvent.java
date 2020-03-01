package com.example.crud.model;

public class UserEvent {

    private String eventId;
    private String eventType;

    public UserEvent(String eventId, String eventType) {
        this.eventId = eventId;
        this.eventType = eventType;
    }

    @Override
    public String toString() {
        return "data:{" +
                "eventId='" + eventId + '\'' +
                ", eventType='" + eventType + '\'' +
                '}';
    }
}
