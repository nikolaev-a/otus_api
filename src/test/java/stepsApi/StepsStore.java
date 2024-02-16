package stepsApi;

import com.github.javafaker.Faker;
import dto.StoreDTO;
import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import services.ServiceApi;

public class StepsStore {

    private final ServiceApi serviceApi = new ServiceApi();
    private final static Faker faker = new Faker();

    private final static Long id = faker.number().randomNumber();
    private final static Long petId = faker.number().randomNumber();
    private final static Long quantity = faker.number().randomNumber();
    private final static String status = faker.lorem().sentence();


    public void createStore(){

        StoreDTO storeDTO = StoreDTO.builder()
                .id(id)
                .petId(petId)
                .quantity(quantity)
                .status(status)
                .complete(true)
                .build();

        ValidatableResponse response = serviceApi.createStore(storeDTO);
        response.statusCode(HttpStatus.SC_OK);
        // подключаем валидатор
        response.body(JsonSchemaValidator.matchesJsonSchemaInClasspath("schema/CreateStore.json"));
        int actualID = response.extract().jsonPath().get("id");

        Assertions.assertEquals(id, actualID);
    }

    public void readStore(){
        ValidatableResponse response = serviceApi.readStore("/" + id);
        response.statusCode(HttpStatus.SC_OK);
        int actualPetId = response.extract().jsonPath().get("petId");
        String actualStatus = response.extract().jsonPath().get("status");

        Assertions.assertEquals(petId, actualPetId);
        Assertions.assertEquals(status, actualStatus);
    }


    public void deleteStore(){
        ValidatableResponse response = serviceApi.deleteStore("/" + id);
        response.statusCode(HttpStatus.SC_OK);
        String actualMessage = response.extract().jsonPath().get("message");

        Assertions.assertEquals(String.valueOf(id), actualMessage);
    }


    public void readStore404(){
        ValidatableResponse response = serviceApi.readStore("/" + id);
        response.statusCode(HttpStatus.SC_NOT_FOUND);
        String actualMessage = response.extract().jsonPath().get("message");
        String actualType = response.extract().jsonPath().get("type");

        Assertions.assertEquals("Order not found", actualMessage);
        Assertions.assertEquals("error", actualType);

    }
}