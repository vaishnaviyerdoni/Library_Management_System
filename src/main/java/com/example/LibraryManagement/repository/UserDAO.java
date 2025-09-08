package com.example.LibraryManagement.repository;

import java.util.Optional;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.LibraryManagement.entity.User;

@Repository
public interface UserDAO extends JpaRepository<User, Integer> {

    Optional<User> findByUserName(String username);

}
