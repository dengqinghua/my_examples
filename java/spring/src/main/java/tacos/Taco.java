package tacos;

import lombok.Data;

import javax.validation.constraints.Size;
import java.util.List;

@Data
public class Taco {
    @Size(min = 5, message = "At Lease 5")
    private String name;

    @Size(min = 1, message = "At Least 1")
    private List<String> ingredients;
}
