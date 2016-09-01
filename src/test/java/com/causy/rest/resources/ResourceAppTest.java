package com.causy.rest.resources;

import io.restassured.http.ContentType;
import org.jboss.arquillian.container.test.api.Deployment;
import org.jboss.arquillian.junit.Arquillian;
import org.jboss.shrinkwrap.api.ShrinkWrap;
import org.jboss.shrinkwrap.api.spec.WebArchive;
import org.jboss.shrinkwrap.resolver.api.maven.Maven;
import org.junit.Test;
import org.junit.runner.RunWith;

import java.io.File;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

@RunWith(Arquillian.class)
public class ResourceAppTest {

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
    public void should_create_employee() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"Thomas-REST\", \"role\":\"admin\"}")
            .post("/tomcat-jersey-weld-hibernate/service/employee")
        .then()
            .statusCode(201)
            .header("Location", containsString("service/employee"));
    }


    @Test
    public void should_list_employees() throws Exception {
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"employee1\", \"role\":\"admin\"}")
            .post("/tomcat-jersey-weld-hibernate/service/employee");
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"employee2\", \"role\":\"admin\"}")
            .post("/tomcat-jersey-weld-hibernate/service/employee");


        when()
            .get("/tomcat-jersey-weld-hibernate/service/employee")
        .then()
            .statusCode(200)
            .body("name[0]", equalTo("employee1"))
            .body("role[0]", equalTo("admin"))
            .body("name[1]", equalTo("employee2"))
            .body("role[1]", equalTo("admin"));
    }

    @Test
    public void should_create_team() throws Exception{
        given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"team\"}")
            .post("/tomcat-jersey-weld-hibernate/service/team")
        .then()
            .statusCode(201)
            .header("Location", containsString("service/team"));
    }

    @Test
    public void should_add_members_to_team() throws Exception{
       String locationHeaderValue =  given()
            .contentType(ContentType.JSON)
            .body("{\"name\": \"team\"}")
            .post("/tomcat-jersey-weld-hibernate/service/team")
       .then()
           .statusCode(201)
           .header("Location", containsString("service/team"))
       .extract().header("Location");

        String[] strings = locationHeaderValue.split("/");
        String teamId = strings[strings.length - 1];

        locationHeaderValue = given()
           .contentType(ContentType.JSON)
           .body("{\"name\": \"employee\", \"role\":\"admin\"}")
           .post("/tomcat-jersey-weld-hibernate/service/employee")
       .then()
            .statusCode(201)
            .header("Location", containsString("service/employee"))
            .extract().header("Location");

        strings = locationHeaderValue.split("/");
        String employeeId = strings[strings.length - 1];

        when()
            .put("/tomcat-jersey-weld-hibernate/service/team/" + teamId+ "/member/" + employeeId)
        .then()
            .statusCode(204);

    }
}