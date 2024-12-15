package com.rygodc.historicalrecycler;

public class HistoricEventModel {

    public String eventName;
    public String eventDate;
    public String eventLocation;
    public boolean esVerdadero;  // Cambiado a tipo boolean

    // Constructor modificado para aceptar un booleano
    public HistoricEventModel(String eventName, String eventDate, String eventLocation, boolean esVerdadero) {
        this.eventName = eventName;
        this.eventDate = eventDate;
        this.eventLocation = eventLocation;
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

    public boolean esVerdadero() {
        return esVerdadero;
    }
}
