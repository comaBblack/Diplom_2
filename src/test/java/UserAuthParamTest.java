import com.github.javafaker.Faker;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import static org.hamcrest.core.IsEqual.equalTo;
import static steps.DeleteUser.userDelete;
import static steps.UserAuth.authUser;
import static steps.UserCreation.createUser;

@RunWith(Parameterized.class)
public class UserAuthParamTest {

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

    private final String email;
    private final String password;

    public UserAuthParamTest(String email, String password){
        this.email = email;
        this.password = password;
    }

    @Before
    public void userCreation(){
        createUser(user);
    }

    @After
    public void deleteUserAfterTest(){
        userDelete(same_user);
    }


    @Parameterized.Parameters
    public static Object[][] userAuthData(){
        return new Object[][]{
                {"", user.getPassword()},
                {user.getEmail(), ""},
                {faker.internet().emailAddress(), user.getPassword()},
                {user.getEmail(), faker.internet().password()},
                {faker.internet().emailAddress(),faker.internet().password()},
                {"", ""}
        };
    }

    @Test
    @DisplayName("Логин с неверным логином и паролем")
    public void incorrectLogPassAuthIsError(){
        UserAuthTestData user_logpass = UserAuthTestData.builder()
                .email(email)
                .password(password)
                .build();

        authUser(user_logpass).then().statusCode(401).and().assertThat().body("message", equalTo("email or password are incorrect"));
    }
}
