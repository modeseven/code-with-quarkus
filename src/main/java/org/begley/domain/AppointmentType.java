package org.begley.domain;

import java.util.Random;

public enum AppointmentType {
    INTERVIEW("I"), TRAINING("T"), MEET("M"), GREET("G");
 
    private String code;
 
    private AppointmentType(String code) {
        this.code = code;
    }
 
    public String getCode() {
        return code;
    }

    public AppointmentType randomType() {
        int pick = new Random().nextInt(AppointmentType.values().length);
        return AppointmentType.values()[pick];
    }
}