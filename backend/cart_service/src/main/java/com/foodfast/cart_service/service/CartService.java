package com.foodfast.cart_service.service;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;
import org.springframework.stereotype.Service;
import com.foodfast.cart_service.client.ProductClient;
import com.foodfast.cart_service.dto.CartDTO;
import com.foodfast.cart_service.dto.CartItemDTO;
import com.foodfast.cart_service.dto.ProductDTO;
import com.foodfast.cart_service.model.Cart;
import com.foodfast.cart_service.model.CartItem;
import com.foodfast.cart_service.repository.CartRepository;

@Service
public class CartService {
    private final CartRepository cartRepository;
    private final ProductClient productClient;
    public CartService(CartRepository cartRepository, ProductClient productClient) {
        this.cartRepository = cartRepository;
        this.productClient = productClient;
    }

      // Lấy giỏ hàng theo userId
public CartDTO getCartByUserId(String userId) {
    Cart cart = cartRepository.findByUserId(userId)
            .orElseGet(() -> {
                Cart newCart = new Cart();
                newCart.setUserId(userId);
                newCart.setItems(new ArrayList<>());
                return cartRepository.save(newCart);
            });

    List<CartItemDTO> items = cart.getItems().stream().map(item -> {
        ProductDTO product = productClient.getProductById(item.getIdProduct());
        return new CartItemDTO(
                item.getIdProduct(),
                item.getQuantity(),
                product.getName(),
                product.getPrice(),
                product.getStock()
        );
    }).collect(Collectors.toList());

    return new CartDTO(cart.getId(), cart.getUserId(), items);
}

    // Thêm sản phẩm vào giỏ (tạo giỏ nếu chưa có)
 public CartDTO addProductToCart(String userId, String productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }

        // Lấy giỏ hàng
        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        // Kiểm tra sản phẩm tồn tại
        ProductDTO product = productClient.getProductById(productId);
        if (product == null) {
            throw new RuntimeException("Sản phẩm không tồn tại");
        }

        // Kiểm tra sản phẩm đã có trong giỏ chưa
        CartItem existingItem = cart.getItems().stream()
                .filter(i -> i.getIdProduct().equals(productId))
                .findFirst()
                .orElse(null);

        if (existingItem != null) {
            existingItem.setQuantity(existingItem.getQuantity() + quantity);
        } else {
            cart.getItems().add(new CartItem(productId, quantity));
        }

        cartRepository.save(cart);

        // Trả về DTO với thông tin sản phẩm
        return getCartByUserId(userId);
    }

    // Cập nhật số lượng sản phẩm trong giỏ
   public CartDTO updateProductQuantity(String userId, String productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getIdProduct().equals(productId))
                .findFirst()
                .orElseThrow(() -> new RuntimeException("Sản phẩm không tồn tại trong giỏ"));

        item.setQuantity(quantity);
        cartRepository.save(cart);

        return getCartByUserId(userId);
    }

    // Xóa sản phẩm khỏi giỏ
    public CartDTO removeProductFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new RuntimeException("Giỏ hàng không tồn tại"));

        List<CartItem> items = cart.getItems().stream()
                .filter(i -> !i.getIdProduct().equals(productId))
                .collect(Collectors.toList());

        cart.setItems(items);
        cartRepository.save(cart);

        return getCartByUserId(userId);
    }
}
