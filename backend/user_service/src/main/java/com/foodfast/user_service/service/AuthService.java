package com.foodfast.user_service.service;
import org.springframework.stereotype.Service;
import java.util.Map;
import org.springframework.security.crypto.password.PasswordEncoder;
import com.foodfast.user_service.dto.LoginRequest;
import com.foodfast.user_service.dto.LoginResponse;
import com.foodfast.user_service.model.User;
import com.foodfast.user_service.repository.UserRepository;
import com.foodfast.user_service.utils.JwtUtils;

@Service
public class AuthService {

    private final UserRepository userRepository;
    private final PasswordEncoder passwordEncoder;
    private final JwtUtils jwtUtils; 

    public AuthService(UserRepository userRepository, PasswordEncoder passwordEncoder, JwtUtils jwtUtils) {
        this.userRepository = userRepository;
        this.passwordEncoder = passwordEncoder;
        this.jwtUtils = jwtUtils;
    }

    // đăng nhập
    public LoginResponse login(LoginRequest request) {
        User user = userRepository.findByEmail(request.getEmail())
                .orElseThrow(() -> new IllegalArgumentException("Email hoặc mật khẩu không chính xác"));

        if (user.getStatus() == 0) {
            throw new IllegalStateException("Tài khoản của bạn đã bị khóa");
        }

        if (!passwordEncoder.matches(request.getPassword(), user.getPassword())) {
            throw new IllegalArgumentException("Email hoặc mật khẩu không chính xác");
        }

        // Tạo token
        String token = jwtUtils.generateToken(user);

        return LoginResponse.builder()
                .token(token)
                .id(user.getId())
                .email(user.getEmail())
                .phone(user.getPhone())
                .fullname(user.getFullname())
                .role(user.getRole())
                .build();
    }

    // lấy thông tin tài khoản từ token
    public Map<String, Object> getUserFromToken(String token) {
        return jwtUtils.getUserFromToken(token);
    }
}
