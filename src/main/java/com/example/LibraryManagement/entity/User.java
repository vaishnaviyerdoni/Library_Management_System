package com.example.LibraryManagement.entity;

import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Getter 
@Setter
@ToString
@Entity
@Table(name = "users")
public class User {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="userId")
    private Integer userId;

    @Column(name = "userName", nullable = false, unique = true)
    private String userName;

    @Column(name="pass_word", nullable = false)
    private String pass_word;

    @Column(name="userRole", nullable = false)
    private String userRole;

    public User() {

    }

    public User(String userName, String pass_word, String userRole) {
        this.userName = userName;
        this.pass_word = pass_word;
        this.userRole = userRole;
    }
}
