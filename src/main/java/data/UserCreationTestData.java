package data;

import lombok.Builder;
import lombok.Data;
@Data
@Builder
public class UserCreationTestData {
    public String email;
    public String password;
    public String name;

}
