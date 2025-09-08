package com.example.LibraryManagement.DTO;

import com.fasterxml.jackson.annotation.JsonProperty;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@Setter
@NoArgsConstructor
@AllArgsConstructor
public class AddBook {
    private String title;
    private String author;
    
    @JsonProperty("ISBN")
    private String ISBN;
}
