package com.causy.rest.resources;

import com.causy.model.Person;
import com.causy.services.PersonTransformationService;
import com.google.common.collect.ImmutableList;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import java.util.HashMap;
import java.util.Map;


@Path("/person")
public class PersonResource {

    private PersonTransformationService personTransformationService;

    public PersonResource() {
    }

    @Inject
    public PersonResource(PersonTransformationService personTransformationService) {
        this.personTransformationService = personTransformationService;
    }

    @Path("get")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public Person getPerson() {

        Map<String, Object> creditCards = new HashMap<String, Object>();
        creditCards.put("MasterCard", "1234 1234 1234 1234");
        creditCards.put("Visa", "1234 1234 1234 1234");
        creditCards.put("dummy", true);
        Person p = new Person("Nabi", "Zamani", null, ImmutableList.copyOf(new String[]{"German", "Persian"}), creditCards, 33);


        System.out.println("REST call...");

        //return Response.ok().entity(p).build();
        return p;
    }

    @Path("get/anonymous")
    @GET
    @Produces({MediaType.APPLICATION_JSON})
    //add MediaType.APPLICATION_XML if you want XML as well (don't forget @XmlRootElement)
    public Person getAnonymizedPerson() {

        Map<String, Object> creditCards = new HashMap<String, Object>();
        creditCards.put("MasterCard", "1234 1234 1234 1234");
        creditCards.put("Visa", "1234 1234 1234 1234");
        creditCards.put("dummy", true);
        Person p = new Person("Nabi", "Zamani", null, ImmutableList.copyOf(new String[]{"German", "Persian"}), creditCards, 33);


        System.out.println("REST call...");

        //return Response.ok().entity(p).build();
        return personTransformationService.anonymize(p);
    }

    @POST
    @Consumes({MediaType.APPLICATION_JSON})
    @Produces({MediaType.TEXT_PLAIN})
    @Path("/post")
    public String postPerson(Person pers) throws Exception {

        System.out.println("First Name = " + pers.getFirstName());
        System.out.println("Last Name  = " + pers.getLastName());

        return "ok";
    }

}
