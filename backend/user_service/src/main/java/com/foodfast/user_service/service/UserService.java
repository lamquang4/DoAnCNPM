package com.foodfast.user_service.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.repository.UserRepository;
import java.util.List;
import java.util.Optional;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
 public List<User> getAllUsers() {
        return userRepository.findAll();
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
        return userRepository.save(user);
    }

    public User updateUser(String id, User user) {
        if (userRepository.existsById(id)) {
            user.setId(id);

          if (user.getPassword() != null && !user.getPassword().isBlank()) {
                user.setPassword(passwordEncoder.encode(user.getPassword()));
            } else {
                //  Giữ lại mật khẩu cũ nếu không nhập mới
                String oldPassword = userRepository.findById(id)
                        .map(User::getPassword)
                        .orElse(null);
                user.setPassword(oldPassword);
            }

            return userRepository.save(user);
        }
        return null;
    }

    public void deleteUser(String id) {
      userRepository.deleteById(id);
    }
}
