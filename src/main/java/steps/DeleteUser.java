package steps;

import data.UserAuthTestData;
import io.qameta.allure.Step;

import static data.Endpoints.USER_DATA_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;
import static steps.GetAuthToken.getAuthToken;

public class DeleteUser {
    @Step("delete user")
    public static void userDelete(UserAuthTestData user){
        given()
                .spec(getBaseSpec())
                .auth().oauth2(getAuthToken(user).toString())
                .when()
                .delete(USER_DATA_ENDPOINT);
    }
}
