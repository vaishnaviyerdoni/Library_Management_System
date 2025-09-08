package com.example.LibraryManagement.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.stereotype.Repository;
import com.example.LibraryManagement.entity.Library;

@Repository
public interface LibraryDAO extends JpaRepository<Library, Integer>{
    Library findByTitle(String title);
} 