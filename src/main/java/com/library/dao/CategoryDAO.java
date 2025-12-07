package com.library.dao;

import com.library.entity.Category;
import com.library.util.JPAUtil;
import jakarta.persistence.EntityManager;
import jakarta.persistence.NoResultException;
import jakarta.persistence.TypedQuery;

import java.util.List;
import java.util.Optional;

/**
 * DAO class for Category entity operations
 */
public class CategoryDAO extends GenericDAO<Category, Long> {
    
    public CategoryDAO() {
        super(Category.class);
    }
    
    /**
     * Find category by name
     */
    public Optional<Category> findByName(String categoryName) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE c.categoryName = :name", Category.class);
            query.setParameter("name", categoryName);
            return Optional.of(query.getSingleResult());
        } catch (NoResultException e) {
            return Optional.empty();
        } finally {
            em.close();
        }
    }
    
    /**
     * Search categories by name
     */
    public List<Category> searchCategories(String searchTerm) {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Category> query = em.createQuery(
                "SELECT c FROM Category c WHERE " +
                "LOWER(c.categoryName) LIKE LOWER(:term) OR " +
                "LOWER(c.description) LIKE LOWER(:term)", Category.class);
            query.setParameter("term", "%" + searchTerm + "%");
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Get categories with book count
     */
    public List<Object[]> getCategoriesWithBookCount() {
        EntityManager em = JPAUtil.getEntityManager();
        try {
            TypedQuery<Object[]> query = em.createQuery(
                "SELECT c, COUNT(b) FROM Category c LEFT JOIN c.books b GROUP BY c ORDER BY c.categoryName", Object[].class);
            return query.getResultList();
        } finally {
            em.close();
        }
    }
    
    /**
     * Check if category name exists
     */
    public boolean categoryNameExists(String categoryName) {
        return findByName(categoryName).isPresent();
    }
}

