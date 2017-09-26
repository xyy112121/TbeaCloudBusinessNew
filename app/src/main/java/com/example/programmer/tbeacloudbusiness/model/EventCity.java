package com.example.programmer.tbeacloudbusiness.model;

/**
 * Created by programmer on 2017/9/26.
 */

public class EventCity {
    private String eventFlag;
    private Condition data;

    public EventCity(String eventFlag, Condition data) {
        this.eventFlag = eventFlag;
        this.data = data;
    }

    public EventCity(String eventFlag) {
        this.eventFlag = eventFlag;
        this.data = data;
    }

    public String getEventFlag() {
        return eventFlag;
    }

    public void setEventFlag(String eventFlag) {
        this.eventFlag = eventFlag;
    }

    public Condition getData() {
        return data;
    }

    public void setData(Condition data) {
        this.data = data;
    }
}
