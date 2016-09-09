package com.causy.persistence.impl;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.api.BasicDAO;
import com.causy.persistence.api.TeamDAO;

import javax.inject.Inject;
import java.util.List;

import static com.causy.persistence.impl.JpaUtils.transactional;

public class TeamDAOImpl implements TeamDAO {

    private final BasicDAO basicDAO;

    @Inject
    public TeamDAOImpl(BasicDAO basicDAO) {
        this.basicDAO = basicDAO;
    }

    @Override
    public Team create(Team newTeam) {
        basicDAO.create(newTeam);
        return newTeam;
    }

    @Override
    public Team get(int teamId) {
        return (Team) basicDAO.get(Team.class, teamId);
    }

    @Override
    public Team update(Team team) {
        return (Team) basicDAO.update(team);
    }

    @Override
    public void addMember(Team team, Employee employee) {
        transactional(entityManager -> {
            employee.setTeam(team);
            team.addMember(employee);
            entityManager.merge(team);
            return null;
        }, String.format("Could not add member %s to team %s !", employee, team));
    }

    @Override
    public List<Team> list() {
        return (List<Team>) basicDAO.list(Team.class);
    }

    @Override
    public long count() {
        return basicDAO.count(Team.class);
    }

    @Override
    public void delete(int teamId) {
        transactional(entityManager -> {
            final Team team = entityManager.find(Team.class, teamId);
            entityManager.remove(team);
            return null;
        }, String.format("Could not delete team with id %s", teamId));
    }
}
