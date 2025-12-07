package com.library.service;

import com.library.dao.UserDAO;
import com.library.entity.User;
import com.library.security.PasswordUtil;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for user management operations
 */
public class UserService {
    
    private final UserDAO userDAO;
    
    public UserService() {
        this.userDAO = new UserDAO();
    }
    
    /**
     * Get all users
     */
    public List<Map<String, Object>> getAllUsers() {
        List<User> users = userDAO.findAll();
        return users.stream().map(this::getUserInfo).collect(Collectors.toList());
    }
    
    /**
     * Get user by ID
     */
    public Optional<Map<String, Object>> getUserById(Long userId) {
        Optional<User> userOpt = userDAO.findById(userId);
        return userOpt.map(this::getUserInfo);
    }
    
    /**
     * Get users by role
     */
    public List<Map<String, Object>> getUsersByRole(String role) {
        try {
            User.UserRole userRole = User.UserRole.valueOf(role.toUpperCase());
            List<User> users = userDAO.findByRole(userRole);
            return users.stream().map(this::getUserInfo).collect(Collectors.toList());
        } catch (IllegalArgumentException e) {
            return new ArrayList<>();
        }
    }
    
    /**
     * Search users
     */
    public List<Map<String, Object>> searchUsers(String searchTerm) {
        List<User> users = userDAO.searchUsers(searchTerm);
        return users.stream().map(this::getUserInfo).collect(Collectors.toList());
    }
    
    /**
     * Create user (Admin only)
     */
    public Map<String, Object> createUser(Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String username = userData.get("username");
            String email = userData.get("email");
            String password = userData.get("password");
            String firstName = userData.get("firstName");
            String lastName = userData.get("lastName");
            String role = userData.get("role");
            
            // Validate required fields
            if (username == null || email == null || password == null || 
                firstName == null || lastName == null) {
                response.put("success", false);
                response.put("message", "Missing required fields");
                return response;
            }
            
            // Check duplicates
            if (userDAO.usernameExists(username)) {
                response.put("success", false);
                response.put("message", "Username already exists");
                return response;
            }
            
            if (userDAO.emailExists(email)) {
                response.put("success", false);
                response.put("message", "Email already exists");
                return response;
            }
            
            // Create user
            User user = new User(username, email, PasswordUtil.hashPassword(password), firstName, lastName);
            user.setPhone(userData.get("phone"));
            user.setAddress(userData.get("address"));
            
            // Set role
            if (role != null) {
                try {
                    user.setRole(User.UserRole.valueOf(role.toUpperCase()));
                } catch (IllegalArgumentException e) {
                    user.setRole(User.UserRole.USER);
                }
            }
            
            User savedUser = userDAO.save(user);
            
            response.put("success", true);
            response.put("message", "User created successfully");
            response.put("user", getUserInfo(savedUser));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating user: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Update user
     */
    public Map<String, Object> updateUser(Long userId, Map<String, String> userData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User not found");
                return response;
            }
            
            User user = userOpt.get();
            
            // Update fields
            if (userData.containsKey("firstName")) {
                user.setFirstName(userData.get("firstName"));
            }
            if (userData.containsKey("lastName")) {
                user.setLastName(userData.get("lastName"));
            }
            if (userData.containsKey("email")) {
                String newEmail = userData.get("email");
                if (!newEmail.equals(user.getEmail()) && userDAO.emailExists(newEmail)) {
                    response.put("success", false);
                    response.put("message", "Email already exists");
                    return response;
                }
                user.setEmail(newEmail);
            }
            if (userData.containsKey("phone")) {
                user.setPhone(userData.get("phone"));
            }
            if (userData.containsKey("address")) {
                user.setAddress(userData.get("address"));
            }
            if (userData.containsKey("role")) {
                try {
                    user.setRole(User.UserRole.valueOf(userData.get("role").toUpperCase()));
                } catch (IllegalArgumentException ignored) {}
            }
            if (userData.containsKey("status")) {
                try {
                    user.setStatus(User.UserStatus.valueOf(userData.get("status").toUpperCase()));
                } catch (IllegalArgumentException ignored) {}
            }
            
            User updatedUser = userDAO.update(user);
            
            response.put("success", true);
            response.put("message", "User updated successfully");
            response.put("user", getUserInfo(updatedUser));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating user: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Delete user
     */
    public Map<String, Object> deleteUser(Long userId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User not found");
                return response;
            }
            
            userDAO.delete(userId);
            
            response.put("success", true);
            response.put("message", "User deleted successfully");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting user: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Get user statistics
     */
    public Map<String, Object> getUserStatistics() {
        Map<String, Object> stats = new HashMap<>();
        
        stats.put("totalUsers", userDAO.count());
        stats.put("admins", userDAO.countByRole(User.UserRole.ADMIN));
        stats.put("instructors", userDAO.countByRole(User.UserRole.INSTRUCTOR));
        stats.put("users", userDAO.countByRole(User.UserRole.USER));
        stats.put("activeUsers", userDAO.findByStatus(User.UserStatus.ACTIVE).size());
        
        return stats;
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
}

