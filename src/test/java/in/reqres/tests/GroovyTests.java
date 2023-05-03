package in.reqres.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.Specs.request;
import static in.reqres.specs.Specs.responseSpecification;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.hasItem;

public class GroovyTests {

    @Test
    @DisplayName("Check users body by groovy")
    void checkUsersContent() {
                given()
                .spec(request)
                .when()
                .get("/users?page=1")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .body("data.id.flatten()", hasItem(6))
                .body("data.findAll{it.avatar =~/faces/}.avatar.flatten()",
                        hasItem("https://reqres.in/img/faces/4-image.jpg"))
                .body("data.findAll{it.email =~/.*?@reqres.in/}.email.flatten()",
                        hasItem("emma.wong@reqres.in"));
    }
}
