package utils;

import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;
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
}