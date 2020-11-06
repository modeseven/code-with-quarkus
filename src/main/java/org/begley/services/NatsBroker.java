package org.begley.services;

import java.nio.charset.StandardCharsets;

import javax.enterprise.context.ApplicationScoped;
import javax.enterprise.event.Observes;
import javax.inject.Inject;

import org.jboss.logging.Logger;

import io.nats.client.Connection;
import io.nats.client.Nats;
import io.quarkus.runtime.StartupEvent;

@ApplicationScoped
public class NatsBroker {

    private static final Logger LOG = Logger.getLogger("NatsBroker");

    @Inject
    NatsConfig config;

    private Connection nats;

    void onStart(@Observes StartupEvent ev) {
        LOG.info("starting Nats NatsBroker...");
        try {
            nats = Nats.connect("nats://"+config.host+":" + config.port);
            LOG.info("connected to NATS on:" + config.host + ":" + config.port);
        } catch (Exception e) {
            e.printStackTrace();
        } 
    }

    public void publish(String subject, String message) {
        nats.publish(subject, message.getBytes(StandardCharsets.UTF_8));
    }

}
