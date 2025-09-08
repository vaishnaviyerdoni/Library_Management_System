package com.example.LibraryManagement.controller;

import java.util.Collections;
import java.util.List;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import com.example.LibraryManagement.DTO.AddUser;
import com.example.LibraryManagement.DTO.Login;
import com.example.LibraryManagement.DTO.LoginResponse;
import com.example.LibraryManagement.DTO.UpdatePassword;
import com.example.LibraryManagement.DTO.UserResponse;
import com.example.LibraryManagement.entity.User;
import com.example.LibraryManagement.service.UserService;
import org.springframework.web.bind.annotation.GetMapping;
import org.springframework.web.bind.annotation.RequestParam;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.PutMapping;
import org.springframework.web.bind.annotation.PathVariable;

@CrossOrigin(origins = "http://localhost:5173")
@RestController
@RequestMapping("/library")
public class UserController {
    
    private UserService userService;

    public UserController(UserService userService) {
        this.userService = userService;
    }

    //Admin can request information about all the users
    @GetMapping("/user/{userId}")
    public List<User> getAllUsers(@PathVariable int userId) {
        try{
            return userService.getAllUsers(userId);
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //User can request their information
    @GetMapping("/user/{userId}/userinfo")
    public List<User> getUserById(@PathVariable int userId) {
        try{
            return userService.getbyId(userId);
        }
        catch(Exception e){
            e.printStackTrace();
            return Collections.emptyList();
        }
    }

    //Registering new user to the System
    @PostMapping("/user/register")
    public ResponseEntity<UserResponse> registerUser(@RequestBody AddUser user) {
        try{
            int userId = userService.registerUser(user);
            UserResponse res = new UserResponse(userId, "User Registered Successfully");

            if(userId > 0) {
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(-1, "Failed to Register User"));
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body(new UserResponse(-1, "Failed to Register User"));
        }
    }
    
    //Login Validation for User
    @PostMapping("/user/login")
    public ResponseEntity<LoginResponse> loginValidation(@RequestBody Login login) {
        try{
            LoginResponse res = userService.loginUser(login);
            if(res != null) {
                return ResponseEntity.ok(res);
            }
            else{
                return ResponseEntity.status(HttpStatus.UNAUTHORIZED).build();
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).build();
        }
    }
    
    //User can update their Password
    @PutMapping("/user/{userId}/password")
    public ResponseEntity<String> updatePassword(@PathVariable int userId, @RequestBody UpdatePassword password) {
        try{
            if(userService.updatePassword(userId, password)){
                return ResponseEntity.ok("Password Updated Successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to update Password");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to update Password");
        }
    }
    
    //Admin can delete any user account
    @DeleteMapping("/user/{adminId}/admin")
    public ResponseEntity<String> adminDeletesAccount(@PathVariable int adminId, @RequestParam int userId) {
        try{
            if(userService.adminDeletesAccount(adminId, userId)){
                return ResponseEntity.ok("User Deleted Successfully");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete User");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete User");
        }
    }

    //user can delete their own account
    @DeleteMapping("/user/{userId}/self")
    public ResponseEntity<String> deletemyAccount(@PathVariable int userId, @RequestParam String userName, String password) {
        try{
            if(userService.deleteAccount(userId, userName, password)){
                return ResponseEntity.ok("Account Deleted");
            }
            else{
                return ResponseEntity.status(HttpStatus.BAD_REQUEST).body("Failed to delete account");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            return ResponseEntity.status(HttpStatus.INTERNAL_SERVER_ERROR).body("Failed to delete account");
        }
    }
}
