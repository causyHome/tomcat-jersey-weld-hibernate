package com.causy.rest.configuration.jersey;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/service/")
public class RestConfiguration extends ResourceConfig {

    public RestConfiguration() {
        packages(false, "com.causy.rest.resources");
    }
}
