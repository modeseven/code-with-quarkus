package org.begley.domain;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * AppointmentRequest
 */
@Entity(name="appt_rqst")
public class AppointmentRequest extends PanacheEntity {

    public String status;
    public String apptType;
    public String subjectId;
    public String firstName;
    public String lastName;    
}