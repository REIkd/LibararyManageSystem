package com.library.dao;

import com.library.entity.Book;
import com.library.entity.Category;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO class for Book entity operations
 */
public class BookDAO extends GenericDAO<Book, Long> {
    
    public BookDAO() {
        super(Book.class);
    }
    
    /**
     * Find book by ISBN
     */
    public Optional<Book> findByIsbn(String isbn) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.isbn = :isbn", Book.class);
            query.setParameter("isbn", isbn);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find books by category
     */
    public List<Book> findByCategory(Long categoryId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.category.categoryId = :categoryId", Book.class);
            query.setParameter("categoryId", categoryId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find books by status
     */
    public List<Book> findByStatus(Book.BookStatus status) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.status = :status", Book.class);
            query.setParameter("status", status);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find available books
     */
    public List<Book> findAvailableBooks() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b WHERE b.availableCopies > 0 AND b.status = :status", Book.class);
            query.setParameter("status", Book.BookStatus.AVAILABLE);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Search books by title, author, ISBN, or description
     */
    public List<Book> searchBooks(String searchTerm) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT DISTINCT b FROM Book b LEFT JOIN b.authors a WHERE " +
                "LOWER(b.title) LIKE LOWER(:term) OR " +
                "LOWER(b.subtitle) LIKE LOWER(:term) OR " +
                "LOWER(b.isbn) LIKE LOWER(:term) OR " +
                "LOWER(b.publisher) LIKE LOWER(:term) OR " +
                "LOWER(b.description) LIKE LOWER(:term) OR " +
                "LOWER(a.authorName) LIKE LOWER(:term)", Book.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find books by author
     */
    public List<Book> findByAuthor(Long authorId) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT DISTINCT b FROM Book b JOIN b.authors a WHERE a.authorId = :authorId", Book.class);
            query.setParameter("authorId", authorId);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get most borrowed books
     */
    public List<Book> getMostBorrowedBooks(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b LEFT JOIN b.borrowingRecords br " +
                "GROUP BY b ORDER BY COUNT(br) DESC", Book.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get recently added books
     */
    public List<Book> getRecentlyAddedBooks(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT b FROM Book b ORDER BY b.createdAt DESC", Book.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Count available books
     */
    public long countAvailableBooks() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Long> query = em.createQuery(
                "SELECT COUNT(b) FROM Book b WHERE b.availableCopies > 0 AND b.status = :status", Long.class);
            query.setParameter("status", Book.BookStatus.AVAILABLE);
            return query.getSingleResult();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get books with reviews
     */
    public List<Book> getBooksWithReviews() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Book> query = em.createQuery(
                "SELECT DISTINCT b FROM Book b LEFT JOIN FETCH b.reviews WHERE SIZE(b.reviews) > 0", Book.class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

