package com.foodfast.order_service.client;
import java.util.List;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.PathVariable;
import com.foodfast.order_service.model.Product;

@FeignClient(name = "product-service")
public interface ProductClient {
  @GetMapping("/api/product/{id}")
    Product getProductById(@PathVariable("id") String id);

    @GetMapping("/api/product/restaurant/{restaurantId}")
    List<Product> getProductsByRestaurantId(@PathVariable("restaurantId") String restaurantId);
  }
