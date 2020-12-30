package org.begley.resources;

import javax.enterprise.event.Observes;
import javax.inject.Inject;
import javax.persistence.EntityManager;
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
import org.begley.domain.AppointmentStatus;
import org.begley.domain.AppointmentType;
import org.begley.domain.BookingRequest;
import org.begley.domain.ScheduleSlot;
import org.begley.services.NatsBroker;
import org.hibernate.search.mapper.orm.Search;
import org.jboss.resteasy.annotations.jaxrs.QueryParam;

import io.quarkus.runtime.StartupEvent;

import static javax.ws.rs.core.Response.Status.NOT_FOUND;

import java.time.LocalDateTime;
import java.util.List;
import java.util.Optional;

@Path("apptrqst")
public class ApptRequestResource {

    @Inject
    NatsBroker nats;

    @Inject
    EntityManager em;

    @Transactional 
    void onStart(@Observes StartupEvent ev) throws InterruptedException { 
        // only reindex if we imported some content
        if (AppointmentRequest.count() > 0) {
            Search.session(em)
                    .massIndexer()
                    .startAndWait();
        }
    }

    @GET
    @Path("/search") 
    @Transactional
    public List<AppointmentRequest> searchAuthors(@QueryParam String pattern, 
            @QueryParam Optional<Integer> size) {
                System.out.println("search is happening?" + pattern);
        return Search.session(em) 
                .search(AppointmentRequest.class) 
                .where(f ->
                    pattern == null || pattern.trim().isEmpty() ?
                            f.matchAll() : 
                            f.simpleQueryString()
                                .fields("firstName", "lastName").matching(pattern) //"books.title" todo: add more late?
                )
                .sort(f -> f.field("lastName_sort").then().field("firstName_sort")) 
                .fetchHits(size.orElse(20)); 
    }

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
        // generate fake identity name if -1
        if(apptrqst.id == -1) {
            Faker faker = new Faker();
            apptrqst.firstName = faker.name().firstName();
            apptrqst.lastName =  faker.name().lastName();
            apptrqst.subjectId = faker.idNumber().ssnValid();
            apptrqst.street = faker.address().streetName();
            apptrqst.city = faker.address().city();
            apptrqst.state = faker.address().state();
            apptrqst.zip = faker.address().zipCode();
            apptrqst.appointmentType = AppointmentType.GREET.randomType();
        }       
        apptrqst.appointmentStatus = AppointmentStatus.QUEUED;
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
                Faker faker = new Faker();
                ScheduleSlot ss = new ScheduleSlot();
                ss.locationName = faker.lordOfTheRings().character();

                AppointmentRequest appt = (AppointmentRequest) u;
                appt.appointmentStatus = AppointmentStatus.BOOKED;

                ss.appointmentType = appt.appointmentType;
                ss.zip = appt.zip;
                ss.appointmentDateTime = LocalDateTime.now();

                appt.scheduleSlot = ss;
                appt.persist();
                nats.publish("bookingRqst", bookrqst.toString());
                return Response.ok( u);
            })
            .orElseGet(() -> Response.status(NOT_FOUND))
            .build();
    }
}