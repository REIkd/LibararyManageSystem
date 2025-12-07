package com.library.dao;

import com.library.entity.Review;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO class for Review entity operations
 */
public class ReviewDAO extends GenericDAO<Review, Long> {
    
    public ReviewDAO() {
        super(Review.class);
    }
    
    /**
     * Find reviews by book
     */
    public List<Review> findByBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.book.bookId = :bookId ORDER BY r.reviewDate DESC", 
                Review.class);
            query.setParameter("bookId", bookId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find reviews by user
     */
    public List<Review> findByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.user.userId = :userId ORDER BY r.reviewDate DESC", 
                Review.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find review by user and book
     */
    public Optional<Review> findByUserAndBook(Long userId, Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.user.userId = :userId AND r.book.bookId = :bookId", 
                Review.class);
            query.setParameter("userId", userId);
            query.setParameter("bookId", bookId);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get average rating for a book
     */
    public Double getAverageRatingForBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Double> query = em.createQuery(
                "SELECT AVG(r.rating) FROM Review r WHERE r.book.bookId = :bookId", 
                Double.class);
            query.setParameter("bookId", bookId);
            Double result = query.getSingleResult();
            return result != null ? result : 0.0;
        } finally {
            em.close();
        }
    }
    
    /**
     * Count reviews for a book
     */
    public long countReviewsForBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Review r WHERE r.book.bookId = :bookId", 
                Long.class);
            query.setParameter("bookId", bookId);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get recent reviews
     */
    public List<Review> getRecentReviews(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r ORDER BY r.reviewDate DESC", 
                Review.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find reviews by rating
     */
    public List<Review> findByRating(Integer rating) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Review> query = em.createQuery(
                "SELECT r FROM Review r WHERE r.rating = :rating ORDER BY r.reviewDate DESC", 
                Review.class);
            query.setParameter("rating", rating);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

