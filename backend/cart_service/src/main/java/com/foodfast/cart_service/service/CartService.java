package com.foodfast.cart_service.service;
import java.util.ArrayList;
import java.util.List;
import java.util.NoSuchElementException;
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

            if (product == null) {
                throw new NoSuchElementException("Sản phẩm không tồn tại với ID: " + item.getIdProduct());
            }

            return new CartItemDTO(
                    product.getId(),
                    product.getName(), 
                    product.getImage(),
                    product.getPrice(), 
                    item.getQuantity()    
            );
        }).collect(Collectors.toList());

        return new CartDTO(cart.getId(), cart.getUserId(), items);
    }

    // Thêm sản phẩm vào giỏ (tạo giỏ nếu chưa có)
     public CartDTO addProductToCart(String userId, String productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseGet(() -> {
                    Cart newCart = new Cart();
                    newCart.setUserId(userId);
                    newCart.setItems(new ArrayList<>());
                    return cartRepository.save(newCart);
                });

        ProductDTO product = productClient.getProductById(productId);
        if (product == null) {
            throw new NoSuchElementException("Sản phẩm không tồn tại với ID: " + productId);
        }

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
        return getCartByUserId(userId);
    }

    // Cập nhật số lượng sản phẩm trong giỏ
   public CartDTO updateProductQuantity(String userId, String productId, int quantity) {
        if (quantity < 1) {
            throw new IllegalArgumentException("Số lượng mua phải lớn hơn 0");
        }

        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Giỏ hàng không tồn tại cho user: " + userId));

        CartItem item = cart.getItems().stream()
                .filter(i -> i.getIdProduct().equals(productId))
                .findFirst()
                .orElseThrow(() -> new NoSuchElementException("Sản phẩm không tồn tại trong giỏ với ID: " + productId));

        item.setQuantity(quantity);
        cartRepository.save(cart);

        return getCartByUserId(userId);
    }

    // Xóa sản phẩm khỏi giỏ
      public CartDTO removeProductFromCart(String userId, String productId) {
        Cart cart = cartRepository.findByUserId(userId)
                .orElseThrow(() -> new NoSuchElementException("Giỏ hàng không tồn tại cho user: " + userId));

        List<CartItem> items = cart.getItems().stream()
                .filter(i -> !i.getIdProduct().equals(productId))
                .collect(Collectors.toList());

        cart.setItems(items);
        cartRepository.save(cart);

        return getCartByUserId(userId);
    }

    public CartDTO clearCart(String userId) {
    Cart cart = cartRepository.findByUserId(userId)
            .orElseThrow(() -> new NoSuchElementException("Giỏ hàng không tồn tại cho user: " + userId));

    cart.setItems(new ArrayList<>());
    cartRepository.save(cart);

    return getCartByUserId(userId);
}
}
