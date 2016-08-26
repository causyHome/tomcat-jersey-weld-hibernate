package com.causy.rest.resources;

import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import javax.inject.Inject;
import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class EmployeeResourceAppTest {

    @Inject
    EmployeeResource resource;

    @Deployment(testable = false)
    public static WebArchive createDeployment() {
        File[] lib = Maven.resolver()
                .resolve("el-impl:el-impl:1.0")
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
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"Thomas-REST\", \"role\":\"admin\"}")
            .post("/tomcat-jersey-weld-hibernate/service/employee");

        when()
            .get("/tomcat-jersey-weld-hibernate/service/employee")
        .then()
            .statusCode(200)
            .body("name[0]", equalTo("Thomas-REST"))
            .body("role[0]", equalTo("admin"));
    }
}