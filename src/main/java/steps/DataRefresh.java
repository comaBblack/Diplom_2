package steps;

import data.NewUserData;
import data.UserAuthTestData;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static data.Endpoints.USER_DATA_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;
import static steps.GetAuthToken.getAuthToken;

public class DataRefresh {
    @Step("refresh user's data")
    public static Response userDataRefresh(String token, NewUserData newUser){
        return given()
                .spec(getBaseSpec())
                .auth().oauth2(token.toString())
                .body(newUser)
                .when()
                .patch(USER_DATA_ENDPOINT);
    }
}
