package steps;

import data.UserAuthTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static data.Endpoints.AUTHORIZATION_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;

public class UserAuth {
    @Step("user auth")
    public static Response authUser(UserAuthTestData user){

        return given()
                .spec(getBaseSpec())
                .body(user)
                .when()
                .post(AUTHORIZATION_ENDPOINT);
    }
}
