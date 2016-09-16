package com.causy.rest.resources;

import com.causy.cache.CacheHandler;
import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.api.EmployeeDAO;
import com.causy.persistence.api.TeamDAO;

import javax.inject.Inject;
import javax.ws.rs.Consumes;
import javax.ws.rs.DELETE;
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
    private final CacheHandler<Integer, Team> cacheHandler;

    @Inject
    public TeamResource(TeamDAO teamDAO, EmployeeDAO employeeDAO, CacheHandler<Integer, Team> cacheHandler) {
        this.teamDAO = teamDAO;
        this.employeeDAO = employeeDAO;
        this.cacheHandler = cacheHandler;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamById(@PathParam("id") final int id) {
        Team team = cacheHandler.getEntityFromCache("TeamCache").orFromSource(teamDAO::get).usingCacheKey(id);
        return Response.ok(team).build();
    }

    @GET
    @Produces(MediaType.APPLICATION_JSON)
    public Response listTeams() {
        return Response.ok(teamDAO.list()).build();
    }

    @POST
    @Consumes(MediaType.APPLICATION_JSON)
    public Response createTeam(Team newTeam) throws URISyntaxException {
        final Team team = teamDAO.create(newTeam);
        return Response.created(new URI("/service/team/" + team.getId())).build();
    }

    @PUT
    @Path("{id}/member/{employeeId}")
    public Response addMember(@PathParam("id") int id, @PathParam("employeeId") int employeeId) {
        final Team team = teamDAO.get(id);
        final Employee employee = employeeDAO.get(employeeId);
        teamDAO.addMember(team, employee);
        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(Team existingTeam) {
        teamDAO.update(existingTeam);
        return Response.noContent().build();
    }

    @DELETE
    @Path("{id}")
    public Response deleteTeam(@PathParam("id") final int teamId) {
        teamDAO.delete(teamId);
        return Response.noContent().build();
    }
}
