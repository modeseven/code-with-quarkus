package org.begley.repository;

import javax.enterprise.context.ApplicationScoped;

import org.begley.domain.AppointmentRequest;

import io.quarkus.hibernate.orm.panache.PanacheRepository;

@ApplicationScoped
public class AppRequestRepository implements PanacheRepository<AppointmentRequest> {
    
}
