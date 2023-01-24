package in.reqres.tests;

import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static io.restassured.RestAssured.get;
import static io.restassured.RestAssured.given;
import static io.restassured.http.ContentType.JSON;
import static org.hamcrest.Matchers.is;

public class ReqresTests {

    @Test
    void checkIfPageNumberUsersListIsCorrect() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users?page=1")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("page", is(1));
    }

    @Test
    void checkUserInfo() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users/2")
                .then()
                .log().body()
                .log().status()
                .statusCode(200)
                .body("data.id", is(2),
                        "data.email", is("janet.weaver@reqres.in"),
                        "data.first_name", is("Janet"),
                        "data.last_name", is("Weaver"),
                        "data.avatar", is("https://reqres.in/img/faces/2-image.jpg"));
    }

    @Test
    void checkStatusCodeIfUserDoesNotExist() {
        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void checkSuccessfulUserCreation() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        given()
                .log().uri()
                .log().method()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(201)
                .body("name", is("morpheus"),
                        "job", is("leader"));
    }

    @Test
    void checkSuccessfulUserDeleting() {
        String data = "{ \"name\": \"morpheus\", \"job\": \"leader\" }";

        String userId = given()
                .log().uri()
                .log().method()
                .contentType(JSON)
                .body(data)
                .when()
                .post("https://reqres.in/api/users")
                .then()
                .log().body()
                .log().status()
                .statusCode(201)
                .extract().path("id");

        given()
                .log().uri()
                .log().method()
                .when()
                .delete("https://reqres.in/api/users/" + userId)
                .then()
                .log().body()
                .log().status()
                .statusCode(204)
                .body(Matchers.anything());

        given()
                .log().uri()
                .log().method()
                .when()
                .get("https://reqres.in/api/users/" + userId)
                .then()
                .log().body()
                .log().status()
                .statusCode(404)
                .body(Matchers.anything());
    }
}
