package org.begley.domain;

import javax.persistence.Entity;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

/**
 * AppointmentRequest
 */
@Entity(name="appt_rqst")
public class AppointmentRequest extends PanacheEntity {

    private String status;
    private String apptType;
    private String subjectId;
    private String firstName;
    private String lastName;

    public String getStatus() {
        return status;
    }

    public void setStatus(String status) {
        this.status = status;
    }

    public String getApptType() {
        return apptType;
    }

    public void setApptType(String apptType) {
        this.apptType = apptType;
    }

    public String getSubjectId() {
        return subjectId;
    }

    public void setSubjectId(String subjectId) {
        this.subjectId = subjectId;
    }

    public String getFirstName() {
        return firstName;
    }

    public void setFirstName(String firstName) {
        this.firstName = firstName;
    }

    public String getLastName() {
        return lastName;
    }

    public void setLastName(String lastName) {
        this.lastName = lastName;
    }

    @Override
    public String toString() {
        return "AppointmentRequest [apptType=" + apptType + ", firstName=" + firstName + ", lastName=" + lastName
                + ", status=" + status + ", subjectId=" + subjectId + "]";  
    }

    
}