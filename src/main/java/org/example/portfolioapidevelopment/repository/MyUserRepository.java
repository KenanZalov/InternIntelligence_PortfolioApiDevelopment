package org.example.portfolioapidevelopment.repository;

import org.example.portfolioapidevelopment.dao.MyUser;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import java.util.Optional;

@Repository
public interface MyUserRepository extends JpaRepository<MyUser, Long> {
    boolean existsByUsername(String username);
    Optional<MyUser> findByUsername(String username);
}
