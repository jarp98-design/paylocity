package utils;

import io.restassured.response.Response;

import java.util.Map;

import static io.restassured.RestAssured.given;

public class AuthApi {

    public static Map<String, String> loginAndGetCookies() {
        Response response =
                given()
                    .spec(RequestFactory.getRequestSpec())
                .when()
                    .post("/api/login")
                .then()
                    .statusCode(200)
                    .extract()
                    .response();

        return response.getCookies();
    }
}
