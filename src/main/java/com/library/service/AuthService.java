package com.library.service;

import com.library.dao.UserDAO;
import com.library.entity.User;
import com.library.security.JWTUtil;
import com.library.security.PasswordUtil;

import java.time.LocalDateTime;
import java.util.HashMap;
import java.util.Map;
import java.util.Optional;

/**
 * Service class for authentication operations
 */
public class AuthService {
    
    private final UserDAO userDAO;
    
    public AuthService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Register a new user
     */
    public Map<String, Object> register(String username, String email, String password, 
                                        String firstName, String lastName, String role) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Username is required");
            return response;
        }
        
        if (email == null || email.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Email is required");
            return response;
        }
        
        if (password == null || password.length() < 6) {
            response.put("success", false);
            response.put("message", "Password must be at least 6 characters");
            return response;
        }
        
        // Check if username exists
        if (userDAO.usernameExists(username)) {
            response.put("success", false);
            response.put("message", "Username already exists");
            return response;
        }
        
        // Check if email exists
        if (userDAO.emailExists(email)) {
            response.put("success", false);
            response.put("message", "Email already exists");
            return response;
        }
        
        try {
            // Create new user
            User user = new User(username, email, PasswordUtil.hashPassword(password), firstName, lastName);
            
            // Set role
            if (role != null && !role.isEmpty()) {
                try {
                    user.setRole(User.UserRole.valueOf(role.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    user.setRole(User.UserRole.USER);
                }
            } else {
                user.setRole(User.UserRole.USER);
            }
            
            user.setStatus(User.UserStatus.ACTIVE);
            
            // Save user
            User savedUser = userDAO.save(user);
            
            // Generate token
            String token = JWTUtil.generateToken(savedUser);
            
            response.put("success", true);
            response.put("message", "User registered successfully");
            response.put("token", token);
            response.put("user", getUserInfo(savedUser));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error registering user: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Login user
     */
    public Map<String, Object> login(String username, String password) {
        Map<String, Object> response = new HashMap<>();
        
        // Validate input
        if (username == null || username.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Username is required");
            return response;
        }
        
        if (password == null || password.trim().isEmpty()) {
            response.put("success", false);
            response.put("message", "Password is required");
            return response;
        }
        
        try {
            // Find user by username or email
            Optional<User> userOpt = userDAO.findByUsername(username);
            if (userOpt.isEmpty()) {
                userOpt = userDAO.findByEmail(username);
            }
            
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return response;
            }
            
            User user = userOpt.get();
            
            // Check if user is active
            if (user.getStatus() != User.UserStatus.ACTIVE) {
                response.put("success", false);
                response.put("message", "Account is " + user.getStatus().name().toLowerCase());
                return response;
            }
            
            // Verify password
            if (!PasswordUtil.verifyPassword(password, user.getPasswordHash())) {
                response.put("success", false);
                response.put("message", "Invalid credentials");
                return response;
            }
            
            // Update last login
            user.setLastLogin(LocalDateTime.now());
            userDAO.update(user);
            
            // Generate token
            String token = JWTUtil.generateToken(user);
            
            response.put("success", true);
            response.put("message", "Login successful");
            response.put("token", token);
            response.put("user", getUserInfo(user));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error during login: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Validate token and get user info
     */
    public Map<String, Object> validateToken(String token) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            if (!JWTUtil.validateToken(token)) {
                response.put("success", false);
                response.put("message", "Invalid or expired token");
                return response;
            }
            
            Long userId = JWTUtil.getUserIdFromToken(token);
            Optional<User> userOpt = userDAO.findById(userId);
            
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User not found");
                return response;
            }
            
            User user = userOpt.get();
            
            if (user.getStatus() != User.UserStatus.ACTIVE) {
                response.put("success", false);
                response.put("message", "Account is " + user.getStatus().name().toLowerCase());
                return response;
            }
            
            response.put("success", true);
            response.put("user", getUserInfo(user));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error validating token: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Get user information map (without sensitive data)
     */
    private Map<String, Object> getUserInfo(User user) {
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", user.getUserId());
        userInfo.put("username", user.getUsername());
        userInfo.put("email", user.getEmail());
        userInfo.put("firstName", user.getFirstName());
        userInfo.put("lastName", user.getLastName());
        userInfo.put("fullName", user.getFullName());
        userInfo.put("phone", user.getPhone());
        userInfo.put("address", user.getAddress());
        userInfo.put("role", user.getRole().name());
        userInfo.put("status", user.getStatus().name());
        userInfo.put("registrationDate", user.getRegistrationDate());
        userInfo.put("lastLogin", user.getLastLogin());
        return userInfo;
    }
    
    /**
     * Change password
     */
    public Map<String, Object> changePassword(Long userId, String oldPassword, String newPassword) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User not found");
                return response;
            }
            
            User user = userOpt.get();
            
            // Verify old password
            if (!PasswordUtil.verifyPassword(oldPassword, user.getPasswordHash())) {
                response.put("success", false);
                response.put("message", "Current password is incorrect");
                return response;
            }
            
            // Validate new password
            if (newPassword == null || newPassword.length() < 6) {
                response.put("success", false);
                response.put("message", "New password must be at least 6 characters");
                return response;
            }
            
            // Update password
            user.setPasswordHash(PasswordUtil.hashPassword(newPassword));
            userDAO.update(user);
            
            response.put("success", true);
            response.put("message", "Password changed successfully");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error changing password: " + e.getMessage());
        }
        
        return response;
    }
}

