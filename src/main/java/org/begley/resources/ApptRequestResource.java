package org.begley.resources;

import javax.inject.Inject;
import javax.transaction.Transactional;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import javax.ws.rs.core.Response.Status;

import com.github.javafaker.Faker;

import org.begley.domain.AppointmentRequest;
import org.begley.domain.BookingRequest;
import org.begley.services.NatsBroker;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.util.List;
import java.util.Optional;

@Path("apptrqst")
public class ApptRequestResource {

    @Inject
    NatsBroker nats;

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public List<AppointmentRequest> apptRqst() {
        return AppointmentRequest.listAll();
    }

    @Transactional
    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response find(@PathParam("id") Long id) {       

        return AppointmentRequest.findByIdOptional(id)
            .map(u -> Response.ok( u))
            .orElseGet(() -> Response.status(NOT_FOUND))
            .build();

    }

    @Transactional
    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response newApptRqst(AppointmentRequest apptrqst) {
        if(apptrqst.id == -1) {
            Faker faker = new Faker();
            apptrqst.firstName = faker.name().firstName();
            apptrqst.lastName =  faker.name().lastName();
            apptrqst.subjectId = faker.idNumber().ssnValid();
        }

        apptrqst.status = "Q";
        apptrqst.id = null;
        apptrqst.persist();
        return Response.status(Status.CREATED).entity(apptrqst).build();
    }

    @Transactional
    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    @Produces(MediaType.APPLICATION_JSON)
    public Response bookingRequest(BookingRequest bookrqst) {
        return AppointmentRequest.findByIdOptional(bookrqst.id)
            .map(u -> {
                nats.publish("bookingRqst", bookrqst.toString());
                return Response.ok( u);
            })
            .orElseGet(() -> Response.status(NOT_FOUND))
            .build();
    }
}