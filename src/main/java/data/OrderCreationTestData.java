package data;

import lombok.Builder;
import lombok.Data;

import java.util.List;

@Data
@Builder
public class OrderCreationTestData {
    public List<String> ingredients;

}
