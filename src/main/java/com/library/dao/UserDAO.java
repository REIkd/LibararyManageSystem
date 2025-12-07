package com.library.dao;

import com.library.entity.User;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO class for User entity operations
 */
public class UserDAO extends GenericDAO<User, Long> {
    
    public UserDAO() {
        super(User.class);
    }
    
    /**
     * Find user by username
     */
    public Optional<User> findByUsername(String username) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.username = :username", User.class);
            query.setParameter("username", username);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find user by email
     */
    public Optional<User> findByEmail(String email) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.email = :email", User.class);
            query.setParameter("email", email);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find users by role
     */
    public List<User> findByRole(User.UserRole role) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.role = :role", User.class);
            query.setParameter("role", role);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find users by status
     */
    public List<User> findByStatus(User.UserStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE u.status = :status", User.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Search users by name or username
     */
    public List<User> searchUsers(String searchTerm) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<User> query = em.createQuery(
                "SELECT u FROM User u WHERE " +
                "LOWER(u.username) LIKE LOWER(:term) OR " +
                "LOWER(u.firstName) LIKE LOWER(:term) OR " +
                "LOWER(u.lastName) LIKE LOWER(:term) OR " +
                "LOWER(u.email) LIKE LOWER(:term)", User.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Check if username exists
     */
    public boolean usernameExists(String username) {
        return findByUsername(username).isPresent();
    }
    
    /**
     * Check if email exists
     */
    public boolean emailExists(String email) {
        return findByEmail(email).isPresent();
    }
    
    /**
     * Count users by role
     */
    public long countByRole(User.UserRole role) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(u) FROM User u WHERE u.role = :role", Long.class);
            query.setParameter("role", role);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}

