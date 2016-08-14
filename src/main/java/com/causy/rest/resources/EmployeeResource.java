package com.causy.rest.resources;

import com.causy.model.Employee;
import com.causy.persistence.SessionFactoryManager;
import org.hibernate.Session;
import org.hibernate.SessionFactory;
import org.hibernate.Transaction;

import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("employee")
public class EmployeeResource {

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") final int id) {
        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        Employee emp = (Employee) session.get(Employee.class, id);
        tx.commit();
        return Response.ok(emp).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee newEmployee) throws URISyntaxException {

        SessionFactory sessionFactory = SessionFactoryManager.instance.getSessionFactory();

        Session session = sessionFactory.getCurrentSession();
        Transaction tx = session.beginTransaction();
        session.save(newEmployee);
        tx.commit();
        return Response.created(new URI("/service/employee/" + newEmployee.getId())).build();
    }

}
