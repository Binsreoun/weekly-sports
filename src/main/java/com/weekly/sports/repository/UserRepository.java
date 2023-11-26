package com.weekly.sports.repository;

import com.weekly.sports.model.entity.UserEntity;
import java.util.List;
import org.springframework.data.jpa.repository.JpaRepository;

public interface UserRepository extends JpaRepository<UserEntity, Long> {

    List<UserEntity> findByEmail(String email);

    UserEntity findByUserId(Long userId);
}
