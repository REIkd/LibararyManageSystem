package com.library.dao;

import com.library.entity.Author;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.TypedQuery;

import java.util.List;

/**
 * DAO class for Author entity operations
 */
public class AuthorDAO extends GenericDAO<Author, Long> {
    
    public AuthorDAO() {
        super(Author.class);
    }
    
    /**
     * Find authors by name
     */
    public List<Author> findByName(String authorName) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE LOWER(a.authorName) LIKE LOWER(:name)", Author.class);
            query.setParameter("name", "%" + authorName + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Find authors by nationality
     */
    public List<Author> findByNationality(String nationality) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE a.nationality = :nationality", Author.class);
            query.setParameter("nationality", nationality);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Search authors
     */
    public List<Author> searchAuthors(String searchTerm) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a WHERE " +
                "LOWER(a.authorName) LIKE LOWER(:term) OR " +
                "LOWER(a.biography) LIKE LOWER(:term) OR " +
                "LOWER(a.nationality) LIKE LOWER(:term)", Author.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get authors with book count
     */
    public List<Object[]> getAuthorsWithBookCount() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT a, COUNT(b) FROM Author a LEFT JOIN a.books b GROUP BY a ORDER BY a.authorName", Object[].class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get popular authors (most books)
     */
    public List<Author> getPopularAuthors(int limit) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Author> query = em.createQuery(
                "SELECT a FROM Author a LEFT JOIN a.books b GROUP BY a ORDER BY COUNT(b) DESC", Author.class);
            query.setMaxResults(limit);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
}

