package com.causy.rest.resources;

import com.causy.model.Employee;
import com.causy.persistence.dao.EmployeeDAO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.GET;
import javax.ws.rs.POST;
import javax.ws.rs.PUT;
import javax.ws.rs.Path;
import javax.ws.rs.PathParam;
import javax.ws.rs.Produces;
import javax.ws.rs.core.MediaType;
import javax.ws.rs.core.Response;
import java.net.URI;
import java.net.URISyntaxException;

@Path("employee")
public class EmployeeResource {

    private final EmployeeDAO employeeDAO;

    @Inject
    public EmployeeResource(EmployeeDAO employeeDAO) {
        this.employeeDAO = employeeDAO;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") final int id) {
        return Response.ok(employeeDAO.get(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEmployees() {
        return Response.ok(employeeDAO.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee newEmployee) throws URISyntaxException {

        final int id = employeeDAO.create(newEmployee);
        return Response.created(new URI("/service/employee/" + id)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee existingEmployee) {
        employeeDAO.update(existingEmployee);
        return Response.noContent().build();
    }

}
