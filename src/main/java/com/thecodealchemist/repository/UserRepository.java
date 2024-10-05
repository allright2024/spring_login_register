package com.thecodealchemist.repository;

import com.thecodealchemist.entity.User;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

@Repository
public interface UserRepository extends JpaRepository<User, Long> {
    boolean existsByEmail(String email);  // 이메일 중복 체크 메서드
    User findByEmail(String email);
}
