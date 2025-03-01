package onlineshoppingsystemlikeamazon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class OrderItem {

    private final Product product;
    private final int quantity;
}
