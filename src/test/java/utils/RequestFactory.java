package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.builder.ResponseSpecBuilder;
import io.restassured.specification.RequestSpecification;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.http.ContentType.JSON;

public class RequestFactory {

    public static RequestSpecification getRequestSpec() {
        return new RequestSpecBuilder()
            .setBaseUri(ConfigAPI.BASE_URI)
            .setBasePath(ConfigAPI.BASE_PATH)
            .addHeader("Authorization", ConfigAPI.AUTH_HEADER)
            .setContentType(JSON)
            .build();
    }

    public static ResponseSpecification getResponseSpec() {
        return new ResponseSpecBuilder()
            .expectStatusCode(200)
            .expectContentType(JSON)
            .build();
    }
}