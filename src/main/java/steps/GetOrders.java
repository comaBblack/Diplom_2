package steps;

import data.OrderCreationTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static data.Endpoints.ORDER_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;

public class GetOrders {
    @Step("get user's orders")
    public static Response getOrder(String token){

        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token.toString())
                .when()
                .get(ORDER_ENDPOINT);
    }
}
