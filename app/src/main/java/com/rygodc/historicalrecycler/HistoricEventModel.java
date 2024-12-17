package com.rygodc.historicalrecycler;

public class HistoricEventModel {

    public String eventName;
    public String eventDate;
    public String eventLocation;
    public String eventQuestion;
    public String eventExplain;
    public boolean esVerdadero;


    public HistoricEventModel(String eventName, String eventDate, String eventLocation, String eventQuestion, String eventExplain, boolean esVerdadero) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
        this.eventQuestion = eventQuestion;
        this.eventExplain = eventExplain;
        this.esVerdadero = esVerdadero;
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

    public String getEventQuestion() {
        return eventQuestion;
    }
    public String getEventExplain() {
        return eventExplain;
    }

    public boolean esVerdadero() {
        return esVerdadero;
    }
}
