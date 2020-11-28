package org.begley.domain;

import java.time.LocalDateTime;

import javax.persistence.Entity;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * AppointmentRequest
 */
@Entity(name="appt_rqst")
public class AppointmentRequest extends PanacheEntity {

    @CreationTimestamp
    public LocalDateTime createDateTime;

    @UpdateTimestamp
    public LocalDateTime updateDateTime;


    public String status;
    public String apptType;
    public String subjectId;
    public String firstName;
    public String lastName;    
}