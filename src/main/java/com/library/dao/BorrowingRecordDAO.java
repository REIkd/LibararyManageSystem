package com.library.dao;

import com.library.entity.BorrowingRecord;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO class for BorrowingRecord entity operations
 */
public class BorrowingRecordDAO extends GenericDAO<BorrowingRecord, Long> {
    
    public BorrowingRecordDAO() {
        super(BorrowingRecord.class);
    }
    
    /**
     * Find borrowing records by user
     */
    public List<BorrowingRecord> findByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br WHERE br.user.userId = :userId ORDER BY br.borrowDate DESC", 
                BorrowingRecord.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find borrowing records by book
     */
    public List<BorrowingRecord> findByBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br WHERE br.book.bookId = :bookId ORDER BY br.borrowDate DESC", 
                BorrowingRecord.class);
            query.setParameter("bookId", bookId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find active borrowings by user
     */
    public List<BorrowingRecord> findActiveBorrowingsByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br WHERE br.user.userId = :userId " +
                "AND br.status IN (:borrowed, :overdue) ORDER BY br.dueDate", 
                BorrowingRecord.class);
            query.setParameter("userId", userId);
            query.setParameter("borrowed", BorrowingRecord.BorrowStatus.BORROWED);
            query.setParameter("overdue", BorrowingRecord.BorrowStatus.OVERDUE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find overdue borrowings
     */
    public List<BorrowingRecord> findOverdueBorrowings() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br WHERE " +
                "br.status = :status OR (br.status = :borrowed AND br.dueDate < :now)", 
                BorrowingRecord.class);
            query.setParameter("status", BorrowingRecord.BorrowStatus.OVERDUE);
            query.setParameter("borrowed", BorrowingRecord.BorrowStatus.BORROWED);
            query.setParameter("now", LocalDateTime.now());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find borrowings by status
     */
    public List<BorrowingRecord> findByStatus(BorrowingRecord.BorrowStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br WHERE br.status = :status ORDER BY br.borrowDate DESC", 
                BorrowingRecord.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get borrowing statistics
     */
    public Object[] getBorrowingStatistics() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT " +
                "COUNT(br), " +
                "SUM(CASE WHEN br.status = :borrowed THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN br.status = :overdue THEN 1 ELSE 0 END), " +
                "SUM(CASE WHEN br.status = :returned THEN 1 ELSE 0 END) " +
                "FROM BorrowingRecord br", 
                Object[].class);
            query.setParameter("borrowed", BorrowingRecord.BorrowStatus.BORROWED);
            query.setParameter("overdue", BorrowingRecord.BorrowStatus.OVERDUE);
            query.setParameter("returned", BorrowingRecord.BorrowStatus.RETURNED);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Count active borrowings by user
     */
    public long countActiveBorrowingsByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(br) FROM BorrowingRecord br WHERE br.user.userId = :userId " +
                "AND br.status IN (:borrowed, :overdue)", 
                Long.class);
            query.setParameter("userId", userId);
            query.setParameter("borrowed", BorrowingRecord.BorrowStatus.BORROWED);
            query.setParameter("overdue", BorrowingRecord.BorrowStatus.OVERDUE);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get recent borrowings
     */
    public List<BorrowingRecord> getRecentBorrowings(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<BorrowingRecord> query = em.createQuery(
                "SELECT br FROM BorrowingRecord br ORDER BY br.borrowDate DESC", 
                BorrowingRecord.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

