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



    @Test
    public void should_delete_team_with_members() throws Exception{
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
            .header("Location", containsString("service/employee")).extract().header("Location");


        strings = locationHeaderValue.split("/");
        String employeeId = strings[strings.length - 1];

        when()
            .put("/tomcat-jersey-weld-hibernate/service/team/" + teamId+ "/member/" + employeeId)
        .then()
            .statusCode(204);


        when()
            .delete("/tomcat-jersey-weld-hibernate/service/team/" + teamId)
        .then()
            .statusCode(204);
    }
}