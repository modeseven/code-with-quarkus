package org.begley.domain;

public enum AppointmentStatus {
    QUEUED("Q"), BOOKED("B"), CANCELED("C");
 
    private String code;
 
    private AppointmentStatus(String code) {
        this.code = code;
    }
 
    public String getCode() {
        return code;
    }
}