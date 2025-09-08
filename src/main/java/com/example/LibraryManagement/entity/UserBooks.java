package com.example.LibraryManagement.entity;

import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.JoinColumn;
import jakarta.persistence.ManyToOne;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Entity
@Getter
@Setter
@ToString
@Table(name = "userbooks")
public class UserBooks {
    
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private int Id;

    @ManyToOne
    @JoinColumn(name = "bookId")
    private Library borrowedBook;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User borrowerUser;

    public UserBooks() {

    }

    public UserBooks(Library borrowedBook, User borrowerUser) {
        this.borrowedBook = borrowedBook;
        this.borrowerUser = borrowerUser;
    }

}
