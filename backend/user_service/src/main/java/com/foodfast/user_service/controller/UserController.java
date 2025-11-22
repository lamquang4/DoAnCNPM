package com.foodfast.user_service.controller;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.service.UserService;
import  com.foodfast.user_service.dto.OrderDTO;
import com.foodfast.user_service.dto.UserDTO;
import com.foodfast.user_service.dto.LoginRequest;
import com.foodfast.user_service.dto.LoginResponse;
import com.foodfast.user_service.service.AuthService;
import org.springframework.http.HttpStatus;
import org.springframework.data.domain.Page;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import java.util.List;
import java.util.Map;

@RestController
@RequestMapping("/api/user")
public class UserController {
 private final UserService userService;
    private final AuthService authService;
public UserController(UserService userService, AuthService authService) {
        this.userService = userService;
        this.authService = authService;
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
        UserDTO user = userService.getUserById(id);
            return ResponseEntity.ok(user);
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

    @PostMapping("/login")
    public ResponseEntity<LoginResponse> login(@RequestBody LoginRequest request) {
        LoginResponse response = authService.login(request);
        return ResponseEntity.ok(response);
    }
    

@GetMapping("/me")
public ResponseEntity<Map<String, Object>> getCurrentUser(@RequestHeader("Authorization") String authHeader) {
    if (authHeader == null || !authHeader.startsWith("Bearer ")) {
        return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
    }

    String token = authHeader.substring(7);
    Map<String, Object> user = authService.getUserFromToken(token);
    return ResponseEntity.ok(user);
}

 @PatchMapping("/{id}/status")
public ResponseEntity<User> updateUserStatus(
        @PathVariable String id,
        @RequestParam int status
) {
    User updated = userService.updateUserStatus(id, status);
    return updated != null 
            ? ResponseEntity.ok(updated) 
            : ResponseEntity.notFound().build();
}
}
