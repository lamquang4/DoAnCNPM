package com.foodfast.user_service.controller;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.service.UserService;
import main.java.com.foodfast.user_service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
 private final UserService userService;

public UserController(UserService userService) {
        this.userService = userService;
 }

@GetMapping("/role/{role}")
public ResponseEntity<?> getUsersByRole(
        @PathVariable Integer role,
        @RequestParam(value = "q", required = false) String q,
        @RequestParam(value = "status", required = false) Integer status,
        @RequestParam(value = "page", defaultValue = "1") int page,
        @RequestParam(value = "limit", defaultValue = "12") int limit
) {
    Page<UserDTO> userPage = userService.getUsersByRole(role, q, status, page, limit);

    return ResponseEntity.ok(Map.of(
            "users", userPage.getContent(),
            "totalPages", userPage.getTotalPages(),
            "total", userPage.getTotalElements()
    ));
}

    @GetMapping("/{id}")
    public ResponseEntity<UserDTO> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(userService::toDTO)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
    }

   @PostMapping
    public ResponseEntity<UserDTO> createUser(@RequestBody User user) {
        User created = userService.createUser(user);
        return ResponseEntity.ok(userService.toDTO(created));
    }

    @PutMapping("/{id}")
    public ResponseEntity<UserDTO> updateUser(@PathVariable String id, @RequestBody User user) {
        User updated = userService.updateUser(id, user);
        if (updated != null) {
            return ResponseEntity.ok(userService.toDTO(updated));
        }
        return ResponseEntity.notFound().build();
    }
}
