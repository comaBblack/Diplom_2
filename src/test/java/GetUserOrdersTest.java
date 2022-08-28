import com.github.javafaker.Faker;
import data.OrderCreationTestData;
import data.UserAuthTestData;
import data.UserCreationTestData;
import io.qameta.allure.junit4.DisplayName;
import io.restassured.response.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.junit.runners.Parameterized;

import java.util.List;

import static org.apache.http.HttpStatus.SC_OK;
import static org.apache.http.HttpStatus.SC_UNAUTHORIZED;
import static steps.GetAuthToken.getAuthToken;
import static steps.GetOrders.getOrder;
import static steps.OrderCreation.createOrder;
import static steps.UserCreation.createUser;

@RunWith(Parameterized.class)
public class GetUserOrdersTest {
    static Faker faker = new Faker();
    static String userEmail = faker.internet().emailAddress();
    static String userPassword = faker.internet().password();
    //для создания пользователя
    private static UserCreationTestData user = UserCreationTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .name(faker.name().name())
            .build();


    private static Response createUserResponse = createUser(user);

    //для авторизации пользователя
    private static UserAuthTestData same_user = UserAuthTestData.builder()
            .email(userEmail)
            .password(userPassword)
            .build();
    private final String token;
    private final String same_token;
    private final int code;

    public GetUserOrdersTest(String token, String same_token, int code) {
        this.token = token;
        this.same_token = same_token;
        this.code = code;
    }

    @Parameterized.Parameters
    public static Object[][] getOrdersData() {
        return new Object[][]{
                {getAuthToken(same_user).toString(), getAuthToken(same_user).toString(), SC_OK},
                {"", "", SC_UNAUTHORIZED}
        };
    }

    @Test
    @DisplayName("Получение заказов авторизованного и неавторизованного  пользовтеля")
    public void getAuthUserOrdersTest() {
        //создание заказа
        OrderCreationTestData ingr = OrderCreationTestData.builder()
                .ingredients(List.of("61c0c5a71d1f82001bdaaa6c", "61c0c5a71d1f82001bdaaa72"))
                .build();
        createOrder(token, ingr);
        //получение заказов
        getOrder(same_token).then().statusCode(code);
    }
}



