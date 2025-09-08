package com.example.LibraryManagement.DTO;

import lombok.AllArgsConstructor;
import lombok.Getter;
import lombok.NoArgsConstructor;
import lombok.Setter;

@Getter@AllArgsConstructor
@Setter@NoArgsConstructor
public class LoginResponse {
    private int userId;
    private String userRole;
    private String token;
}
