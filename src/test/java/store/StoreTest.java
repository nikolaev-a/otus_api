package store;

import com.github.javafaker.Faker;
import dto.StoreDTO;

import io.restassured.module.jsv.JsonSchemaValidator;
import io.restassured.response.ValidatableResponse;
import org.apache.http.HttpStatus;
import org.junit.jupiter.api.*;
import services.ServiceApi;

@TestMethodOrder(MethodOrderer.OrderAnnotation.class)
public class StoreTest {

    private final ServiceApi serviceApi = new ServiceApi();
    private final static Faker faker = new Faker();

    private final static Long id = faker.number().randomNumber();
    private final static Long petId = faker.number().randomNumber();
    private final static Long quantity = faker.number().randomNumber();
    private final static String status = faker.lorem().sentence();

    @Order(1)
    @Test
    // создаем магазин
    // проверяем статус код 200 и id равен ожидаемому и поле snipDate не является обязательным
    public void createStore(){

        StoreDTO  storeDTO = StoreDTO.builder()
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

    @Order(2)
    @Test
    // проверяем, что магазин создан
    // проверяем статус код 200 и actualPetId и actualStatus что они равны ожидаемому результату
    public void readStore(){
        ValidatableResponse response = serviceApi.readStore("/" + id);
        response.statusCode(HttpStatus.SC_OK);
        int actualPetId = response.extract().jsonPath().get("petId");
        String actualStatus = response.extract().jsonPath().get("status");

        Assertions.assertEquals(petId, actualPetId);
        Assertions.assertEquals(status, actualStatus);
    }

    @Order(3)
    @Test
    // удаляем магазин
    // проверяем статус код 200 и message что равно ожидаемому результату
    public void deleteStore(){
        ValidatableResponse response = serviceApi.deleteStore("/" + id);
        response.statusCode(HttpStatus.SC_OK);
        String actualMessage = response.extract().jsonPath().get("message");

        Assertions.assertEquals(String.valueOf(id), actualMessage);
    }

    @Order(4)
    @Test
    // проверяем, что магазин удален
    // проверяем статус код 404 и message и type что они равны ожидаемому результату
    public void readStore404(){
        ValidatableResponse response = serviceApi.readStore("/" + id);
        response.statusCode(HttpStatus.SC_NOT_FOUND);
        String actualMessage = response.extract().jsonPath().get("message");
        String actualType = response.extract().jsonPath().get("type");

        Assertions.assertEquals("Order not found", actualMessage);
        Assertions.assertEquals("error", actualType);

    }

    @Order(5)
    @Test
    public void inventoryStore(){
        serviceApi.inventoryStore();
    }
}