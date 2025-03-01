package onlineshoppingsystemlikeamazon;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public class ShoppingCart {

    private final Map<String, OrderItem> items;

    public ShoppingCart() {
        this.items = new HashMap<>();
    }

    public boolean addProductToCart(Product product, int quantity){
        String productId = product.getId();

        //if desired quantity is unavailable
        if(!product.isProductAvailable(quantity))
            return false;

        int newQuantity = quantity;
        if(items.containsKey(productId)){
            newQuantity += items.get(productId).getQuantity();
        }
        items.put(productId, new OrderItem(product, newQuantity));
        return true;
    }

    public void removeItemFromCart(Product product){
        items.remove(product.getId());
    }

    public boolean updateProductQuantityToASpecificNumber(String productId, int quantity){
        OrderItem orderItem = items.get(productId);

        if (orderItem != null && orderItem.getProduct().isProductAvailable(quantity)){
            items.put(productId, new OrderItem(orderItem.getProduct(), quantity));
            return true;
        }

        return false;
    }

    public List<OrderItem> getItems() {
        return new ArrayList<>(items.values());
    }

    public void clear() {
        items.clear();
    }
}

