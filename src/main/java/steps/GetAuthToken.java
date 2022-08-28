package steps;

import data.UserAuthTestData;
import io.qameta.allure.Step;
import static steps.UserAuth.authUser;

public class GetAuthToken {
        @Step("get auth token")
        public static String getAuthToken(UserAuthTestData user){

            String authTokenB = authUser(user).then().extract().body().path("accessToken").toString();
            String authToken = authTokenB.replace("Bearer ", "");
            return authToken.toString();
        }
    }
