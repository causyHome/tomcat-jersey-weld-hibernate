package com.causy.persistence.impl;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.api.TeamDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import java.util.Collections;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDAOImplTest {
    private final TeamDAO teamDAO = new TeamDAOImpl(new BasicDAOImpl());

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_be_able_to_create_entity_and_then_read_it() throws Exception {
        //when
        Team team  = (Team) teamDAO.create(new Team("test"));
        final Team actual = teamDAO.get(team.getId());

        //then
        assertThat(actual.getName()).isEqualTo("test");
    }

    @Test
    public void should_not_create_entities_with_the_same_id() throws Exception {
        //when
        final int id1 = teamDAO.create(new Team("Team1")).getId();
        final int id2 = teamDAO.create(new Team("Team2")).getId();

        final Team actual = teamDAO.get(id1);

        //then
        assertThat(actual.getName()).isEqualTo("Team1");
        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void should_be_able_to_update_existing_team() throws Exception {
        //given
        final Team team = teamDAO.create(new Team("Team1"));

        //when
        teamDAO.update(new Team(team.getId(), "Team2", Collections.emptyList()));

        //then
        assertThat(teamDAO.get(team.getId()).getName()).isEqualTo("Team2");
    }
    
    @Test
    public void should_be_able_to_add_employee_to_existing_team() throws Exception {
        //given
        final int id = teamDAO.create(new Team("Team1")).getId();

        //when
        final Employee employee = new Employee("employee", "role");
        teamDAO.addMember(new Team(id, "Team1"), employee);

        //then
        final Team savedTeam = teamDAO.get(id);
        assertThat(savedTeam.getName()).isEqualTo("Team1");
        assertThat(savedTeam.getMembers()).isNotEmpty();

        final Employee savedEmployee = savedTeam.getMembers().get(0);
        assertThat(savedEmployee.getName()).isEqualTo("employee");
        assertThat(savedEmployee.getRole()).isEqualTo("role");
        assertThat(savedEmployee.getRole()).isEqualTo("role");

        assertThat(savedEmployee.getTeam().getId()).isEqualTo(id);
    }
}