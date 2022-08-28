import com.github.javafaker.Faker;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import static org.hamcrest.Matchers.equalTo;
import static steps.DeleteUser.userDelete;
import static steps.UserAuth.authUser;
import static steps.UserCreation.createUser;

public class UserCreationTest {

    @Test
    @DisplayName("Создание уникального пользователя")
    public void uniqueUserCreationTest (){

        Faker faker = new Faker();
        String email =  faker.internet().emailAddress();
        String password = faker.internet().password();
        String name = faker.name().name();

       UserCreationTestData user = UserCreationTestData.builder()
               .email(email)
               .password(password)
               .name(name)
               .build();

       createUser(user).then()
               .statusCode(200);
       //удаление пользователя
        UserAuthTestData user_logpass = UserAuthTestData.builder()
                .email(email)
                .password(password)
                .build();
        authUser(user_logpass);
        userDelete(user_logpass);
    }

    @Test
    @DisplayName("Создание пользователя, который уже зарегистрирован")
    public void notUniqueUserCreationTest (){

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
        //создание пользователя с теми же данными
        createUser(user).then().statusCode(403).and().assertThat().body("message", equalTo("User already exists"));

        //удаление пользователя
        UserAuthTestData user_logpass = UserAuthTestData.builder()
                .email(email)
                .password(password)
                .build();
        authUser(user_logpass);
        userDelete(user_logpass);
    }
}
