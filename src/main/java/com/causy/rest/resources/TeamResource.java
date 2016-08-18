package com.causy.rest.resources;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.dao.EmployeeDAO;
import com.causy.persistence.dao.TeamDAO;

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

@Path("team")
public class TeamResource {

    private final TeamDAO teamDAO;
    private final EmployeeDAO employeeDAO;

    @Inject
    public TeamResource(TeamDAO teamDAO, EmployeeDAO employeeDAO) {
        this.teamDAO = teamDAO;
        this.employeeDAO = employeeDAO;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamById(@PathParam("id") final int id) {
        return Response.ok(teamDAO.get(id)).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTeams() {
        return Response.ok(teamDAO.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(Team newTeam) throws URISyntaxException {
        final int id = teamDAO.create(newTeam);
        return Response.created(new URI("/service/team/" + id)).build();
    }

    @PUT
    @Path("{id}/member/{employeeId}")
    public Response addMember(@PathParam("id") int id, @PathParam("employeeId") int employeeId) {
        final Team team = teamDAO.get(id);
        final Employee employee = employeeDAO.get(employeeId);
        team.addMember(employee);
        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(Team existingTeam) {
        teamDAO.update(existingTeam);
        return Response.noContent().build();
    }
}
