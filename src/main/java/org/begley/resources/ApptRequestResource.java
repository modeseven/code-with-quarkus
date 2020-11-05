package org.begley.resources;

import java.util.Optional;

import javax.transaction.Transactional;
import javax.ws.rs.GET;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;

import org.begley.domain.AppointmentRequest;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

@Path("api/appointmentrequest")
public class ApptRequestResource {

    @Transactional
    @GET
    @Path("/{id}")
    @Produces(MediaType.TEXT_PLAIN)
    public Response find(@PathParam("id") Long id) {

        AppointmentRequest ar = new AppointmentRequest();
        ar.setApptType("apptType");
        ar.setFirstName("firstName");
        ar.setLastName("lastName");
        ar.setSubjectId("subjectId");
        ar.setStatus("status");
        ar.persistAndFlush();

        return AppointmentRequest.findByIdOptional(id)
            .map(u -> Response.ok( u.toString()))
            .orElseGet(() -> Response.status(NOT_FOUND))
            .build();

    }
}