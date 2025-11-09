package com.foodfast.user_service.controller;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.service.UserService;

import java.util.List;

@RestController
@RequestMapping("/api/user")
public class UserController {
 private final UserService userService;

public UserController(UserService userService) {
        this.userService = userService;
 }

 @GetMapping
 public List<User> getAllUsers() {
        return userService.getAllUsers();
 }

  @GetMapping("/{id}")
  public ResponseEntity<User> getUserById(@PathVariable String id) {
        return userService.getUserById(id)
                .map(ResponseEntity::ok)
                .orElse(ResponseEntity.notFound().build());
  }

    @PostMapping
    public User createUser(@RequestBody User user) {
        return userService.createUser(user);
    }

    @PutMapping("/{id}")
    public ResponseEntity<User> updateUser(@PathVariable String id, @RequestBody User User) {
        User updated = userService.updateUser(id, User);
        if (updated != null) {
            return ResponseEntity.ok(updated);
        }
        return ResponseEntity.notFound().build();
    }

   @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteUser(@PathVariable String id) {
        userService.deleteUser(id);
        return ResponseEntity.noContent().build();
    }

}
