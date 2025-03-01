package elevatorsystem.aps;

import lombok.AllArgsConstructor;
import lombok.Getter;

@Getter
@AllArgsConstructor
public class Request {

    private final int sourceFloor;
    private final int destinationFloor;

}
