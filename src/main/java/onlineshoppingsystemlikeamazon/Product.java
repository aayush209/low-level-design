package onlineshoppingsystemlikeamazon;

import lombok.AllArgsConstructor;
import lombok.Getter;

@AllArgsConstructor
@Getter
public class Product {

    private final String id;
    private final String name;
    private final String description;
    private double price;
    private int quantity;

    public boolean isProductAvailable(int requiredQuantity){
        return this.quantity >= requiredQuantity;
    }

    public void updateQuantity(int quantity){
        this.quantity += quantity;
    }
}
