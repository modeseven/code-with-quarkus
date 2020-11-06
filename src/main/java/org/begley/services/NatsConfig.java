package org.begley.services;

import io.quarkus.arc.config.ConfigProperties;

@ConfigProperties(prefix = "nats")
public class NatsConfig {

    public String host;
    public String port;      
}
