package uber.entities;

import lombok.AllArgsConstructor;
import lombok.Getter;
import uber.enums.Rating;

@AllArgsConstructor
@Getter
public class Rider {

    private final String name;
    private final Rating rating;

}
