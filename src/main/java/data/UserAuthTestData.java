package data;

import lombok.Builder;
import lombok.Data;

@Data
@Builder
public class UserAuthTestData {
    public String email;
    public String password;
}
