package com.example.LibraryManagement.entity;

import com.fasterxml.jackson.annotation.JsonIgnore;
import com.fasterxml.jackson.annotation.JsonProperty;
import jakarta.persistence.Column;
import jakarta.persistence.Entity;
import jakarta.persistence.GeneratedValue;
import jakarta.persistence.GenerationType;
import jakarta.persistence.Id;
import jakarta.persistence.Table;
import lombok.Getter;
import lombok.Setter;
import lombok.ToString;

@Setter @Getter
@ToString
@Entity
@Table(name="books")
public class Library {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name="bookId")
    private Integer bookId;

    @Column(name="title", nullable = false)
    private String title;
    
    @Column(name="author", nullable = false)
    private String author;

    @Column(name="ISBN")
    @JsonProperty("ISBN")
    private String ISBN;

    @Column(name="isAvailable")
    private boolean isAvailable;

    public Library(){

    }

    public Library(String title, String author, String ISBN, boolean isAvailable){
        this.title = title;
        this.author = author;
        this.ISBN = ISBN;
        this.isAvailable = isAvailable;
    }

    @JsonIgnore
    public String getIsbn() {
        return this.ISBN;
    }
}
