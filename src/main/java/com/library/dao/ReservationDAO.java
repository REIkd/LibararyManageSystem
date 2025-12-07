package com.library.dao;

import com.library.entity.Reservation;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.time.LocalDateTime;
import java.util.List;

/**
 * DAO class for Reservation entity operations
 */
public class ReservationDAO extends GenericDAO<Reservation, Long> {
    
    public ReservationDAO() {
        super(Reservation.class);
    }
    
    /**
     * Find reservations by user
     */
    public List<Reservation> findByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.user.userId = :userId ORDER BY r.reservationDate DESC", 
                Reservation.class);
            query.setParameter("userId", userId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find active reservations by user
     */
    public List<Reservation> findActiveReservationsByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.user.userId = :userId " +
                "AND r.status = :status ORDER BY r.reservationDate DESC", 
                Reservation.class);
            query.setParameter("userId", userId);
            query.setParameter("status", Reservation.ReservationStatus.ACTIVE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find reservations by book
     */
    public List<Reservation> findByBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.book.bookId = :bookId ORDER BY r.reservationDate", 
                Reservation.class);
            query.setParameter("bookId", bookId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find active reservations by book
     */
    public List<Reservation> findActiveReservationsByBook(Long bookId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.book.bookId = :bookId " +
                "AND r.status = :status ORDER BY r.reservationDate", 
                Reservation.class);
            query.setParameter("bookId", bookId);
            query.setParameter("status", Reservation.ReservationStatus.ACTIVE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find reservations by status
     */
    public List<Reservation> findByStatus(Reservation.ReservationStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE r.status = :status ORDER BY r.reservationDate DESC", 
                Reservation.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find expired reservations
     */
    public List<Reservation> findExpiredReservations() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Reservation> query = em.createQuery(
                "SELECT r FROM Reservation r WHERE " +
                "r.status = :active AND r.expiryDate < :now", 
                Reservation.class);
            query.setParameter("active", Reservation.ReservationStatus.ACTIVE);
            query.setParameter("now", LocalDateTime.now());
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Count active reservations by user
     */
    public long countActiveReservationsByUser(Long userId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(r) FROM Reservation r WHERE r.user.userId = :userId " +
                "AND r.status = :status", 
                Long.class);
            query.setParameter("userId", userId);
            query.setParameter("status", Reservation.ReservationStatus.ACTIVE);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
}

