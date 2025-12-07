package com.library.dao;

import com.library.entity.Fine;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.math.BigDecimal;
import java.util.List;

/**
 * DAO class for Fine entity operations
 */
public class FineDAO extends GenericDAO<Fine, Long> {
    
    public FineDAO() {
        super(Fine.class);
    }
    
    /**
     * Find fines by user
     */
    public List<Fine> findByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Fine> query = em.createQuery(
                "SELECT f FROM Fine f WHERE f.user.userId = :userId ORDER BY f.fineDate DESC", 
                Fine.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find pending fines by user
     */
    public List<Fine> findPendingFinesByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Fine> query = em.createQuery(
                "SELECT f FROM Fine f WHERE f.user.userId = :userId " +
                "AND f.status = :status ORDER BY f.fineDate", 
                Fine.class);
            query.setParameter("userId", userId);
            query.setParameter("status", Fine.FineStatus.PENDING);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find fines by status
     */
    public List<Fine> findByStatus(Fine.FineStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Fine> query = em.createQuery(
                "SELECT f FROM Fine f WHERE f.status = :status ORDER BY f.fineDate DESC", 
                Fine.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get total pending fines for a user
     */
    public BigDecimal getTotalPendingFinesByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BigDecimal> query = em.createQuery(
                "SELECT COALESCE(SUM(f.fineAmount), 0) FROM Fine f " +
                "WHERE f.user.userId = :userId AND f.status = :status", 
                BigDecimal.class);
            query.setParameter("userId", userId);
            query.setParameter("status", Fine.FineStatus.PENDING);
            BigDecimal result = query.getSingleResult();
            return result != null ? result : BigDecimal.ZERO;
        } finally {
            em.close();
        }
    }
    
    /**
     * Get all pending fines
     */
    public List<Fine> getAllPendingFines() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Fine> query = em.createQuery(
                "SELECT f FROM Fine f WHERE f.status = :status ORDER BY f.fineDate", 
                Fine.class);
            query.setParameter("status", Fine.FineStatus.PENDING);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Count pending fines by user
     */
    public long countPendingFinesByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(f) FROM Fine f WHERE f.user.userId = :userId " +
                "AND f.status = :status", 
                Long.class);
            query.setParameter("userId", userId);
            query.setParameter("status", Fine.FineStatus.PENDING);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get fine statistics
     */
    public Object[] getFineStatistics() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT " +
                "COUNT(f), " +
                "COALESCE(SUM(f.fineAmount), 0), " +
                "COALESCE(SUM(CASE WHEN f.status = :pending THEN f.fineAmount ELSE 0 END), 0), " +
                "COALESCE(SUM(CASE WHEN f.status = :paid THEN f.fineAmount ELSE 0 END), 0) " +
                "FROM Fine f", 
                Object[].class);
            query.setParameter("pending", Fine.FineStatus.PENDING);
            query.setParameter("paid", Fine.FineStatus.PAID);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}

