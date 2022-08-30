package steps;

import data.UserCreationTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;
import static data.Endpoints.REGISTRATION_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;

public class UserCreation {
        @Step("create user")
        public static Response createUser(UserCreationTestData user){

            return given()
                    .spec(getBaseSpec())
                    .body(user)
                    .when()
                    .post(REGISTRATION_ENDPOINT);
        }
    }

