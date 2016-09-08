package com.causy.persistence.impl;

import com.causy.model.Employee;
import com.causy.model.Team;
import com.causy.persistence.api.BasicDAO;
import com.causy.persistence.api.TeamDAO;
import org.junit.Before;
import org.junit.Rule;
import org.junit.Test;
import org.junit.rules.ExpectedException;

import static org.assertj.core.api.Assertions.assertThat;

public class BasicDAOImplTest {
    private final BasicDAO basicDAO = new BasicDAOImpl();

    @Rule
    public final ExpectedException expectedException = ExpectedException.none();

    @Before
    public void setUp() {
        basicDAO.deleteAll(Employee.class);
    }


    @Test
    public void should_be_able_to_create_entity_and_then_read_it() throws Exception {
        //when
        final Employee employee = new Employee("Thomas", "test");
        basicDAO.create(employee);
        final Employee actual = (Employee) basicDAO.get(Employee.class, employee.getId());

        //then
        assertThat(actual.getName()).isEqualTo("Thomas");
        assertThat(actual.getRole()).isEqualTo("test");
    }

    @Test
    public void should_not_create_entities_with_the_same_id() throws Exception {
        //when
        final Employee employee1 = new Employee("Thomas", "test");
        basicDAO.create(employee1);
        final Employee employee2 = new Employee("Florian", "test");
        basicDAO.create(employee2);

        final Employee actual = (Employee) basicDAO.get(Employee.class, employee1.getId());

        //then
        assertThat(actual.getName()).isEqualTo("Thomas");
        assertThat(actual.getRole()).isEqualTo("test");
        assertThat(employee1.getId()).isNotEqualTo(employee2.getId());
    }

    @Test
    public void should_be_able_to_count_entities() throws Exception {
        //given
        assertThat(basicDAO.count(Employee.class)).isEqualTo(0);

        //when
        basicDAO.create(new Employee("Thomas", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));


        //then
        assertThat(basicDAO.count(Employee.class)).isEqualTo(4);
    }

    @Test
    public void should_be_able_to_delete_entities() throws Exception {

        //given
        basicDAO.create(new Employee("Thomas", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));

        //when
        basicDAO.deleteAll(Employee.class);

        //then
        assertThat(basicDAO.count(Employee.class)).isEqualTo(0);
    }

    @Test
    public void should_be_able_to_list_entities() throws Exception {
        final TeamDAO teamDAO = new TeamDAOImpl(basicDAO);

        //when
        basicDAO.create(new Employee("Thomas", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        basicDAO.create(new Employee("Florian", "test"));
        teamDAO.create(new Team("team"));
        //then
        assertThat(basicDAO.list(Employee.class).size()).isEqualTo(4);
    }

    @Test
    public void should_be_able_to_update_existing_employee() throws Exception {
        //given
        final Employee employee1 = new Employee("Thomas", "test");
        basicDAO.create(employee1);

        //

        basicDAO.update(new Employee(employee1.getId(), employee1.getName(), "admin"));

        //then
        final Employee employee = (Employee) basicDAO.get(Employee.class, employee1.getId());
        assertThat((employee).getRole()).isEqualTo("admin");
    }
}