import com.github.javafaker.Faker;
import data.OrderCreationTestData;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import org.junit.After;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.*;
import static org.hamcrest.Matchers.notNullValue;
import static steps.DeleteUser.userDelete;
import static steps.GetAuthToken.getAuthToken;
import static steps.OrderCreation.createOrder;
import static steps.UserCreation.createUser;

@RunWith(Parameterized.class)
public class OrderCreationParamTest {
    static Faker faker = new Faker();
    static String userEmail = faker.internet().emailAddress();
    static String userPassword = faker.internet().password();
    //для создания пользователя
    private static UserCreationTestData user = UserCreationTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .name(faker.name().name())
            .build();
    //для авторизации пользователя
    private static UserAuthTestData same_user = UserAuthTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .build();

    private final List<String> ingredients;
    private final int code;

    public OrderCreationParamTest(List<String> ingredients, int code) {
        this.ingredients = ingredients;
        this.code = code;
    }


    @Parameterized.Parameters
    public static Object[][] orderCreationData() {
        return new Object[][]{
                {List.of("61c0c5a71d1f82001bdaaa6c"), SC_OK},
                {List.of("61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa72"), SC_OK},
                {List.of(), SC_BAD_REQUEST},
                {List.of("61c0c5a71d1f8299999999", "61c0c5a71d1f82001bdaaa72"), SC_INTERNAL_SERVER_ERROR},

        };
    }

    @Test
    @DisplayName("Создание заказа")
    public void createOrderTest() {
        createUser(user);
        OrderCreationTestData ingr = OrderCreationTestData.builder()
                .ingredients(ingredients)
                .build();
        String token = getAuthToken(same_user).toString();
        createOrder(token, ingr).then().statusCode(code);
        //удаление пользователя
        userDelete(same_user);
    }

    @Test
    @DisplayName("Создание заказа без авторизации")
    public void createOrderWithoutAuthIsErrorTest() {
        OrderCreationTestData ingr = OrderCreationTestData.builder()
                .ingredients(ingredients)
                .build();
        String token = "";
        createOrder(token, ingr).then().statusCode(code);
    }
}