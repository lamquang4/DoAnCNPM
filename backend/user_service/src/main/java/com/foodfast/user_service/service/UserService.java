package com.foodfast.user_service.service;
import org.springframework.security.crypto.password.PasswordEncoder;
import org.springframework.stereotype.Service;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.repository.UserRepository;
import main.java.com.foodfast.user_service.dto.UserDTO;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.domain.PageRequest;
import org.springframework.data.domain.Sort;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

@Service
public class UserService {
    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    public UserService(UserRepository userRepository, PasswordEncoder passwordEncoder) {
        this.userRepository  = userRepository;
        this.passwordEncoder = passwordEncoder;
    }
    public UserDTO toDTO(User user) {
        return new UserDTO(
                user.getId(),
                user.getFullname(),
                user.getEmail(),
                user.getPhone(),
                user.getRole(),
                user.getStatus()
        );
    }

    public Page<UserDTO> getUsersByRole(Integer role, String q, Integer status, int page, int limit) {
        Pageable pageable = PageRequest.of(page - 1, limit, Sort.by(Sort.Direction.DESC, "createdAt"));
        Page<User> userPage;

        if (status != null) {
            userPage = userRepository.findByRoleAndFullnameContainingIgnoreCaseAndStatus(role, q != null ? q : "", status, pageable);
        } else {
            userPage = userRepository.findByRoleAndFullnameContainingIgnoreCase(role, q != null ? q : "", pageable);
        }

        return userPage.map(this::toDTO);
    }

    public Optional<User> getUserById(String id) {
        return userRepository.findById(id);
    }

    public User createUser(User user) {
        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            user.setPassword(passwordEncoder.encode(user.getPassword()));
        }
         user.setStatus(1);
        return userRepository.save(user);
    }

public User updateUser(String id, User user) {
    return userRepository.findById(id).map(existingUser -> {

        if (user.getRole() != null) {
            existingUser.setRole(user.getRole());
        }

        existingUser.setFullname(user.getFullname() != null ? user.getFullname() : existingUser.getFullname());
        existingUser.setEmail(user.getEmail() != null ? user.getEmail() : existingUser.getEmail());
        existingUser.setPhone(user.getPhone() != null ? user.getPhone() : existingUser.getPhone());
        existingUser.setStatus(user.getStatus() != null ? user.getStatus() : existingUser.getStatus());

        if (user.getPassword() != null && !user.getPassword().isBlank()) {
            existingUser.setPassword(passwordEncoder.encode(user.getPassword()));
        }

        return userRepository.save(existingUser);
    }).orElse(null);
}


}
