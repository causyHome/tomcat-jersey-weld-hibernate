package com.causy.rest.resources;

import com.causy.cache.CacheProducer;
import com.causy.model.Employee;
import com.causy.persistence.api.EmployeeDAO;
import org.infinispan.Cache;

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
    private final Cache<Integer, Employee> cache;

    @Inject
    public EmployeeResource(EmployeeDAO employeeDAO) {
        this.cache = CacheProducer.singleton.getCacheManager().getCache(Employee.class.getCanonicalName());
        this.employeeDAO = employeeDAO;
    }


    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getEmployeeById(@PathParam("id") final int id) {
        Employee employee = cache.get(id);
        if (employee == null) {
            employee = (Employee) employeeDAO.get(id);
            if (employee != null) {
                cache.put(id, employee);
            }
        }
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
