package com.example.LibraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;

import com.example.LibraryManagement.entity.UserBooks;

@Repository
public interface UserBookDAO extends JpaRepository<UserBooks, Integer> {

    
} 