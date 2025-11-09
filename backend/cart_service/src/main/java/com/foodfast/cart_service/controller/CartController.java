package com.foodfast.cart_service.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.DeleteMapping;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.RestController;
import com.foodfast.cart_service.dto.CartDTO;
import com.foodfast.cart_service.service.CartService;

@RestController
@RequestMapping("/api/cart")
public class CartController {
    private final CartService cartService;
    public CartController(CartService cartService){
        this.cartService = cartService;
    }

     // Lấy giỏ hàng theo userId
    @GetMapping("/{userId}")
    public ResponseEntity<CartDTO> getCart(@PathVariable String userId) {
        return ResponseEntity.ok(cartService.getCartByUserId(userId));
    }

    // Thêm sản phẩm vào giỏ
    @PostMapping("/{userId}")
    public ResponseEntity<CartDTO> addProductToCart(
            @PathVariable String userId,
            @RequestParam String productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.addProductToCart(userId, productId, quantity));
    }

    // Cập nhật số lượng sản phẩm
    @PutMapping("/{userId}")
    public ResponseEntity<CartDTO> updateQuantity(
            @PathVariable String userId,
            @RequestParam String productId,
            @RequestParam int quantity
    ) {
        return ResponseEntity.ok(cartService.updateProductQuantity(userId, productId, quantity));
    }

    // Xóa sản phẩm khỏi giỏ
    @DeleteMapping("/{userId}")
    public ResponseEntity<CartDTO> removeProduct(
            @PathVariable String userId,
            @RequestParam String productId
    ) {
        return ResponseEntity.ok(cartService.removeProductFromCart(userId, productId));
    }
}
