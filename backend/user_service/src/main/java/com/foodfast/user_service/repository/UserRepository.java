package com.foodfast.user_service.repository;
import java.util.List;
import com.foodfast.user_service.model.User;
import org.springframework.data.domain.Page;
import org.springframework.data.domain.Pageable;
import org.springframework.data.mongodb.repository.MongoRepository;

public interface UserRepository extends MongoRepository<User, String> {
        List<User> findAllByRoleOrderByCreatedAtDesc(Integer role);

        Page<User> findByRoleAndFullnameContainingIgnoreCaseAndStatus(
            Integer role, String fullname, Integer status, Pageable pageable);

       Page<User> findByRoleAndFullnameContainingIgnoreCase(
            Integer role, String fullname, Pageable pageable);
}