package com.foodfast.product_service.service;
import java.util.List;
import java.util.Optional;
import org.springframework.stereotype.Service;
import com.foodfast.product_service.model.Product;
import com.foodfast.product_service.repository.ProductRepository;
@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService (ProductRepository productRepository){
        this.productRepository  = productRepository;
    }

    public List<Product> getAllProducts() {
        return productRepository.findAll();
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product) {
        return productRepository.save(product);
    }

    public Product updateProduct(String id, Product product) {
        if (productRepository.existsById(id)) {
            product.setId(id);
            return productRepository.save(product);
        }
        return null;
    }
    
    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }
}
