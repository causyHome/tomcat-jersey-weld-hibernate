package com.causy.rest.resources;

import com.causy.model.Message;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.ArrayList;
import java.util.Date;
import java.util.List;

@Path("/message")
public class MessageResource {

    @GET
    @Path("ping")
    public String getServerTime() {
        System.out.println("RESTful Service 'MessageService' is running ==> ping");
        return "received ping on " + new Date().toString();
    }

    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public List<Message> getAllMessages() throws Exception {

        List<Message> messages = new ArrayList<>();

        Message m = new Message("Nabi", "Zamani", new Date().getTime(), "Hello World");
        messages.add(m);

        System.out.println("getAllMessages(): found " + messages.size() + " message(s) on DB");

        return messages; //do not use Response object because this causes issues when generating XML automatically
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/post")
    public String postMessage(Message msg) throws Exception {

        System.out.println("First Name = " + msg.getFirstName());
        System.out.println("Last Name  = " + msg.getLastName());

        return "ok";
    }
}
