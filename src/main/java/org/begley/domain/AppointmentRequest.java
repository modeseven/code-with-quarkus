package org.begley.domain;

import java.time.LocalDateTime;

import javax.persistence.CascadeType;
import javax.persistence.Convert;
import javax.persistence.Entity;
import javax.persistence.FetchType;
import javax.persistence.ManyToOne;
import javax.persistence.OneToOne;
import javax.persistence.PrimaryKeyJoinColumn;

import com.fasterxml.jackson.annotation.JsonBackReference;
import com.fasterxml.jackson.annotation.JsonManagedReference;

import javax.persistence.JoinColumn;

import org.hibernate.annotations.CreationTimestamp;
import org.hibernate.annotations.Fetch;
import org.hibernate.annotations.FetchMode;
import org.hibernate.annotations.LazyToOne;
import org.hibernate.annotations.LazyToOneOption;
import org.hibernate.annotations.UpdateTimestamp;
import org.hibernate.search.engine.backend.types.Sortable;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.FullTextField;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.Indexed;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.IndexedEmbedded;
import org.hibernate.search.mapper.pojo.mapping.definition.annotation.KeywordField;

import io.quarkus.hibernate.orm.panache.PanacheEntity;


/**
 * AppointmentRequest
 */
@Entity(name="appt_rqst")
@Indexed
public class AppointmentRequest extends PanacheEntity {

    @CreationTimestamp
    public LocalDateTime createDateTime;

    @UpdateTimestamp
    public LocalDateTime updateDateTime;

    // subject
    public String subjectId;

    @FullTextField(analyzer = "name")
    @KeywordField(name = "firstName_sort", sortable = Sortable.YES, normalizer = "sort")
    public String firstName;
    @FullTextField(analyzer = "name")
    @KeywordField(name = "lastName_sort", sortable = Sortable.YES, normalizer = "sort")
    public String lastName; 
    @FullTextField(analyzer = "name")
    public String street;
    @FullTextField(analyzer = "name")
    public String city;
    public String state;
    public String zip;

    @Convert(converter = AppointmentTypeConverter.class)    
    public AppointmentType appointmentType;

    @Convert(converter = AppointmentStatusConverter.class)    
    public AppointmentStatus appointmentStatus;

    //@JsonBackReference
    //@JsonManagedReference
    //@OneToOne(targetEntity = ScheduleSlot.class, cascade = CascadeType.ALL, fetch = FetchType.EAGER)
   // @LazyToOne(LazyToOneOption.NO_PROXY)
    //@JoinColumn(name = "slot_id")
  //  @Fetch(value = FetchMode.JOIN)
    @ManyToOne(cascade = CascadeType.ALL, fetch = FetchType.LAZY)
  //  @IndexedEmbedded 
   // @JoinColumn(name = "id")
    public ScheduleSlot scheduleSlot;
    
}