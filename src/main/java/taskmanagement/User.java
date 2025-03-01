package taskmanagement;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class User {

    private final String id;
    private final String name;
    private final String email;
}
