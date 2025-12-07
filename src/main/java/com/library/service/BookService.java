package com.library.service;

import com.library.dao.BookDAO;
import com.library.dao.CategoryDAO;
import com.library.dao.AuthorDAO;
import com.library.entity.Book;
import com.library.entity.Category;
import com.library.entity.Author;

import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for book management operations
 */
public class BookService {
    
    private final BookDAO bookDAO;
    private final CategoryDAO categoryDAO;
    private final AuthorDAO authorDAO;
    
    public BookService() {
        this.bookDAO = new BookDAO();
        this.categoryDAO = new CategoryDAO();
        this.authorDAO = new AuthorDAO();
    }
    
    /**
     * Get all books
     */
    public List<Map<String, Object>> getAllBooks() {
        List<Book> books = bookDAO.findAll();
        return books.stream().map(this::getBookInfo).collect(Collectors.toList());
    }
    
    /**
     * Get book by ID
     */
    public Optional<Map<String, Object>> getBookById(Long bookId) {
        Optional<Book> bookOpt = bookDAO.findById(bookId);
        return bookOpt.map(this::getBookInfo);
    }
    
    /**
     * Get available books
     */
    public List<Map<String, Object>> getAvailableBooks() {
        List<Book> books = bookDAO.findAvailableBooks();
        return books.stream().map(this::getBookInfo).collect(Collectors.toList());
    }
    
    /**
     * Search books
     */
    public List<Map<String, Object>> searchBooks(String searchTerm) {
        List<Book> books = bookDAO.searchBooks(searchTerm);
        return books.stream().map(this::getBookInfo).collect(Collectors.toList());
    }
    
    /**
     * Get books by category
     */
    public List<Map<String, Object>> getBooksByCategory(Long categoryId) {
        List<Book> books = bookDAO.findByCategory(categoryId);
        return books.stream().map(this::getBookInfo).collect(Collectors.toList());
    }
    
    /**
     * Create book
     */
    public Map<String, Object> createBook(Map<String, Object> bookData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            String isbn = (String) bookData.get("isbn");
            String title = (String) bookData.get("title");
            
            // Validate required fields
            if (isbn == null || title == null) {
                response.put("success", false);
                response.put("message", "ISBN and title are required");
                return response;
            }
            
            // Check ISBN uniqueness
            if (bookDAO.findByIsbn(isbn).isPresent()) {
                response.put("success", false);
                response.put("message", "ISBN already exists");
                return response;
            }
            
            // Create book
            Book book = new Book();
            book.setIsbn(isbn);
            book.setTitle(title);
            book.setSubtitle((String) bookData.get("subtitle"));
            book.setPublisher((String) bookData.get("publisher"));
            book.setEdition((String) bookData.get("edition"));
            book.setLanguage((String) bookData.get("language"));
            book.setDescription((String) bookData.get("description"));
            book.setCoverImageUrl((String) bookData.get("coverImageUrl"));
            
            if (bookData.containsKey("pages")) {
                book.setPages(((Number) bookData.get("pages")).intValue());
            }
            if (bookData.containsKey("totalCopies")) {
                int copies = ((Number) bookData.get("totalCopies")).intValue();
                book.setTotalCopies(copies);
                book.setAvailableCopies(copies);
            }
            
            // Set category
            if (bookData.containsKey("categoryId")) {
                Long categoryId = ((Number) bookData.get("categoryId")).longValue();
                Optional<Category> categoryOpt = categoryDAO.findById(categoryId);
                categoryOpt.ifPresent(book::setCategory);
            }
            
            Book savedBook = bookDAO.save(book);
            
            // Add authors
            if (bookData.containsKey("authorIds")) {
                @SuppressWarnings("unchecked")
                List<Number> authorIds = (List<Number>) bookData.get("authorIds");
                for (Number authorId : authorIds) {
                    Optional<Author> authorOpt = authorDAO.findById(authorId.longValue());
                    authorOpt.ifPresent(savedBook::addAuthor);
                }
                bookDAO.update(savedBook);
            }
            
            response.put("success", true);
            response.put("message", "Book created successfully");
            response.put("book", getBookInfo(savedBook));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error creating book: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Update book
     */
    public Map<String, Object> updateBook(Long bookId, Map<String, Object> bookData) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            if (bookOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Book not found");
                return response;
            }
            
            Book book = bookOpt.get();
            
            // Update fields
            if (bookData.containsKey("title")) {
                book.setTitle((String) bookData.get("title"));
            }
            if (bookData.containsKey("subtitle")) {
                book.setSubtitle((String) bookData.get("subtitle"));
            }
            if (bookData.containsKey("publisher")) {
                book.setPublisher((String) bookData.get("publisher"));
            }
            if (bookData.containsKey("description")) {
                book.setDescription((String) bookData.get("description"));
            }
            if (bookData.containsKey("totalCopies")) {
                book.setTotalCopies(((Number) bookData.get("totalCopies")).intValue());
            }
            if (bookData.containsKey("availableCopies")) {
                book.setAvailableCopies(((Number) bookData.get("availableCopies")).intValue());
            }
            if (bookData.containsKey("categoryId")) {
                Long categoryId = ((Number) bookData.get("categoryId")).longValue();
                Optional<Category> categoryOpt = categoryDAO.findById(categoryId);
                categoryOpt.ifPresent(book::setCategory);
            }
            
            Book updatedBook = bookDAO.update(book);
            
            response.put("success", true);
            response.put("message", "Book updated successfully");
            response.put("book", getBookInfo(updatedBook));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error updating book: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Delete book
     */
    public Map<String, Object> deleteBook(Long bookId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            if (bookOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Book not found");
                return response;
            }
            
            bookDAO.delete(bookId);
            
            response.put("success", true);
            response.put("message", "Book deleted successfully");
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error deleting book: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Get book information map
     */
    private Map<String, Object> getBookInfo(Book book) {
        Map<String, Object> bookInfo = new HashMap<>();
        bookInfo.put("bookId", book.getBookId());
        bookInfo.put("isbn", book.getIsbn());
        bookInfo.put("title", book.getTitle());
        bookInfo.put("subtitle", book.getSubtitle());
        bookInfo.put("publisher", book.getPublisher());
        bookInfo.put("publicationDate", book.getPublicationDate());
        bookInfo.put("edition", book.getEdition());
        bookInfo.put("language", book.getLanguage());
        bookInfo.put("pages", book.getPages());
        bookInfo.put("description", book.getDescription());
        bookInfo.put("coverImageUrl", book.getCoverImageUrl());
        bookInfo.put("totalCopies", book.getTotalCopies());
        bookInfo.put("availableCopies", book.getAvailableCopies());
        bookInfo.put("status", book.getStatus().name());
        
        if (book.getCategory() != null) {
            Map<String, Object> categoryInfo = new HashMap<>();
            categoryInfo.put("categoryId", book.getCategory().getCategoryId());
            categoryInfo.put("categoryName", book.getCategory().getCategoryName());
            bookInfo.put("category", categoryInfo);
        }
        
        List<Map<String, Object>> authors = book.getAuthors().stream().map(author -> {
            Map<String, Object> authorInfo = new HashMap<>();
            authorInfo.put("authorId", author.getAuthorId());
            authorInfo.put("authorName", author.getAuthorName());
            return authorInfo;
        }).collect(Collectors.toList());
        bookInfo.put("authors", authors);
        
        return bookInfo;
    }
}

