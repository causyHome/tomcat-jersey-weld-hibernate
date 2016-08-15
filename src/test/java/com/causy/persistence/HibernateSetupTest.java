package com.causy.persistence;

import com.causy.model.Employee;
import com.causy.services.EmployeeService;
import com.causy.services.EmployeeServiceImpl;
import org.hibernate.Session;
import org.hibernate.Transaction;
import org.junit.Test;
import static org.assertj.core.api.Assertions.assertThat;

public class HibernateSetupTest {

    private final EmployeeService service = new EmployeeServiceImpl();

    @Test
    public void should_be_able_to_write_entity_and_then_read_it() {

        service.create(new Employee(1, "Thomas", "test"));

        final Employee actual = service.get(1);

        assertThat(actual.getName()).isEqualTo("Thomas");
        assertThat(actual.getRole()).isEqualTo("test");
    }


    @Test
    public void should_not_be_able_to_write_entity_with_the_same_id_as_another_one() {

        service.create(new Employee(1, "Thomas", "test"));
        service.create(new Employee(1, "Florian", "test"));

        final Employee actual = service.get(1);

        assertThat(actual.getName()).isEqualTo("Thomas");
        assertThat(actual.getRole()).isEqualTo("test");
    }
}
