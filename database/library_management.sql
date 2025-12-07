-- ============================================
-- Library Management System - DDL Script
-- Database: library_management
-- Author: Library Management System Team
-- Date: 2025-12-07
-- ============================================

-- Drop database if exists and create new
DROP DATABASE IF EXISTS library_management;
CREATE DATABASE library_management CHARACTER SET utf8mb4 COLLATE utf8mb4_unicode_ci;
USE library_management;

-- ============================================
-- Table: users
-- Description: Stores user information with roles
-- ============================================
CREATE TABLE users (
    user_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    username VARCHAR(50) NOT NULL UNIQUE,
    email VARCHAR(100) NOT NULL UNIQUE,
    password_hash VARCHAR(255) NOT NULL,
    first_name VARCHAR(50) NOT NULL,
    last_name VARCHAR(50) NOT NULL,
    phone VARCHAR(20),
    address TEXT,
    role ENUM('ADMIN', 'USER', 'INSTRUCTOR') NOT NULL DEFAULT 'USER',
    status ENUM('ACTIVE', 'INACTIVE', 'SUSPENDED') NOT NULL DEFAULT 'ACTIVE',
    registration_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    last_login TIMESTAMP NULL,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_username (username),
    INDEX idx_email (email),
    INDEX idx_role (role),
    INDEX idx_status (status)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: categories
-- Description: Book categories/genres
-- ============================================
CREATE TABLE categories (
    category_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    category_name VARCHAR(100) NOT NULL UNIQUE,
    description TEXT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_category_name (category_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: authors
-- Description: Book authors information
-- ============================================
CREATE TABLE authors (
    author_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    author_name VARCHAR(100) NOT NULL,
    biography TEXT,
    birth_date DATE,
    nationality VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    INDEX idx_author_name (author_name)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: books
-- Description: Book inventory management
-- ============================================
CREATE TABLE books (
    book_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    isbn VARCHAR(20) NOT NULL UNIQUE,
    title VARCHAR(255) NOT NULL,
    subtitle VARCHAR(255),
    publisher VARCHAR(100),
    publication_date DATE,
    edition VARCHAR(50),
    language VARCHAR(50) DEFAULT 'English',
    pages INT,
    description TEXT,
    cover_image_url VARCHAR(500),
    total_copies INT NOT NULL DEFAULT 1,
    available_copies INT NOT NULL DEFAULT 1,
    category_id BIGINT,
    status ENUM('AVAILABLE', 'UNAVAILABLE', 'DAMAGED', 'LOST') NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (category_id) REFERENCES categories(category_id) ON DELETE SET NULL,
    INDEX idx_isbn (isbn),
    INDEX idx_title (title),
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    CONSTRAINT chk_copies CHECK (available_copies >= 0 AND available_copies <= total_copies)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: book_authors
-- Description: Many-to-many relationship between books and authors
-- ============================================
CREATE TABLE book_authors (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    PRIMARY KEY (book_id, author_id),
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) ON DELETE CASCADE,
    INDEX idx_book (book_id),
    INDEX idx_author (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: borrowing_records
-- Description: Track book lending/borrowing
-- ============================================
CREATE TABLE borrowing_records (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NULL,
    status ENUM('BORROWED', 'RETURNED', 'OVERDUE', 'LOST') NOT NULL DEFAULT 'BORROWED',
    fine_amount DECIMAL(10, 2) DEFAULT 0.00,
    notes TEXT,
    issued_by BIGINT,
    returned_to BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    FOREIGN KEY (issued_by) REFERENCES users(user_id) ON DELETE SET NULL,
    FOREIGN KEY (returned_to) REFERENCES users(user_id) ON DELETE SET NULL,
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_status (status),
    INDEX idx_borrow_date (borrow_date),
    INDEX idx_due_date (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: reservations
-- Description: Book reservation system
-- ============================================
CREATE TABLE reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date TIMESTAMP NOT NULL,
    status ENUM('ACTIVE', 'FULFILLED', 'CANCELLED', 'EXPIRED') NOT NULL DEFAULT 'ACTIVE',
    notified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_status (status),
    INDEX idx_reservation_date (reservation_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: reviews
-- Description: Book reviews and ratings by users
-- ============================================
CREATE TABLE reviews (
    review_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    rating INT NOT NULL CHECK (rating >= 1 AND rating <= 5),
    review_text TEXT,
    review_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) ON DELETE CASCADE,
    UNIQUE KEY unique_user_book_review (user_id, book_id),
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_rating (rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Table: fines
-- Description: Fine management for overdue books
-- ============================================
CREATE TABLE fines (
    fine_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    record_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    fine_amount DECIMAL(10, 2) NOT NULL,
    fine_reason VARCHAR(255),
    fine_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_date TIMESTAMP NULL,
    status ENUM('PENDING', 'PAID', 'WAIVED') NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    FOREIGN KEY (record_id) REFERENCES borrowing_records(record_id) ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) ON DELETE CASCADE,
    INDEX idx_user (user_id),
    INDEX idx_status (status),
    INDEX idx_fine_date (fine_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- Insert Default Categories
-- ============================================
INSERT INTO categories (category_name, description) VALUES
('Fiction', 'Literary works created from imagination'),
('Non-Fiction', 'Factual and informative content'),
('Science Fiction', 'Speculative fiction based on science and technology'),
('Mystery', 'Detective and suspense novels'),
('Biography', 'Life stories of real people'),
('History', 'Historical events and analysis'),
('Technology', 'Books about technology and computing'),
('Science', 'Scientific research and discoveries'),
('Philosophy', 'Philosophical thoughts and theories'),
('Art', 'Books about art, design, and creativity'),
('Business', 'Business management and economics'),
('Self-Help', 'Personal development and improvement'),
('Education', 'Educational materials and textbooks'),
('Children', 'Books for children and young readers'),
('Romance', 'Love stories and romantic fiction');

-- ============================================
-- Insert Sample Authors
-- ============================================
INSERT INTO authors (author_name, biography, birth_date, nationality) VALUES
('George Orwell', 'English novelist and essayist, known for Animal Farm and 1984', '1903-06-25', 'British'),
('J.K. Rowling', 'British author, best known for the Harry Potter series', '1965-07-31', 'British'),
('Stephen King', 'American author of horror, supernatural fiction, and fantasy', '1947-09-21', 'American'),
('Agatha Christie', 'English writer known for detective novels', '1890-09-15', 'British'),
('Isaac Asimov', 'American writer and professor of biochemistry, prolific science fiction author', '1920-01-02', 'American'),
('Jane Austen', 'English novelist known for romantic fiction', '1775-12-16', 'British'),
('Mark Twain', 'American writer, humorist, and lecturer', '1835-11-30', 'American'),
('Ernest Hemingway', 'American novelist and short-story writer', '1899-07-21', 'American');

-- ============================================
-- Insert Sample Books
-- ============================================
INSERT INTO books (isbn, title, subtitle, publisher, publication_date, edition, language, pages, description, total_copies, available_copies, category_id) VALUES
('978-0-452-28423-4', '1984', NULL, 'Penguin Books', '1949-06-08', 'Reprint', 'English', 328, 'A dystopian novel set in Airstrip One, a province of the superstate Oceania', 5, 5, 1),
('978-0-7475-3269-9', 'Harry Potter and the Philosophers Stone', NULL, 'Bloomsbury', '1997-06-26', 'First Edition', 'English', 223, 'The first novel in the Harry Potter series', 10, 8, 1),
('978-0-385-12167-5', 'The Shining', NULL, 'Doubleday', '1977-01-28', 'First Edition', 'English', 447, 'A horror novel about a family in an isolated hotel', 3, 2, 4),
('978-0-06-112008-4', 'To Kill a Mockingbird', NULL, 'Harper Perennial', '1960-07-11', 'Reprint', 'English', 336, 'A novel about racial injustice in the American South', 7, 7, 1),
('978-0-553-38034-7', 'Foundation', NULL, 'Bantam Spectra', '1951-06-01', 'Reprint', 'English', 255, 'The first novel in the Foundation series', 4, 4, 3),
('978-0-14-143951-8', 'Pride and Prejudice', NULL, 'Penguin Classics', '1813-01-28', 'Reprint', 'English', 279, 'A romantic novel of manners', 6, 5, 15),
('978-0-7432-7356-5', 'The Da Vinci Code', NULL, 'Doubleday', '2003-03-18', 'First Edition', 'English', 454, 'A mystery thriller novel', 5, 3, 4),
('978-0-553-21311-8', 'A Brief History of Time', NULL, 'Bantam Books', '1988-04-01', 'First Edition', 'English', 256, 'A book on cosmology by physicist Stephen Hawking', 4, 4, 8);

-- ============================================
-- Link Books with Authors
-- ============================================
INSERT INTO book_authors (book_id, author_id) VALUES
(1, 1), -- 1984 by George Orwell
(2, 2), -- Harry Potter by J.K. Rowling
(3, 3), -- The Shining by Stephen King
(5, 5), -- Foundation by Isaac Asimov
(6, 6); -- Pride and Prejudice by Jane Austen

-- ============================================
-- Insert Default Users
-- Password for all accounts: admin123
-- BCrypt hash generated with 10 rounds
-- IMPORTANT: Change these passwords after first login in production!
-- ============================================
INSERT INTO users (username, email, password_hash, first_name, last_name, phone, address, role, status) VALUES
('admin', 'admin@library.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'System', 'Administrator', '+1234567890', '123 Library Street', 'ADMIN', 'ACTIVE'),
('instructor1', 'instructor@library.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'John', 'Smith', '+1234567891', '456 Academic Avenue', 'INSTRUCTOR', 'ACTIVE'),
('user1', 'user@library.com', '$2a$10$92IXUNpkjO0rOQ5byMi.Ye4oKoEa3Ro9llC/.og/at2.uheWG/igi', 'Jane', 'Doe', '+1234567892', '789 Student Lane', 'USER', 'ACTIVE');

-- ============================================
-- Views for Common Queries
-- ============================================

-- View: Available books with category information
CREATE VIEW available_books_view AS
SELECT 
    b.book_id,
    b.isbn,
    b.title,
    b.subtitle,
    b.publisher,
    b.publication_date,
    b.language,
    b.pages,
    b.description,
    b.cover_image_url,
    b.total_copies,
    b.available_copies,
    c.category_name,
    GROUP_CONCAT(a.author_name SEPARATOR ', ') AS authors,
    COALESCE(AVG(r.rating), 0) AS average_rating,
    COUNT(DISTINCT r.review_id) AS review_count
FROM books b
LEFT JOIN categories c ON b.category_id = c.category_id
LEFT JOIN book_authors ba ON b.book_id = ba.book_id
LEFT JOIN authors a ON ba.author_id = a.author_id
LEFT JOIN reviews r ON b.book_id = r.book_id
WHERE b.available_copies > 0 AND b.status = 'AVAILABLE'
GROUP BY b.book_id;

-- View: Currently borrowed books
CREATE VIEW current_borrowings_view AS
SELECT 
    br.record_id,
    br.borrow_date,
    br.due_date,
    br.status,
    br.fine_amount,
    u.user_id,
    u.username,
    u.first_name,
    u.last_name,
    u.email,
    b.book_id,
    b.isbn,
    b.title,
    DATEDIFF(NOW(), br.due_date) AS days_overdue
FROM borrowing_records br
JOIN users u ON br.user_id = u.user_id
JOIN books b ON br.book_id = b.book_id
WHERE br.status IN ('BORROWED', 'OVERDUE');

-- View: User borrowing history
CREATE VIEW user_borrowing_history_view AS
SELECT 
    u.user_id,
    u.username,
    u.first_name,
    u.last_name,
    COUNT(br.record_id) AS total_borrowed,
    SUM(CASE WHEN br.status = 'BORROWED' THEN 1 ELSE 0 END) AS currently_borrowed,
    SUM(CASE WHEN br.status = 'OVERDUE' THEN 1 ELSE 0 END) AS overdue_books,
    SUM(br.fine_amount) AS total_fines
FROM users u
LEFT JOIN borrowing_records br ON u.user_id = br.user_id
GROUP BY u.user_id;

-- ============================================
-- Stored Procedures
-- ============================================

DELIMITER //

-- Procedure: Borrow a book
CREATE PROCEDURE borrow_book(
    IN p_user_id BIGINT,
    IN p_book_id BIGINT,
    IN p_issued_by BIGINT,
    IN p_days INT
)
BEGIN
    DECLARE v_available_copies INT;
    
    -- Check if book is available
    SELECT available_copies INTO v_available_copies
    FROM books
    WHERE book_id = p_book_id AND status = 'AVAILABLE';
    
    IF v_available_copies > 0 THEN
        -- Create borrowing record
        INSERT INTO borrowing_records (user_id, book_id, due_date, issued_by)
        VALUES (p_user_id, p_book_id, DATE_ADD(NOW(), INTERVAL p_days DAY), p_issued_by);
        
        -- Update available copies
        UPDATE books
        SET available_copies = available_copies - 1
        WHERE book_id = p_book_id;
        
        SELECT 'Book borrowed successfully' AS message;
    ELSE
        SELECT 'Book not available' AS message;
    END IF;
END //

-- Procedure: Return a book
CREATE PROCEDURE return_book(
    IN p_record_id BIGINT,
    IN p_returned_to BIGINT
)
BEGIN
    DECLARE v_book_id BIGINT;
    DECLARE v_due_date TIMESTAMP;
    DECLARE v_fine DECIMAL(10, 2);
    DECLARE v_days_overdue INT;
    
    -- Get record details
    SELECT book_id, due_date INTO v_book_id, v_due_date
    FROM borrowing_records
    WHERE record_id = p_record_id AND status IN ('BORROWED', 'OVERDUE');
    
    IF v_book_id IS NOT NULL THEN
        -- Calculate fine if overdue (e.g., $1 per day)
        SET v_days_overdue = DATEDIFF(NOW(), v_due_date);
        IF v_days_overdue > 0 THEN
            SET v_fine = v_days_overdue * 1.00;
        ELSE
            SET v_fine = 0.00;
        END IF;
        
        -- Update borrowing record
        UPDATE borrowing_records
        SET return_date = NOW(),
            status = 'RETURNED',
            fine_amount = v_fine,
            returned_to = p_returned_to
        WHERE record_id = p_record_id;
        
        -- Update available copies
        UPDATE books
        SET available_copies = available_copies + 1
        WHERE book_id = v_book_id;
        
        -- Create fine record if applicable
        IF v_fine > 0 THEN
            INSERT INTO fines (record_id, user_id, fine_amount, fine_reason)
            SELECT p_record_id, user_id, v_fine, CONCAT('Overdue by ', v_days_overdue, ' days')
            FROM borrowing_records
            WHERE record_id = p_record_id;
        END IF;
        
        SELECT 'Book returned successfully' AS message, v_fine AS fine_amount;
    ELSE
        SELECT 'Invalid record or book already returned' AS message;
    END IF;
END //

DELIMITER ;

-- ============================================
-- Triggers
-- ============================================

DELIMITER //

-- Trigger: Update overdue status
CREATE TRIGGER update_overdue_status
BEFORE UPDATE ON borrowing_records
FOR EACH ROW
BEGIN
    IF NEW.status = 'BORROWED' AND NEW.due_date < NOW() THEN
        SET NEW.status = 'OVERDUE';
    END IF;
END //

-- Trigger: Prevent negative available copies
CREATE TRIGGER check_available_copies
BEFORE UPDATE ON books
FOR EACH ROW
BEGIN
    IF NEW.available_copies < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Available copies cannot be negative';
    END IF;
END //

DELIMITER ;

-- ============================================
-- End of DDL Script
-- ============================================

