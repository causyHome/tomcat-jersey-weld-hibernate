package com.causy.rest.resources;

import com.causy.model.Employee;
import com.causy.persistence.dao.BasicDAO;
import org.assertj.core.api.Assertions;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.asset.EmptyAsset;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import javax.ws.rs.core.Response;
import java.io.File;
import java.net.URISyntaxException;

@RunWith(Arquillian.class)
public class EmployeeResourceAppTest {

    @Inject
    EmployeeResource resource;

    @Deployment
    public static WebArchive createDeployment() {
        File[] lib = Maven.resolver()
                .resolve("org.jboss.weld.servlet:weld-servlet:2.2.9.Final",
                        "el-impl:el-impl:1.0")
                .withTransitivity()
                .as(File.class);


//        WebArchive causyPocWar =  ShrinkWrap.create(WebArchive.class, "test.war")
//                .addClass(EmployeeResource.class)
//                .addAsManifestResource("arquillian.xml")
//                .addAsLibraries(lib)
//                .addAsWebInfResource(EmptyAsset.INSTANCE, "beans.xml")
//                .setWebXML("web.xml");


        WebArchive causyPocWar = ShrinkWrap.createFromZipFile(WebArchive.class, new File("./target/tomcat-jersey-weld-hibernate.war"));
        causyPocWar.addAsLibraries(lib);

        // replacing prod hibernate settings in war with test properties
        causyPocWar.addAsResource(new File("src/test/resources/hibernate.properties"));

        return causyPocWar;
    }

    @Test
    public void test() throws Exception {
        final Response employeeCreationResponse = resource.createEmployee(new Employee("Thomas", "admin"));


        Assertions.assertThat(employeeCreationResponse.getStatus()).isEqualTo(201);
    }
}