package com.causy.rest.resources;

import org.glassfish.jersey.server.ResourceConfig;

import javax.ws.rs.ApplicationPath;

@ApplicationPath("/service/")
public class Application extends ResourceConfig {

    public Application() {

        packages(false, "com.causy.rest.resources");
    }
}
