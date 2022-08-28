package steps;

import data.OrderCreationTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static data.Endpoints.ORDER_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;


public class OrderCreation {
    @Step("make an order")
    public static Response createOrder(String token, OrderCreationTestData ingredients){

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token.toString())
                .body(ingredients)
                .when()
                .post(ORDER_ENDPOINT);
    }
}
