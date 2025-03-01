package onlineshoppingsystemlikeamazon;

import java.util.List;
import java.util.ArrayList;
import lombok.Getter;

@Getter
public class User {

    private final String id;
    private final String name;
    private final String email;
    private final String password;
    private final List<Order> orders;

    public User(String id, String name, String email, String password) {
        this.id = id;
        this.name = name;
        this.email = email;
        this.password = password;
        this.orders = new ArrayList<>();
    }

    public void addOrder(Order order){
        orders.add(order);
    }

}
