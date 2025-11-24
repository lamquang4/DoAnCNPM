package com.foodfast.product_service.service;
import java.io.IOException;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.util.List;
import java.util.Map;
import java.util.NoSuchElementException;
import java.util.Optional;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.PageImpl;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import org.springframework.stereotype.Service;
import org.springframework.web.multipart.MultipartFile;
import com.cloudinary.utils.ObjectUtils;
import com.cloudinary.Cloudinary;
import com.foodfast.product_service.client.RestaurantClient;
import com.foodfast.product_service.dto.ProductDTO;
import com.foodfast.product_service.dto.RestaurantDTO;
import com.foodfast.product_service.model.Product;
import com.foodfast.product_service.repository.ProductRepository;

@Service
public class ProductService {

    private final ProductRepository productRepository;
    private final RestaurantClient restaurantClient;
    private final Cloudinary cloudinary;

    public ProductService(ProductRepository productRepository, RestaurantClient restaurantClient, Cloudinary cloudinary){
        this.productRepository  = productRepository;
        this.restaurantClient = restaurantClient;
        this.cloudinary = cloudinary;
    }

    public Page<ProductDTO> getAllProducts(String q, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        Page<Product> products = (q != null && !q.isBlank())
                ? productRepository.findByNameContainingIgnoreCase(q, pageable)
                : productRepository.findAll(pageable);

        List<ProductDTO> dtoList = products.getContent().stream()
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, products.getTotalElements());
    }

    public Optional<Product> getProductById(String id) {
        return productRepository.findById(id);
    }

    public Page<ProductDTO> getActiveProducts(int page, int limit, String q) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<Product> products = (q != null && !q.isBlank())
                ? productRepository.findByStatusAndNameContainingIgnoreCase(1, q, pageable)
                : productRepository.findByStatus(1, pageable);

        List<ProductDTO> list = products.getContent().stream()
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(list, pageable, products.getTotalElements());
    }

    public List<ProductDTO> getAllProductsByRestaurantId(String restaurantId) {
        List<Product> products = productRepository.findByRestaurantId(restaurantId);
        return products.stream().map(this::toDTO).toList();
    }

    public Page<ProductDTO> getProductsByUserId(String userId, int page, int limit, String q) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));

        List<RestaurantDTO> restaurants = restaurantClient.getRestaurantsByUserIdSimple(userId);

        if (restaurants.isEmpty()) {
            return new PageImpl<>(List.of(), pageable, 0);
        }

        List<String> restaurantIds = restaurants.stream()
                .map(RestaurantDTO::getId)
                .toList();

        Page<Product> productPage = (q != null && !q.isBlank())
                ? productRepository.findByRestaurantIdInAndNameContainingIgnoreCase(restaurantIds, q, pageable)
                : productRepository.findByRestaurantIdIn(restaurantIds, pageable);

        List<ProductDTO> dtoList = productPage.getContent().stream()
                .map(this::toDTO)
                .toList();

        return new PageImpl<>(dtoList, pageable, productPage.getTotalElements());
    }

    public Product createProduct(Product product, MultipartFile imageFile) throws IOException {
        if (productRepository.existsByName(product.getName())) {
            throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
        }

        if (product.getStatus() == null) product.setStatus(1);
        Product saved = productRepository.save(product);

        if (imageFile != null && !imageFile.isEmpty()) {
            try {
                String imageUrl = uploadToCloudinary(imageFile, saved.getId());
                saved.setImage(imageUrl);
                saved = productRepository.save(saved);
            } catch (IOException e) {
                throw new RuntimeException("Upload hình thất bại", e);
            }
        }

        return saved;
    }

    public Product updateProduct(String id, Product product, MultipartFile imageFile) throws IOException {
        return productRepository.findById(id).map(existing -> {
            if (!existing.getName().equals(product.getName()) && productRepository.existsByName(product.getName())) {
                throw new IllegalArgumentException("Tên sản phẩm đã tồn tại");
            }

            existing.setName(product.getName() != null ? product.getName() : existing.getName());
            existing.setPrice(product.getPrice() != null ? product.getPrice() : existing.getPrice());
            existing.setRestaurantId(product.getRestaurantId() != null ? product.getRestaurantId() : existing.getRestaurantId());
            existing.setStatus(product.getStatus() != null ? product.getStatus() : existing.getStatus());

            if (imageFile != null && !imageFile.isEmpty()) {
                try {
                    String imageUrl = uploadToCloudinary(imageFile, existing.getId());
                    existing.setImage(imageUrl);
                } catch (IOException e) {
                    throw new RuntimeException("Upload hình thất bại", e);
                }
            }

            return productRepository.save(existing);
        }).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + id));
    }

    public Product updateProductStatus(String id, int status) {
        return productRepository.findById(id).map(product -> {
            product.setStatus(status);
            return productRepository.save(product);
        }).orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + id));
    }

    public void deleteProduct(String id) {
        Product product = productRepository.findById(id)
                .orElseThrow(() -> new NoSuchElementException("Không tìm thấy sản phẩm với ID: " + id));

        if (product.getImage() != null && !product.getImage().isBlank()) {
            try {
                String folderPath = "foodfast/product/" + product.getId();
                cloudinary.api().deleteResourcesByPrefix(folderPath, ObjectUtils.emptyMap());
            } catch (Exception e) {
                throw new RuntimeException("Xóa ảnh trên Cloudinary thất bại", e);
            }
        }

        productRepository.deleteById(id);
    }

    private String uploadToCloudinary(MultipartFile file, String productId) throws IOException {
        String folderPath = "foodfast/product/" + productId;

        Map uploadResult = cloudinary.uploader().upload(file.getBytes(), ObjectUtils.asMap(
                "folder", folderPath,
                "public_id", productId, 
                "overwrite", true
        ));
        return uploadResult.get("secure_url").toString();
    }

    private ProductDTO toDTO(Product product) {
        RestaurantDTO restaurant = null;

        try {
            restaurant = restaurantClient.getRestaurantById(product.getRestaurantId());
        } catch (Exception ignored) { }

        return new ProductDTO(
                product.getId(),
                product.getName(),
                product.getImage(),
                product.getPrice(),
                product.getStatus(),
                product.getRestaurantId(),
                restaurant != null ? restaurant.getName() : null,
                product.getCreatedAt() != null
                        ? LocalDateTime.ofInstant(product.getCreatedAt(), ZoneId.systemDefault())
                        : null
        );
    }
}

