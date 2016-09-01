package com.causy.rest.resources;

import com.causy.cache.CacheProducer;
import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.dao.BasicDAO;
import com.causy.persistence.dao.TeamDAO;
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

@Path("team")
public class TeamResource {

    private final TeamDAO teamDAO;
    private final BasicDAO basicDAO;
    private final Cache<Integer, Team> cache;

    @Inject
    public TeamResource(TeamDAO teamDAO, BasicDAO basicDAO) {
        this.cache = CacheProducer.singleton.getCacheManager().getCache(Team.class.getCanonicalName());
        this.teamDAO = teamDAO;
        this.basicDAO = basicDAO;
    }

    @GET
    @Path("/{id}")
    @Produces(MediaType.APPLICATION_JSON)
    public Response getTeamById(@PathParam("id") final int id) {
        Team team = cache.get(id);
        if (team == null) {
            team = teamDAO.get(id);
            cache.put(id, team);
        }
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
        final Team team = (Team) teamDAO.create(newTeam);
        return Response.created(new URI("/service/team/" + team.getId())).build();
    }

    @PUT
    @Path("{id}/member/{employeeId}")
    public Response addMember(@PathParam("id") int id, @PathParam("employeeId") int employeeId) {
        final Team team = teamDAO.get(id);
        final Employee employee = (Employee) basicDAO.get(Employee.class, employeeId);
        teamDAO.addMember(team, employee);
        return Response.noContent().build();
    }

    @PUT
    @Consumes(MediaType.APPLICATION_JSON)
    public Response updateTeam(Team existingTeam) {
        teamDAO.update(existingTeam);
        return Response.noContent().build();
    }
}
