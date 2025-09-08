package com.example.LibraryManagement.service;

import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;
import java.util.Objects;
import java.util.Optional;
import org.slf4j.Logger;
import com.example.LibraryManagement.DTO.AddUser;
import com.example.LibraryManagement.DTO.Login;
import com.example.LibraryManagement.DTO.LoginResponse;
import com.example.LibraryManagement.DTO.UpdatePassword;
import com.example.LibraryManagement.Exceptions.UserNotFoundException;
import com.example.LibraryManagement.entity.User;
import com.example.LibraryManagement.repository.UserDAO;

@Service
public class UserService {
    private UserDAO userDAO;
    private JwtAuth auth;
    private static final Logger logger = LoggerFactory.getLogger(UserService.class);

    public UserService(UserDAO userDAO, JwtAuth auth) {
        this.userDAO = userDAO;
        this.auth = auth;
    }

    //To register the user into the Library System
    public int registerUser(AddUser user) {
        try{
            String username = user.getUserName();
            String role = user.getUserRole();
            String passcode = user.getPass_word();

            User newUser = new User(username, passcode, role);
            User savedUser = userDAO.save(newUser);
            return savedUser.getUserId();
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("User could not be registered!", e);
            return -1;
        }
    }

    //To check if user is valid before letting them login to the system
    public LoginResponse loginUser(Login login) {
        try {
            String passcode = login.getPass_word();
            String username = login.getUserName();
        
            Optional<User> optionalUser = userDAO.findByUserName(username);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                    if (user.getPass_word().equals(passcode)) {
                        String token = auth.generateToken(user.getUserName(), user.getUserRole());
                        return new LoginResponse(user.getUserId(), user.getUserRole(), token);
                    } 
                    else {
                        throw new UserNotFoundException("Invalid Credentials");
                    }
                } 
                else {
                    throw new UserNotFoundException("User not found");
                } 
            }
        catch(Exception e) {
            e.printStackTrace();
            logger.error("Failed to login. try again later!", e);
            return null;
        }
    }

    //Admin can get the list of all Users
    public List<User> getAllUsers(int userId) {
        try{
            Optional<User> optionalUser = userDAO.findById(userId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                String role = user.getUserRole();

                if(role.equalsIgnoreCase("Admin")) {
                    List<User> users = userDAO.findAll();

                    return users;
                }
                else{
                    throw new UserNotFoundException("Only Admin can fetch this Information!");
                }
            }
            throw new UserNotFoundException("User could not be found!");
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error while fetching users, try again later", e);
            return Collections.emptyList();
        }
    }

    //member or admin can fetch information by id
    public List<User> getbyId(int userId) {
        try{
            Optional<User> optionalUser = userDAO.findById(userId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                List<User> userbyid = new ArrayList<>();
                userbyid.add(user);
                return userbyid;
            }
            else{
                throw new UserNotFoundException("User for this userId not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Failed to fetch information for this user.", e);
            return Collections.emptyList();
        }
    }

    //update the password: User can update their password
    public boolean updatePassword(int userId, UpdatePassword uPassword) {
        try{
            Optional<User> optionalUser = userDAO.findById(userId);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                String username = user.getUserName();
                String userRole = user.getUserRole();

                System.out.println(username);
                System.out.println(userRole);


                String name = uPassword.getUserName();
                String passcode = uPassword.getPassword(); //new Password
                String role = uPassword.getUserRole();

                System.out.println(name);
                System.out.println(role);

                if ((Objects.equals(username, name)) && (Objects.equals(userRole, role))) {
                    user.setPass_word(passcode);
                    userDAO.save(user);
                    return true;
                }
                else{
                    throw new UserNotFoundException("Credentials do not match, check again");
                }
            }
            else{
                throw new UserNotFoundException("User not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error occurred when updating password", e);
            return false;
        }
    }

    //Admin deletes account
    public boolean adminDeletesAccount(int adminId, int userId) {
        try{
            Optional<User> adminOptional = userDAO.findById(adminId);
            if(adminOptional.isPresent()) {
                User adminUser = adminOptional.get();
                String role = adminUser.getUserRole();
                if(role.equalsIgnoreCase("admin")){
                    userDAO.deleteById(userId);
                    return true;
                }
                else{
                    throw new UserNotFoundException("Only admin can accounts");
                }
            }
            else{
                throw new UserNotFoundException("User not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Exception occurred when deleting account!", e);
            return false;
        }
    }

    //User deletes their own account
    public boolean deleteAccount(int userID, String username, String password) {
        try{
            Optional<User> optionalUser = userDAO.findById(userID);
            if(optionalUser.isPresent()) {
                User user = optionalUser.get();
                userDAO.delete(user);
                return true;
            }
            else{
                throw new UserNotFoundException("user not found");
            }
        }
        catch(Exception e){
            e.printStackTrace();
            logger.error("Error occured when deleting account!", e);
            return false;
        }
    }
}
