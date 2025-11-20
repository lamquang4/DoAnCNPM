package com.foodfast.product_service.service;
import java.io.IOException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import java.io.File;
import com.foodfast.product_service.model.Product;
import com.foodfast.product_service.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;

    public ProductService(ProductRepository productRepository){
        this.productRepository  = productRepository;
    }

    public Page<Product> getAllProducts(String q, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        if (q != null && !q.isBlank()) {
            return productRepository.findByNameContainingIgnoreCase(q, pageable);
        }
        return productRepository.findAll(pageable);
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Product createProduct(Product product, MultipartFile imageFile) throws IOException {
    if (productRepository.existsByName(product.getName())) {
        throw new IllegalArgumentException("Tên đã tồn tại");
    }
    if (product.getStatus() == null) product.setStatus(1);

    Product saved = productRepository.save(product);

    if (imageFile != null && !imageFile.isEmpty()) {
        String uploadDir = "uploads/product/" + saved.getId();
        File dir = new File(uploadDir);
        if (!dir.exists()) dir.mkdirs();

        String filePath = uploadDir + "/" + imageFile.getOriginalFilename();
        imageFile.transferTo(new File(filePath));

        saved.setImage(filePath);
        saved = productRepository.save(saved);
    }

    return saved;
}

public Product updateProduct(String id, Product product, MultipartFile imageFile) throws IOException {
    return productRepository.findById(id).map(existing -> {
        if (!existing.getName().equals(product.getName()) && productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Tên đã tồn tại");
        }

        existing.setName(product.getName() != null ? product.getName() : existing.getName());
        existing.setPrice(product.getPrice() != null ? product.getPrice() : existing.getPrice());
        existing.setStatus(product.getStatus() != null ? product.getStatus() : existing.getStatus());

        try {
            if (imageFile != null && !imageFile.isEmpty()) {
                String uploadDir = "uploads/product/" + existing.getId();
                File dir = new File(uploadDir);
                if (!dir.exists()) dir.mkdirs();

                String filePath = uploadDir + "/" + imageFile.getOriginalFilename();
                imageFile.transferTo(new File(filePath));

                existing.setImage(filePath);
            }
        } catch (IOException e) {
            throw new RuntimeException("Lưu hình thất bại", e);
        }

        return productRepository.save(existing);
    }).orElse(null);
}

    public void deleteProduct(String id) {
        productRepository.deleteById(id);
    }

public Product updateProductStatus(String id, int status) {
    return productRepository.findById(id).map(product -> {
        product.setStatus(status);
        return productRepository.save(product);
    }).orElse(null);
}

}
