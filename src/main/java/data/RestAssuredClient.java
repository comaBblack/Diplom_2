package data;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.specification.RequestSpecification;

    public class RestAssuredClient {
        public static RequestSpecification getBaseSpec(){
            return new RequestSpecBuilder()
                    .setBaseUri("https://stellarburgers.nomoreparties.site")
                    .setContentType("application/json")
                    .build();
        }
}
