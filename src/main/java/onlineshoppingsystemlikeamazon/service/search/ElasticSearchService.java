package onlineshoppingsystemlikeamazon.service.search;

import java.util.List;
import java.util.Map;
import java.util.stream.Collectors;
import onlineshoppingsystemlikeamazon.Product;

public class ElasticSearchService implements SearchService {

    @Override
    public List<Product> searchProducts(String keyword, Map<String, Product> products) {
        return products.values().stream()
                .filter(product -> product.getName().toLowerCase().contains(keyword.toLowerCase()))
                .collect(Collectors.toList());
    }
}
