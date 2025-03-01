package onlineshoppingsystemlikeamazon.service.search;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import onlineshoppingsystemlikeamazon.Product;

public interface SearchService {

    public List<Product> searchProducts(String keyword, Map<String, Product> products);
}
