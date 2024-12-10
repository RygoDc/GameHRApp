package com.rygodc.historicalrecycler;

public class HistoricEventModel {

    public String eventName;
    public String eventDate;
    public String eventLocation;

    public HistoricEventModel(String eventName, String eventDate, String eventLocation) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
    }

    public String getEventName() {
        return eventName;
    }

    public String getEventDate() {
        return eventDate;
    }

    public String getEventLocation() {
        return eventLocation;
    }
}
