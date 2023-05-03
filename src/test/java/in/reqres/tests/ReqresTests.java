package in.reqres.tests;

import in.reqres.models.lombok.GetUserResponseLombokModel;
import in.reqres.models.lombok.GetUsersListResponseLombokModel;
import in.reqres.models.lombok.UserCreationBodyLombokModel;
import in.reqres.models.lombok.UserCreationResponseLombokModel;
import org.hamcrest.Matchers;
import org.junit.jupiter.api.Test;

import static in.reqres.specs.Specs.request;
import static in.reqres.specs.Specs.responseSpecification;
import static org.junit.jupiter.api.Assertions.assertEquals;
import static io.restassured.RestAssured.given;

public class ReqresTests{

    @Test
    void checkIfPageNumberUsersListIsCorrect() {
        GetUsersListResponseLombokModel response = given()
                .spec(request)
                .when()
                .get("/users?page=1")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().as(GetUsersListResponseLombokModel.class);

        assertEquals(response.getPage(), 1);
    }

    @Test
    void checkUserInfo() {
        GetUserResponseLombokModel response = given()
                .spec(request)
                .when()
                .get("/users/2")
                .then()
                .spec(responseSpecification)
                .statusCode(200)
                .extract().as(GetUserResponseLombokModel.class);

        assertEquals(response.getData().getId(), 2);
        assertEquals(response.getData().getEmail(), "janet.weaver@reqres.in");
        assertEquals(response.getData().getFirstName(), "Janet");
        assertEquals(response.getData().getLastName(), "Weaver");
        assertEquals(response.getData().getAvatar(), "https://reqres.in/img/faces/2-image.jpg");
    }

    @Test
    void checkStatusCodeIfUserDoesNotExist() {
        given()
                .spec(request)
                .when()
                .get("/users/23")
                .then()
                .log().status()
                .statusCode(404);
    }

    @Test
    void checkSuccessfulUserCreation() {

        UserCreationBodyLombokModel user = new UserCreationBodyLombokModel();
        user.setName("morpheus");
        user.setJob("leader");

        UserCreationResponseLombokModel response = given()
                .spec(request)
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .extract().as(UserCreationResponseLombokModel.class);

        assertEquals(user.getName(), response.getName());
    }

    @Test
    void checkSuccessfulUserDeleting() {
        UserCreationBodyLombokModel user = new UserCreationBodyLombokModel();
        user.setName("morpheus");
        user.setJob("leader");

        UserCreationResponseLombokModel response = given()
                .spec(request)
                .body(user)
                .when()
                .post("/users")
                .then()
                .spec(responseSpecification)
                .statusCode(201)
                .extract().as(UserCreationResponseLombokModel.class);

        given()
                .spec(request)
                .when()
                .delete("/users/" + response.getId())
                .then()
                .spec(responseSpecification)
                .statusCode(204)
                .body(Matchers.anything());

        given()
                .spec(request)
                .when()
                .get("/users/" + response.getId())
                .then()
                .spec(responseSpecification)
                .statusCode(404)
                .body(Matchers.anything());
    }
}
