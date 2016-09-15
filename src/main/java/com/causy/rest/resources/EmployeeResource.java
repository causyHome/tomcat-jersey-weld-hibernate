package com.causy.rest.resources;

import com.causy.model.Employee;
import com.causy.persistence.api.EmployeeDAO;

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

import static com.causy.cache.CacheHandler.getEntityFromCache;

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
        Employee employee = (Employee) getEntityFromCache("EmployeeCache").orFromSource(employeeDAO::get).usingCacheKey(id);


        return Response.ok(employee).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listEmployees() {
        return Response.ok(employeeDAO.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createEmployee(Employee newEmployee) throws URISyntaxException {

        employeeDAO.create(newEmployee);
        final int id = newEmployee.getId();
        return Response.created(new URI("/service/employee/" + id)).build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateEmployee(Employee existingEmployee) {
        employeeDAO.update(existingEmployee);
        return Response.noContent().build();
    }

}
