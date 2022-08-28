import com.github.javafaker.Faker;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;
import static org.hamcrest.Matchers.equalTo;
import static steps.UserCreation.createUser;


@RunWith(Parameterized.class)
public class UserCreationParamTest {

    private final  String email;
    private final String password;
    private final String name;
    static Faker faker = new Faker();

    public UserCreationParamTest(String email, String password, String name) {
        this.email = email;
        this.password = password;
        this.name = name;
    }

    @Parameterized.Parameters
    public static Object[][] getOrderData() {
        return new Object[][]{
                {"",faker.internet().password(), faker.name().name()},
                {faker.internet().emailAddress(), faker.internet().password(), ""},
                {faker.internet().emailAddress(), "", faker.name().name()},
                {"","",""}

        }; }

        @Test
        @DisplayName("Создание пользователя без обязательных полей")
        public void emptyRequiredFieldsUserCreationTest(){
            UserCreationTestData user = UserCreationTestData.builder()
                    .email(email)
                    .password(password)
                    .name(name)
                    .build();

            createUser(user).then().statusCode(403).and().assertThat().body("message", equalTo("Email, password and name are required fields"));
        }
}
