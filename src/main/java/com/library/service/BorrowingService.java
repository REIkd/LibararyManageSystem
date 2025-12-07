package com.library.service;

import com.library.dao.BorrowingRecordDAO;
import com.library.dao.BookDAO;
import com.library.dao.UserDAO;
import com.library.entity.BorrowingRecord;
import com.library.entity.Book;
import com.library.entity.User;

import java.math.BigDecimal;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

/**
 * Service class for borrowing operations
 */
public class BorrowingService {
    
    private final BorrowingRecordDAO borrowingRecordDAO;
    private final BookDAO bookDAO;
    private final UserDAO userDAO;
    private static final int DEFAULT_BORROW_DAYS = 14;
    private static final BigDecimal FINE_PER_DAY = new BigDecimal("1.00");
    
    public BorrowingService() {
        this.borrowingRecordDAO = new BorrowingRecordDAO();
        this.bookDAO = new BookDAO();
        this.userDAO = new UserDAO();
    }
    
    /**
     * Borrow a book
     */
    public Map<String, Object> borrowBook(Long userId, Long bookId, Long issuedById, int days) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            // Validate user
            Optional<User> userOpt = userDAO.findById(userId);
            if (userOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "User not found");
                return response;
            }
            
            // Validate book
            Optional<Book> bookOpt = bookDAO.findById(bookId);
            if (bookOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Book not found");
                return response;
            }
            
            Book book = bookOpt.get();
            User user = userOpt.get();
            
            // Check if book is available
            if (!book.isAvailable()) {
                response.put("success", false);
                response.put("message", "Book is not available for borrowing");
                return response;
            }
            
            // Check user's active borrowings
            long activeBorrowings = borrowingRecordDAO.countActiveBorrowingsByUser(userId);
            if (activeBorrowings >= 5) {
                response.put("success", false);
                response.put("message", "Maximum borrowing limit (5 books) reached");
                return response;
            }
            
            // Create borrowing record
            LocalDateTime dueDate = LocalDateTime.now().plusDays(days > 0 ? days : DEFAULT_BORROW_DAYS);
            BorrowingRecord record = new BorrowingRecord(user, book, dueDate);
            
            if (issuedById != null) {
                Optional<User> issuedByOpt = userDAO.findById(issuedById);
                issuedByOpt.ifPresent(record::setIssuedBy);
            }
            
            BorrowingRecord savedRecord = borrowingRecordDAO.save(record);
            
            // Update book availability
            book.setAvailableCopies(book.getAvailableCopies() - 1);
            bookDAO.update(book);
            
            response.put("success", true);
            response.put("message", "Book borrowed successfully");
            response.put("record", getBorrowingRecordInfo(savedRecord));
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error borrowing book: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Return a book
     */
    public Map<String, Object> returnBook(Long recordId, Long returnedToId) {
        Map<String, Object> response = new HashMap<>();
        
        try {
            Optional<BorrowingRecord> recordOpt = borrowingRecordDAO.findById(recordId);
            if (recordOpt.isEmpty()) {
                response.put("success", false);
                response.put("message", "Borrowing record not found");
                return response;
            }
            
            BorrowingRecord record = recordOpt.get();
            
            // Check if already returned
            if (record.getStatus() == BorrowingRecord.BorrowStatus.RETURNED) {
                response.put("success", false);
                response.put("message", "Book already returned");
                return response;
            }
            
            // Calculate fine if overdue
            BigDecimal fine = BigDecimal.ZERO;
            if (record.isOverdue()) {
                long daysOverdue = record.getDaysOverdue();
                fine = FINE_PER_DAY.multiply(BigDecimal.valueOf(daysOverdue));
                record.setFineAmount(fine);
            }
            
            // Update record
            record.setReturnDate(LocalDateTime.now());
            record.setStatus(BorrowingRecord.BorrowStatus.RETURNED);
            
            if (returnedToId != null) {
                Optional<User> returnedToOpt = userDAO.findById(returnedToId);
                returnedToOpt.ifPresent(record::setReturnedTo);
            }
            
            BorrowingRecord updatedRecord = borrowingRecordDAO.update(record);
            
            // Update book availability
            Book book = record.getBook();
            book.setAvailableCopies(book.getAvailableCopies() + 1);
            bookDAO.update(book);
            
            response.put("success", true);
            response.put("message", "Book returned successfully");
            response.put("record", getBorrowingRecordInfo(updatedRecord));
            response.put("fine", fine);
            
        } catch (Exception e) {
            response.put("success", false);
            response.put("message", "Error returning book: " + e.getMessage());
        }
        
        return response;
    }
    
    /**
     * Get user's borrowing history
     */
    public List<Map<String, Object>> getUserBorrowingHistory(Long userId) {
        List<BorrowingRecord> records = borrowingRecordDAO.findByUser(userId);
        return records.stream().map(this::getBorrowingRecordInfo).collect(Collectors.toList());
    }
    
    /**
     * Get user's active borrowings
     */
    public List<Map<String, Object>> getUserActiveBorrowings(Long userId) {
        List<BorrowingRecord> records = borrowingRecordDAO.findActiveBorrowingsByUser(userId);
        return records.stream().map(this::getBorrowingRecordInfo).collect(Collectors.toList());
    }
    
    /**
     * Get overdue borrowings
     */
    public List<Map<String, Object>> getOverdueBorrowings() {
        List<BorrowingRecord> records = borrowingRecordDAO.findOverdueBorrowings();
        return records.stream().map(this::getBorrowingRecordInfo).collect(Collectors.toList());
    }
    
    /**
     * Get all borrowing records
     */
    public List<Map<String, Object>> getAllBorrowings() {
        List<BorrowingRecord> records = borrowingRecordDAO.findAll();
        return records.stream().map(this::getBorrowingRecordInfo).collect(Collectors.toList());
    }
    
    /**
     * Get all active borrowings
     */
    public List<Map<String, Object>> getActiveBorrowings() {
        List<BorrowingRecord> borrowed = borrowingRecordDAO.findByStatus(BorrowingRecord.BorrowStatus.BORROWED);
        List<BorrowingRecord> overdue = borrowingRecordDAO.findByStatus(BorrowingRecord.BorrowStatus.OVERDUE);
        
        List<BorrowingRecord> allActive = new ArrayList<>();
        allActive.addAll(borrowed);
        allActive.addAll(overdue);
        
        return allActive.stream().map(this::getBorrowingRecordInfo).collect(Collectors.toList());
    }
    
    /**
     * Get borrowing record information map
     */
    private Map<String, Object> getBorrowingRecordInfo(BorrowingRecord record) {
        Map<String, Object> info = new HashMap<>();
        info.put("recordId", record.getRecordId());
        info.put("borrowDate", record.getBorrowDate());
        info.put("dueDate", record.getDueDate());
        info.put("returnDate", record.getReturnDate());
        info.put("status", record.getStatus().name());
        info.put("fineAmount", record.getFineAmount());
        info.put("notes", record.getNotes());
        info.put("isOverdue", record.isOverdue());
        info.put("daysOverdue", record.getDaysOverdue());
        
        // User info
        Map<String, Object> userInfo = new HashMap<>();
        userInfo.put("userId", record.getUser().getUserId());
        userInfo.put("username", record.getUser().getUsername());
        userInfo.put("fullName", record.getUser().getFullName());
        info.put("user", userInfo);
        
        // Book info
        Map<String, Object> bookInfo = new HashMap<>();
        bookInfo.put("bookId", record.getBook().getBookId());
        bookInfo.put("title", record.getBook().getTitle());
        bookInfo.put("isbn", record.getBook().getIsbn());
        info.put("book", bookInfo);
        
        return info;
    }
}

