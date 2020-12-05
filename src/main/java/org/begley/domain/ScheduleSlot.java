package org.begley.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;
import javax.persistence.ManyToOne;
import javax.persistence.MapsId;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.UpdateTimestamp;

import io.quarkus.hibernate.orm.panache.PanacheEntity;

@Entity(name="schedule_slot")
public class ScheduleSlot extends PanacheEntity {

    public String locationName;
    public String zip;

    public LocalDateTime appointmentDateTime;

    @CreationTimestamp
    public LocalDateTime createDateTime;

    @UpdateTimestamp
    public LocalDateTime updateDateTime;

    @Convert(converter = AppointmentTypeConverter.class)    
    public AppointmentType appointmentType;

    //@JsonManagedReference
    // @JsonBackReference
    @ManyToOne(targetEntity = AppointmentRequest.class)
    public AppointmentRequest appointmentRequest;
    
}
