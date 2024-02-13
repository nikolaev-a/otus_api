package services;

import dto.StoreDTO;
import io.restassured.http.ContentType;
import io.restassured.response.ValidatableResponse;
import io.restassured.specification.RequestSpecification;

import static io.restassured.RestAssured.given;

public class ServiceApi {

    private final String BASE_URL = "https://petstore.swagger.io/v2";
    private final String BASE_PATH = "/store/order";
    private final RequestSpecification spec;

    public ServiceApi() {
        spec = given()
                .baseUri(BASE_URL)
                .contentType(ContentType.JSON)
                .log().all();
    }

    public ValidatableResponse createStore(StoreDTO store) {
        return given(spec)
                .basePath(BASE_PATH)
                .body(store)
                .when()
                .post()
                .then()
                .log().all();
    }

    public ValidatableResponse readStore(String id) {
        return given(spec)
                .basePath(BASE_PATH + id)
                .when()
                .get()
                .then()
                .log().all();
    }

    public ValidatableResponse deleteStore(String id) {
        return given(spec)
                .basePath(BASE_PATH + id)
                .when()
                .delete()
                .then()
                .log().all();
    }

    public ValidatableResponse inventoryStore() {
        return given(spec)
                .basePath("/store/inventory")
                .when()
                .get()
                .then()
                .log().all();
    }
}