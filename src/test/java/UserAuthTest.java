import com.github.javafaker.Faker;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;

import static org.hamcrest.CoreMatchers.notNullValue;
import static steps.UserAuth.authUser;
import static steps.UserCreation.createUser;

public class UserAuthTest {

    @Test
    @DisplayName("Успешная авторизация пользователя")
    public void successfulUserAuthTest(){
        Faker faker = new Faker();
        String email =  faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().name();

        UserCreationTestData user = UserCreationTestData.builder()
                .email(email)
                .password(password)
                .name(name)
                .build();
        //создание пользователя
        createUser(user);

        UserAuthTestData user_logpass = UserAuthTestData.builder()
                .email(email)
                .password(password)
                .build();
        //авторизация под созданным логпассом
        authUser(user_logpass).then().statusCode(200).and().assertThat().body(notNullValue());
    }
}
