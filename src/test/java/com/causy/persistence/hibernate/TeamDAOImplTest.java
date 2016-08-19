package com.causy.persistence.hibernate;

import com.causy.model.Team;
import com.causy.persistence.dao.TeamDAO;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class TeamDAOImplTest {
    private final TeamDAO teamDAO = new TeamDAOImpl();

    @Rule
    public ExpectedException expectedException = ExpectedException.none();

    @Test
    public void should_be_able_to_create_entity_and_then_read_it() throws Exception {
        //when
        final int id = teamDAO.create(new Team("test"));
        final Team actual = teamDAO.get(id);

        //then
        assertThat(actual.getName()).isEqualTo("test");
    }

    @Test
    public void should_not_create_entities_with_the_same_id() throws Exception {
        //when
        final int id1 = teamDAO.create(new Team("Team1"));
        final int id2 = teamDAO.create(new Team("Team2"));

        final Team actual = teamDAO.get(id1);

        //then
        assertThat(actual.getName()).isEqualTo("Team1");
        assertThat(id1).isNotEqualTo(id2);
    }

    @Test
    public void should_be_able_to_update_existing_team() throws Exception {
        //given
        final int id = teamDAO.create(new Team("Team1"));

        //when
        teamDAO.update(new Team(id, "Team2"));

        //then
        assertThat(teamDAO.get(id).getName()).isEqualTo("Team2");
    }

    @Test
    public void should_not_be_able_to_update_non_existing_team() throws Exception {
        //given
        final int id = teamDAO.create(new Team("Team1"));

        //then
        expectedException.expect(IllegalStateException.class);

        //when
        teamDAO.update(new Team(id + 1, "Team2"));

        //then
        assertThat(teamDAO.get(id).getName()).isEqualTo("Team1");
    }
}