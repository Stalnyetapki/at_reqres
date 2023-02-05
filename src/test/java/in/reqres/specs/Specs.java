package in.reqres.specs;

import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static in.reqres.helpers.CustomApiListener.withCustomTemplates;
import static io.restassured.RestAssured.with;
import static io.restassured.filter.log.LogDetail.BODY;
import static io.restassured.filter.log.LogDetail.STATUS;
import static io.restassured.http.ContentType.JSON;

public class Specs {
    public static RequestSpecification request = with()
            .log().uri()
            .log().method()
            .filter(withCustomTemplates())
            .contentType(JSON);

    public static ResponseSpecification responseSpecification = new ResponseSpecBuilder()
            .log(STATUS)
            .log(BODY)
            .build();
}
