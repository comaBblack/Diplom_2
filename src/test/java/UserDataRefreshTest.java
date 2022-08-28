import com.github.javafaker.Faker;
import data.NewUserData;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static data.Endpoints.USER_DATA_ENDPOINT;
import static data.RestAssuredClient.getBaseSpec;
import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;
import static org.hamcrest.Matchers.notNullValue;
import static steps.DataRefresh.userDataRefresh;
import static steps.DeleteUser.userDelete;
import static steps.GetAuthToken.getAuthToken;
import static steps.UserCreation.createUser;

@RunWith(Parameterized.class)
public class UserDataRefreshTest {
  static Faker faker = new Faker();
    static String userEmail = faker.internet().emailAddress();
    static String userPassword = faker.internet().password();
    private static UserCreationTestData user = UserCreationTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .name(faker.name().name())
            .build();
    private static UserAuthTestData same_user = UserAuthTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .build();

    private final String newEmail;
    private final String newName;


    public UserDataRefreshTest(String newEmail, String newName){
        this.newEmail = newEmail;
        this.newName = newName;
    }
    @After
    public void deleteUserAfterTest(){
        userDelete(same_user);
    }

    @Parameterized.Parameters
    public static Object[][] userRefreshAuthData(){
        return new Object[][]{
                {user.getEmail(), user.getName()},
                {faker.internet().emailAddress(), user.getName()},
                {user.getEmail(), faker.name().name()},
                {faker.internet().emailAddress(), ""},
                {faker.internet().emailAddress(),faker.name().name()},
                {"", ""}
        };
    }
    @Test
    @DisplayName("Изменение данных авторизованного пользователя")
    public void authUserDataRefreshTest(){
        NewUserData new_user = NewUserData.builder()
                .newEmail(newEmail)
                .newName(newName)
                .build();
        //создание пользователя
        createUser(user);
        String token = getAuthToken(same_user).toString();
        userDataRefresh(token, new_user).then().statusCode(200).and().assertThat().body("user", notNullValue());
    }

    @Test
    @DisplayName("Изменение данных неавторизованного пользователя")
    public void withoutAuthUserDataRefreshIsErrorTest(){
        NewUserData new_user = NewUserData.builder()
                .newEmail(newEmail)
                .newName(newName)
                .build();
        //создание пользователя
        createUser(user);
        String token = "";
        userDataRefresh(token, new_user).then().statusCode(401).and().assertThat().body("message", equalTo("You should be authorised"));
    }
}
