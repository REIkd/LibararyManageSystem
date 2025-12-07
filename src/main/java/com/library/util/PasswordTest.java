package com.library.util;

import org.mindrot.jbcrypt.BCrypt;

/**
 * Utility to test and generate password hashes
 * Run this class to verify password hashing
 */
public class PasswordTest {
    
    public static void main(String[] args) {
        String password = "admin123";
        
        // Test hash from database
        String dbHash = "$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi";
        
        System.out.println("=== Password Verification Test ===");
        System.out.println("Password to test: " + password);
        System.out.println("Database hash: " + dbHash);
        System.out.println();
        
        // Verify the hash
        boolean matches = BCrypt.checkpw(password, dbHash);
        System.out.println("Does password match? " + matches);
        System.out.println();
        
        if (!matches) {
            System.out.println("Hash does NOT match! Generating new hash...");
            String newHash = BCrypt.hashpw(password, BCrypt.gensalt(10));
            System.out.println("New hash for 'admin123': " + newHash);
            System.out.println();
            System.out.println("Execute this SQL in phpMyAdmin:");
            System.out.println("UPDATE users SET password_hash = '" + newHash + "' WHERE username IN ('admin', 'instructor1', 'user1');");
        } else {
            System.out.println("✓ Hash is CORRECT! The database hash should work.");
            System.out.println();
            System.out.println("If login still fails, check:");
            System.out.println("1. Is the hash exactly this in database? (no extra spaces)");
            System.out.println("2. Is the database connection working?");
            System.out.println("3. Are you using the correct username?");
        }
    }
}

