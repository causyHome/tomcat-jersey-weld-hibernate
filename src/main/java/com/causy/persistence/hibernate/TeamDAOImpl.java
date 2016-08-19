package com.causy.persistence.hibernate;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.dao.EmployeeDAO;
import com.causy.persistence.dao.TeamDAO;

import javax.inject.Inject;
import java.util.List;

import static com.causy.persistence.hibernate.HibernateUtils.performHibernateOperation;

public class TeamDAOImpl implements TeamDAO {
    @Override
    public int create(Team newTeam) {
        return (int) performHibernateOperation(session -> session.save(newTeam), "Could not create entity!");
    }

    @Override
    public Team get(int teamId) {
        return (Team) performHibernateOperation(session -> session.get(Team.class, teamId), String.format("Could not find entity with Id %s", teamId));
    }

    @Override
    public void update(Team team) {
        performHibernateOperation(session -> {
            session.update(team);
            return null;
        }, "Something went wrong when trying to update entity, it probably doesnt exist");
    }

    @Override
    public void addMember(Team team, Employee employee) {
        performHibernateOperation(session -> {
            employee.setTeam(team);
            team.addMember(employee);
            session.update(team);
            return null;
        }, "");
    }

    @Override
    public List<Team> list() {
        return (List<Team>) performHibernateOperation(session -> session.createCriteria(Team.class).list(), "Could not get list of entity");
    }

    @Override
    public int count() {
        return (int) performHibernateOperation(session -> session.createQuery("select count(*) from Team ").uniqueResult(), "Could not get count of entity");
    }
}
