package ridesharing;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Passenger {
    private int id;
    private String name;
    private String contact;
    private Location location;
}
