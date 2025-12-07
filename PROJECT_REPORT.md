# Library Management System - Project Report

---

## 1. Student Information

**Student Name:** [Your Name Here]  
**Student Number:** [Your Student Number Here]  
**Course:** Jakarta EE Web Application Development  
**Project:** Library Management System  
**Date:** December 2025

---

## 2. Design Documentation

### Executive Summary

The Library Management System is a comprehensive web-based application built using Jakarta EE 9.1, designed to streamline library operations including book inventory management, member registration, and lending processes. The system supports three distinct user roles (Admin, Instructor, and User) with role-based access control, providing a secure and efficient platform for managing library resources.

**Key Technologies:**
- Jakarta EE 9.1 (Servlets, JAX-RS, JPA)
- MySQL 8.0 Database
- Hibernate ORM
- BCrypt Password Encryption
- JWT Token Authentication
- HTML5, CSS3, JavaScript (Vanilla)
- Apache Tomcat 10
- Maven Build Tool

---

## 2.1 Requirement Analysis [20 marks]

### 2.1.1 Business Needs

The library management system addresses the following critical business requirements:

1. **Automated Book Inventory Management**
   - Track book availability in real-time
   - Maintain accurate records of total and available copies
   - Support multiple copies of the same book
   - Categorize books by genre/category
   - Manage author information and relationships

2. **Efficient Member Management**
   - Register new members with role-based access
   - Maintain member profiles and contact information
   - Track borrowing history for each member
   - Manage user status (Active, Inactive, Suspended)

3. **Streamlined Lending Process**
   - Quick book checkout and return
   - Automatic due date calculation
   - Overdue detection and fine calculation
   - Borrowing limit enforcement (max 5 books per user)
   - Support for book reservations

4. **Administrative Control**
   - Comprehensive reporting and statistics
   - User and book management capabilities
   - Fine management and tracking
   - System-wide monitoring and control

### 2.1.2 User Requirements

#### **End Users (Library Members)**
- Browse and search available books
- View detailed book information
- Borrow and return books online
- Track personal borrowing history
- View due dates and overdue status
- Receive notifications for overdue books
- Write reviews and rate books

#### **Instructors (Library Staff)**
- All user capabilities
- Issue books on behalf of members
- Process book returns
- View all borrowing records
- Manage overdue books
- Calculate and collect fines

#### **Administrators (System Managers)**
- All instructor capabilities
- Add, edit, and delete books
- Manage book categories and authors
- Create and manage user accounts
- Assign and modify user roles
- Generate system reports
- View comprehensive statistics
- System configuration and maintenance

### 2.1.3 Functional Requirements

#### **FR1: User Authentication and Authorization**
- **FR1.1:** Secure user registration with email validation
- **FR1.2:** Login with username/email and password
- **FR1.3:** Password encryption using BCrypt (10 rounds)
- **FR1.4:** JWT token-based session management (24-hour expiration)
- **FR1.5:** Role-based access control (RBAC)
- **FR1.6:** Password change functionality
- **FR1.7:** Automatic logout on token expiration

#### **FR2: Book Management**
- **FR2.1:** CRUD operations for books (Create, Read, Update, Delete)
- **FR2.2:** ISBN-based unique identification
- **FR2.3:** Multi-author support (Many-to-Many relationship)
- **FR2.4:** Category/Genre classification
- **FR2.5:** Real-time availability tracking
- **FR2.6:** Book search by title, author, ISBN, publisher, or description
- **FR2.7:** Cover image support
- **FR2.8:** Detailed book information (publisher, pages, language, edition)

#### **FR3: Borrowing System**
- **FR3.1:** Book checkout with automatic due date (14 days default)
- **FR3.2:** Maximum 5 concurrent borrowings per user
- **FR3.3:** Book return processing
- **FR3.4:** Automatic overdue detection
- **FR3.5:** Fine calculation ($1.00 per day overdue)
- **FR3.6:** Borrowing history tracking
- **FR3.7:** Book reservation for unavailable items
- **FR3.8:** Email notifications (future enhancement)

#### **FR4: User Management (Admin)**
- **FR4.1:** View all registered users
- **FR4.2:** Create new user accounts with role assignment
- **FR4.3:** Edit user information
- **FR4.4:** Delete user accounts
- **FR4.5:** Change user roles and status
- **FR4.6:** User search functionality
- **FR4.7:** User statistics and reporting

#### **FR5: Review and Rating System**
- **FR5.1:** Users can rate books (1-5 stars)
- **FR5.2:** Users can write text reviews
- **FR5.3:** One review per user per book
- **FR5.4:** Average rating calculation
- **FR5.5:** Review modification and deletion

### 2.1.4 Non-Functional Requirements

#### **NFR1: Security**
- **NFR1.1:** All passwords must be hashed using BCrypt
- **NFR1.2:** JWT tokens for stateless authentication
- **NFR1.3:** HTTPS support for production (recommended)
- **NFR1.4:** SQL injection prevention through JPA
- **NFR1.5:** XSS protection through input validation
- **NFR1.6:** CORS configuration for API security
- **NFR1.7:** Session timeout after 24 hours

#### **NFR2: Performance**
- **NFR2.1:** Page load time < 2 seconds
- **NFR2.2:** API response time < 500ms
- **NFR2.3:** Support for 100+ concurrent users
- **NFR2.4:** Database query optimization with proper indexing
- **NFR2.5:** Connection pooling (10 connections)

#### **NFR3: Usability**
- **NFR3.1:** Intuitive and user-friendly interface
- **NFR3.2:** Responsive design (mobile, tablet, desktop)
- **NFR3.3:** Clear error messages and feedback
- **NFR3.4:** Consistent design patterns
- **NFR3.5:** Accessibility compliance (WCAG 2.1 AA)
- **NFR3.6:** Multi-language support (future enhancement)

#### **NFR4: Reliability**
- **NFR4.1:** 99.9% uptime target
- **NFR4.2:** Automatic error logging
- **NFR4.3:** Database backup (daily recommended)
- **NFR4.4:** Transaction management with rollback
- **NFR4.5:** Data integrity constraints

#### **NFR5: Maintainability**
- **NFR5.1:** Clean code architecture (3-tier design)
- **NFR5.2:** Comprehensive code documentation
- **NFR5.3:** Modular component design
- **NFR5.4:** Version control (Git)
- **NFR5.5:** Automated build process (Maven)

#### **NFR6: Scalability**
- **NFR6.1:** Horizontal scaling capability
- **NFR6.2:** Database partitioning support
- **NFR6.3:** Caching strategy (Redis for future)
- **NFR6.4:** Load balancing ready
- **NFR6.5:** Microservices migration path

### 2.1.5 System Constraints

#### **Technical Constraints:**
- Must use Jakarta EE 9.1 or higher
- MySQL 8.0 database required
- Java 11+ runtime environment
- Apache Tomcat 10 or compatible server
- RESTful API architecture mandatory

#### **Business Constraints:**
- Maximum borrowing period: 90 days
- Maximum concurrent borrowings: 5 books per user
- Fine rate: $1.00 per day (configurable)
- Minimum password length: 6 characters
- Book ISBN must be unique

#### **User Constraints:**
- Users must have valid email address
- Users must be ACTIVE status to borrow books
- Overdue books must be returned before new borrowing (future)
- Fines must be paid before new borrowing (future)

### 2.1.6 Use Case Analysis

#### **Primary Actors:**
1. **Library Member (User)** - Borrows and returns books
2. **Library Staff (Instructor)** - Issues books and manages returns
3. **System Administrator (Admin)** - Full system management

#### **Key Use Cases:**

**UC1: Register Account**
- Actor: New User
- Precondition: None
- Main Flow:
  1. User clicks "Register"
  2. User fills registration form (username, email, password, name)
  3. System validates input
  4. System creates account with USER role
  5. System generates JWT token
  6. User is logged in automatically
- Postcondition: New user account created and logged in

**UC2: Browse and Search Books**
- Actor: Any User (including guests)
- Precondition: None
- Main Flow:
  1. User navigates to Books page
  2. System displays all available books in grid layout
  3. User can search by entering keywords
  4. System filters books in real-time
  5. User clicks book to view details
  6. System displays full book information
- Postcondition: User finds desired book information

**UC3: Borrow Book**
- Actor: Registered User (ACTIVE status)
- Precondition: User is logged in, Book is available
- Main Flow:
  1. User views book details
  2. User clicks "Borrow This Book"
  3. System checks user's active borrowing count (< 5)
  4. System creates borrowing record with due date (14 days)
  5. System decrements available copies
  6. System displays success message
  7. Book appears in "My Borrowings"
- Postcondition: Book borrowed, availability updated

**UC4: Return Book**
- Actor: User or Instructor
- Precondition: User has borrowed book
- Main Flow:
  1. User/Instructor navigates to borrowings
  2. User/Instructor clicks "Return Book"
  3. System checks if overdue
  4. If overdue, system calculates fine ($1/day)
  5. System updates record status to RETURNED
  6. System increments available copies
  7. If fine exists, system creates fine record
  8. System displays return confirmation
- Postcondition: Book returned, fine calculated if applicable

**UC5: Manage Books (Admin)**
- Actor: Administrator
- Precondition: User logged in as ADMIN
- Main Flow:
  1. Admin navigates to Admin Dashboard > Books
  2. Admin can:
     - Add new book (fills form with ISBN, title, etc.)
     - Edit existing book (modifies information)
     - Delete book (confirms deletion)
     - Search books
  3. System validates input
  4. System updates database
  5. System displays success message
- Postcondition: Book inventory updated

**UC6: Manage Users (Admin)**
- Actor: Administrator
- Precondition: User logged in as ADMIN
- Main Flow:
  1. Admin navigates to Admin Dashboard > Users
  2. Admin can:
     - View all users
     - Create new user with role assignment
     - Edit user information
     - Change user role/status
     - Delete user
  3. System validates changes
  4. System updates database
  5. System displays confirmation
- Postcondition: User records updated

**UC7: Process Overdue Returns (Instructor/Admin)**
- Actor: Instructor or Administrator
- Precondition: User logged in with elevated privileges
- Main Flow:
  1. Staff navigates to Overdue Books tab
  2. System displays all overdue borrowings
  3. System shows days overdue and calculated fine
  4. Staff clicks "Process Return"
  5. System confirms action
  6. System marks book as returned
  7. System creates fine record
  8. System updates book availability
  9. System displays fine amount
- Postcondition: Overdue book returned, fine recorded

---

## 2.2 System Architecture Design [20 marks]

### 2.2.1 Overall Architecture

The Library Management System follows the **Jakarta EE Multi-Tier Architecture** pattern, implementing a clear separation of concerns across four distinct tiers:

```
┌─────────────────────────────────────────────────────────────┐
│                      CLIENT TIER                             │
│  (Web Browser - HTML5, CSS3, JavaScript)                    │
│  • Single Page Application (SPA) behavior                   │
│  • Responsive Design (Mobile, Tablet, Desktop)              │
│  • REST API Client                                          │
└──────────────────────┬──────────────────────────────────────┘
                       │ HTTPS/HTTP
                       │ JSON (RESTful API)
┌──────────────────────▼──────────────────────────────────────┐
│                      WEB TIER                                │
│  (Apache Tomcat 10 - Jakarta EE Container)                  │
│  • JAX-RS REST Resources (API Endpoints)                    │
│  • CORS Filter (Cross-Origin Resource Sharing)              │
│  • JWT Authentication Filter                                │
│  • Static Content Serving                                   │
└──────────────────────┬──────────────────────────────────────┘
                       │ Java Method Calls
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                   BUSINESS TIER                              │
│  (Application Logic Layer)                                   │
│  • Service Classes (Business Logic)                         │
│    - AuthService (Authentication & Authorization)           │
│    - UserService (User Management)                          │
│    - BookService (Book Management)                          │
│    - BorrowingService (Lending Operations)                  │
│  • Security Components                                       │
│    - PasswordUtil (BCrypt Hashing)                          │
│    - JWTUtil (Token Management)                             │
│  • Utility Classes                                          │
│    - GsonUtil (JSON Processing)                             │
│    - JPAUtil (Entity Manager Factory)                       │
└──────────────────────┬──────────────────────────────────────┘
                       │ JPA/Hibernate
                       │
┌──────────────────────▼──────────────────────────────────────┐
│                 DATA ACCESS TIER                             │
│  (Persistence Layer)                                         │
│  • Generic DAO Pattern                                       │
│  • Entity-Specific DAOs                                      │
│    - UserDAO, BookDAO, AuthorDAO, CategoryDAO               │
│    - BorrowingRecordDAO, ReservationDAO                     │
│    - ReviewDAO, FineDAO                                      │
│  • JPA Entities with Annotations                            │
│  • Transaction Management                                    │
└──────────────────────┬──────────────────────────────────────┘
                       │ JDBC
                       │
┌──────────────────────▼──────────────────────────────────────┐
│             ENTERPRISE INFORMATION SYSTEM TIER               │
│  (MySQL 8.0 Database)                                        │
│  • 9 Main Tables (Normalized Schema)                        │
│  • Referential Integrity Constraints                        │
│  • Indexes for Query Optimization                           │
│  • Triggers for Business Rules                              │
│  • Stored Procedures for Complex Operations                 │
│  • Views for Common Queries                                 │
└─────────────────────────────────────────────────────────────┘
```

### 2.2.2 Client Tier

**Purpose:** User Interface and User Experience

**Components:**
- **HTML5 Pages:** Single-page application structure
  - `index.html` - Main application shell
  - Semantic HTML5 elements

- **CSS3 Styling:** Modern, responsive design
  - `style.css` - Custom styles with CSS3 features
  - CSS Variables for theming
  - Flexbox and Grid layouts
  - Media queries for responsiveness
  - Font Awesome icons

- **JavaScript (ES6+):** Client-side logic
  - `app.js` - Application controller
  - RESTful API client implementation
  - DOM manipulation
  - Event handling
  - Form validation
  - Local storage for token persistence

**Key Features:**
- Responsive design (mobile-first approach)
- Real-time search with debouncing
- Modal dialogs for forms
- Toast notifications for feedback
- Dynamic content loading
- SPA navigation without page reload

**Communication:**
- HTTPS/HTTP protocol
- RESTful API calls
- JSON data format
- JWT token in Authorization header
- CORS-enabled requests

### 2.2.3 Web Tier

**Purpose:** Request Handling and Routing

**Components:**

1. **JAX-RS REST Resources** (API Controllers)
   - `AuthResource` - Authentication endpoints
     - POST /api/auth/login
     - POST /api/auth/register
     - GET /api/auth/me
     - POST /api/auth/change-password
   
   - `UserResource` - User management endpoints
     - GET /api/users
     - GET /api/users/{id}
     - POST /api/users
     - PUT /api/users/{id}
     - DELETE /api/users/{id}
     - GET /api/users/search
   
   - `BookResource` - Book management endpoints
     - GET /api/books
     - GET /api/books/{id}
     - POST /api/books
     - PUT /api/books/{id}
     - DELETE /api/books/{id}
     - GET /api/books/search
     - GET /api/books/available
   
   - `BorrowingResource` - Borrowing operations
     - POST /api/borrowing/borrow
     - POST /api/borrowing/return/{id}
     - GET /api/borrowing/user/{userId}
     - GET /api/borrowing/user/{userId}/active
     - GET /api/borrowing/overdue
     - GET /api/borrowing/all
     - GET /api/borrowing/active

2. **Filters and Interceptors**
   - `CorsFilter` - CORS configuration
     - Allows cross-origin requests
     - Sets appropriate headers
     - Handles preflight OPTIONS requests
   
   - JWT Authentication (custom filter - future enhancement)
     - Token validation
     - User authentication
     - Role-based authorization

3. **Configuration**
   - `ApplicationConfig` - JAX-RS configuration
   - `web.xml` - Servlet configuration
   - `persistence.xml` - JPA configuration

**Responsibilities:**
- Route incoming HTTP requests
- Serialize/deserialize JSON
- Handle CORS
- Serve static content
- Error handling and HTTP status codes
- Response formatting

### 2.2.4 Business Tier

**Purpose:** Business Logic and Rules Implementation

**Service Layer:**

1. **AuthService**
   - User registration with validation
   - User authentication (login)
   - Password verification using BCrypt
   - JWT token generation
   - Token validation
   - Password change functionality

2. **UserService**
   - User CRUD operations
   - User search functionality
   - Role management
   - Status management
   - User statistics calculation

3. **BookService**
   - Book CRUD operations
   - Book search and filtering
   - Availability management
   - Category management
   - Author management

4. **BorrowingService**
   - Borrow book validation and processing
   - Return book processing
   - Overdue detection
   - Fine calculation ($1.00/day)
   - Borrowing limit enforcement
   - Borrowing history retrieval

**Security Layer:**

1. **PasswordUtil**
   - BCrypt password hashing (10 rounds)
   - Password verification
   - Secure password handling

2. **JWTUtil**
   - Token generation with claims
   - Token validation
   - Token parsing
   - Expiration checking (24 hours)
   - Secret key management

**Utility Layer:**

1. **GsonUtil**
   - JSON serialization/deserialization
   - LocalDateTime adapter
   - LocalDate adapter
   - Custom type adapters

2. **JPAUtil**
   - EntityManagerFactory management
   - EntityManager creation
   - Connection lifecycle management

**Business Rules Enforced:**
- Maximum 5 active borrowings per user
- 14-day default borrowing period
- $1.00 per day overdue fine
- Only ACTIVE users can borrow
- Available copies must be > 0 to borrow
- Password minimum 6 characters
- Unique username and email
- Unique ISBN for books

### 2.2.5 Data Access Tier

**Purpose:** Database Operations Abstraction

**DAO Pattern Implementation:**

1. **GenericDAO<T, ID>** (Abstract Base Class)
   - save(T entity)
   - update(T entity)
   - delete(ID id)
   - findById(ID id)
   - findAll()
   - findAll(offset, limit) - pagination
   - count()
   - executeUpdate(jpql, params)

2. **Entity-Specific DAOs** (Extend GenericDAO)
   
   - **UserDAO**
     - findByUsername(username)
     - findByEmail(email)
     - findByRole(role)
     - findByStatus(status)
     - searchUsers(searchTerm)
     - usernameExists(username)
     - emailExists(email)
     - countByRole(role)
   
   - **BookDAO**
     - findByIsbn(isbn)
     - findByCategory(categoryId)
     - findByStatus(status)
     - findAvailableBooks()
     - searchBooks(searchTerm)
     - findByAuthor(authorId)
     - getMostBorrowedBooks(limit)
     - getRecentlyAddedBooks(limit)
     - countAvailableBooks()
   
   - **BorrowingRecordDAO**
     - findByUser(userId)
     - findByBook(bookId)
     - findActiveBorrowingsByUser(userId)
     - findOverdueBorrowings()
     - findByStatus(status)
     - getBorrowingStatistics()
     - countActiveBorrowingsByUser(userId)
     - getRecentBorrowings(limit)
   
   - **CategoryDAO**
     - findByName(categoryName)
     - searchCategories(searchTerm)
     - getCategoriesWithBookCount()
   
   - **AuthorDAO**
     - findByName(authorName)
     - findByNationality(nationality)
     - searchAuthors(searchTerm)
     - getAuthorsWithBookCount()
     - getPopularAuthors(limit)
   
   - **ReservationDAO**
     - findByUser(userId)
     - findActiveReservationsByUser(userId)
     - findByBook(bookId)
     - findActiveReservationsByBook(bookId)
     - findByStatus(status)
     - findExpiredReservations()
   
   - **ReviewDAO**
     - findByBook(bookId)
     - findByUser(userId)
     - findByUserAndBook(userId, bookId)
     - getAverageRatingForBook(bookId)
     - countReviewsForBook(bookId)
     - getRecentReviews(limit)
   
   - **FineDAO**
     - findByUser(userId)
     - findPendingFinesByUser(userId)
     - findByStatus(status)
     - getTotalPendingFinesByUser(userId)
     - getAllPendingFines()
     - getFineStatistics()

**JPA Entity Mapping:**
- Annotations-based configuration
- Bidirectional relationships
- Lazy loading strategies
- Cascade operations
- Lifecycle callbacks (@PrePersist, @PreUpdate)

**Transaction Management:**
- Container-managed transactions
- EntityTransaction for explicit control
- Rollback on exceptions
- ACID compliance

### 2.2.6 Enterprise Information System Tier

**Purpose:** Data Persistence and Storage

**Database:** MySQL 8.0

**Schema Components:**

1. **Tables (9 main tables)**
   - users
   - books
   - categories
   - authors
   - book_authors (junction table)
   - borrowing_records
   - reservations
   - reviews
   - fines

2. **Constraints**
   - Primary Keys (AUTO_INCREMENT)
   - Foreign Keys (referential integrity)
   - Unique Constraints (username, email, ISBN)
   - Check Constraints (rating 1-5, available_copies >= 0)
   - NOT NULL constraints

3. **Indexes**
   - Primary key indexes
   - Foreign key indexes
   - Unique indexes (username, email, ISBN)
   - Search optimization indexes (title, author_name)
   - Date indexes (borrow_date, due_date)

4. **Database Objects**
   - **Views:**
     - available_books_view
     - current_borrowings_view
     - user_borrowing_history_view
   
   - **Stored Procedures:**
     - borrow_book(user_id, book_id, issued_by, days)
     - return_book(record_id, returned_to)
   
   - **Triggers:**
     - update_overdue_status (before update on borrowing_records)
     - check_available_copies (before update on books)

5. **Data Integrity**
   - Referential integrity through foreign keys
   - Cascade delete for dependent records
   - Set NULL for optional relationships
   - Check constraints for data validation

**Connection Management:**
- JDBC connection pooling (10 connections)
- Connection timeout handling
- Automatic reconnection
- Transaction isolation level: READ_COMMITTED

### 2.2.7 Communication Flow

**Example: User Borrows a Book**

```
1. Client Tier (Browser)
   ├─> User clicks "Borrow This Book" button
   ├─> JavaScript captures event
   ├─> Constructs JSON payload: {userId: 1, bookId: 5, days: 14}
   └─> Sends POST request to /api/borrowing/borrow
       └─> Includes JWT token in Authorization header

2. Web Tier (Tomcat)
   ├─> CorsFilter processes request
   ├─> JAX-RS matches route to BorrowingResource.borrowBook()
   ├─> Deserializes JSON to Java Map
   └─> Calls borrowingService.borrowBook(userId, bookId, issuedById, days)

3. Business Tier (Service Layer)
   ├─> BorrowingService.borrowBook() validates request
   ├─> Checks user exists (calls userDAO.findById())
   ├─> Checks book availability (calls bookDAO.findById())
   ├─> Verifies borrowing limit (calls borrowingRecordDAO.countActiveBorrowingsByUser())
   ├─> Creates BorrowingRecord entity
   ├─> Calculates due date (current + 14 days)
   └─> Calls borrowingRecordDAO.save(record)

4. Data Access Tier (DAO Layer)
   ├─> BorrowingRecordDAO.save() method
   ├─> Creates EntityManager
   ├─> Begins transaction
   ├─> Persists entity
   ├─> Commits transaction
   ├─> Updates book.availableCopies - 1
   ├─> Calls bookDAO.update(book)
   └─> Returns saved entity

5. EIS Tier (Database)
   ├─> INSERT INTO borrowing_records (...)
   ├─> UPDATE books SET available_copies = available_copies - 1
   ├─> Trigger checks constraints
   ├─> Commit transaction
   └─> Returns success

6. Response Flow (Bottom-Up)
   ├─> DAO returns entity to Service
   ├─> Service constructs response Map
   ├─> Service returns to Web Tier
   ├─> JAX-RS serializes to JSON
   ├─> Sets HTTP 200 OK status
   └─> Returns JSON to client

7. Client Tier (Browser)
   ├─> Receives JSON response
   ├─> JavaScript parses response
   ├─> Shows success toast notification
   ├─> Updates UI (decrements available copies)
   └─> Refreshes "My Borrowings" list
```

### 2.2.8 Architectural Patterns Used

1. **Model-View-Controller (MVC)**
   - Model: JPA Entities
   - View: HTML/CSS/JavaScript
   - Controller: JAX-RS Resources

2. **Data Access Object (DAO)**
   - Abstracts database operations
   - Generic base class with common operations
   - Entity-specific extensions

3. **Service Layer Pattern**
   - Business logic encapsulation
   - Transaction boundaries
   - Orchestrates multiple DAOs

4. **Dependency Injection (DI)**
   - Constructor injection
   - Loose coupling
   - Testability

5. **Repository Pattern**
   - DAO acts as repository
   - Collection-like interface
   - Hides persistence details

6. **Filter Pattern**
   - CORS filter
   - Authentication filter (future)
   - Chain of responsibility

7. **Builder Pattern**
   - Gson configuration
   - JWT token building

8. **Singleton Pattern**
   - EntityManagerFactory
   - Gson instance
   - Utility classes

### 2.2.9 Scalability Considerations

**Horizontal Scaling:**
- Stateless REST API (no session state)
- JWT tokens (no server-side sessions)
- Multiple Tomcat instances with load balancer

**Vertical Scaling:**
- Connection pool sizing
- JVM heap configuration
- Database query optimization

**Caching Strategy (Future):**
- Redis for session data
- Application-level caching
- Database query cache

**Database Scaling:**
- Read replicas for queries
- Master-slave replication
- Partitioning for large tables

---

## 2.3 Component Design [25 marks]

### 2.3.1 Component Overview

The system is organized into distinct components following the layered architecture pattern. Each component has well-defined responsibilities and interfaces.

### 2.3.2 Entity Layer Components

**JPA Entity Classes** represent the domain model and map to database tables.

#### **User Entity**
```java
@Entity
@Table(name = "users")
public class User {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long userId;
    
    private String username;
    private String email;
    private String passwordHash;
    private String firstName;
    private String lastName;
    private String phone;
    private String address;
    
    @Enumerated(EnumType.STRING)
    private UserRole role; // ADMIN, INSTRUCTOR, USER
    
    @Enumerated(EnumType.STRING)
    private UserStatus status; // ACTIVE, INACTIVE, SUSPENDED
    
    private LocalDateTime registrationDate;
    private LocalDateTime lastLogin;
    
    @OneToMany(mappedBy = "user")
    private List<BorrowingRecord> borrowingRecords;
    
    @OneToMany(mappedBy = "user")
    private List<Reservation> reservations;
    
    @OneToMany(mappedBy = "user")
    private List<Review> reviews;
}
```

**Relationships:**
- One User → Many BorrowingRecords
- One User → Many Reservations
- One User → Many Reviews

#### **Book Entity**
```java
@Entity
@Table(name = "books")
public class Book {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long bookId;
    
    private String isbn; // Unique
    private String title;
    private String subtitle;
    private String publisher;
    private LocalDate publicationDate;
    private String edition;
    private String language;
    private Integer pages;
    private String description;
    private String coverImageUrl;
    private Integer totalCopies;
    private Integer availableCopies;
    
    @ManyToOne
    @JoinColumn(name = "category_id")
    private Category category;
    
    @Enumerated(EnumType.STRING)
    private BookStatus status; // AVAILABLE, UNAVAILABLE, DAMAGED, LOST
    
    @ManyToMany
    @JoinTable(name = "book_authors",
               joinColumns = @JoinColumn(name = "book_id"),
               inverseJoinColumns = @JoinColumn(name = "author_id"))
    private List<Author> authors;
    
    @OneToMany(mappedBy = "book")
    private List<BorrowingRecord> borrowingRecords;
    
    @OneToMany(mappedBy = "book")
    private List<Reservation> reservations;
    
    @OneToMany(mappedBy = "book")
    private List<Review> reviews;
}
```

**Relationships:**
- Many Books → One Category
- Many Books → Many Authors (junction table: book_authors)
- One Book → Many BorrowingRecords
- One Book → Many Reservations
- One Book → Many Reviews

#### **BorrowingRecord Entity**
```java
@Entity
@Table(name = "borrowing_records")
public class BorrowingRecord {
    @Id @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long recordId;
    
    @ManyToOne
    @JoinColumn(name = "user_id")
    private User user;
    
    @ManyToOne
    @JoinColumn(name = "book_id")
    private Book book;
    
    private LocalDateTime borrowDate;
    private LocalDateTime dueDate;
    private LocalDateTime returnDate;
    
    @Enumerated(EnumType.STRING)
    private BorrowStatus status; // BORROWED, RETURNED, OVERDUE, LOST
    
    private BigDecimal fineAmount;
    private String notes;
    
    @ManyToOne
    @JoinColumn(name = "issued_by")
    private User issuedBy;
    
    @ManyToOne
    @JoinColumn(name = "returned_to")
    private User returnedTo;
}
```

**Relationships:**
- Many BorrowingRecords → One User (borrower)
- Many BorrowingRecords → One Book
- Many BorrowingRecords → One User (issued by)
- Many BorrowingRecords → One User (returned to)

#### **Other Entities:**

**Category Entity**
- categoryId (PK)
- categoryName (Unique)
- description
- One Category → Many Books

**Author Entity**
- authorId (PK)
- authorName
- biography
- birthDate
- nationality
- Many Authors → Many Books

**Reservation Entity**
- reservationId (PK)
- user (FK)
- book (FK)
- reservationDate
- expiryDate
- status (ACTIVE, FULFILLED, CANCELLED, EXPIRED)
- notified (Boolean)

**Review Entity**
- reviewId (PK)
- user (FK)
- book (FK)
- rating (1-5)
- reviewText
- reviewDate
- Unique constraint: (user_id, book_id)

**Fine Entity**
- fineId (PK)
- borrowingRecord (FK)
- user (FK)
- fineAmount
- fineReason
- fineDate
- paymentDate
- status (PENDING, PAID, WAIVED)
- paymentMethod

### 2.3.3 Class Diagram

```
┌─────────────────────────────────────────────────────────────┐
│                      <<Entity>>                              │
│                         User                                 │
├─────────────────────────────────────────────────────────────┤
│ - userId: Long (PK)                                         │
│ - username: String (Unique)                                 │
│ - email: String (Unique)                                    │
│ - passwordHash: String                                      │
│ - firstName: String                                         │
│ - lastName: String                                          │
│ - phone: String                                             │
│ - address: String                                           │
│ - role: UserRole (ADMIN, INSTRUCTOR, USER)                  │
│ - status: UserStatus (ACTIVE, INACTIVE, SUSPENDED)          │
│ - registrationDate: LocalDateTime                           │
│ - lastLogin: LocalDateTime                                  │
├─────────────────────────────────────────────────────────────┤
│ + getFullName(): String                                     │
│ + isActive(): boolean                                       │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ 1
                            │
                            │ *
┌─────────────────────────▼───────────────────────────────────┐
│                  <<Entity>>                                  │
│                BorrowingRecord                               │
├─────────────────────────────────────────────────────────────┤
│ - recordId: Long (PK)                                       │
│ - user: User (FK)                                           │
│ - book: Book (FK)                                           │
│ - borrowDate: LocalDateTime                                 │
│ - dueDate: LocalDateTime                                    │
│ - returnDate: LocalDateTime                                 │
│ - status: BorrowStatus                                      │
│ - fineAmount: BigDecimal                                    │
│ - notes: String                                             │
│ - issuedBy: User (FK)                                       │
│ - returnedTo: User (FK)                                     │
├─────────────────────────────────────────────────────────────┤
│ + isOverdue(): boolean                                      │
│ + getDaysOverdue(): long                                    │
│ + calculateFine(): BigDecimal                               │
└─────────────────────────────────────────────────────────────┘
                            │
                            │ *
                            │
                            │ 1
┌─────────────────────────▼───────────────────────────────────┐
│                     <<Entity>>                               │
│                        Book                                  │
├─────────────────────────────────────────────────────────────┤
│ - bookId: Long (PK)                                         │
│ - isbn: String (Unique)                                     │
│ - title: String                                             │
│ - subtitle: String                                          │
│ - publisher: String                                         │
│ - publicationDate: LocalDate                                │
│ - edition: String                                           │
│ - language: String                                          │
│ - pages: Integer                                            │
│ - description: String                                       │
│ - coverImageUrl: String                                     │
│ - totalCopies: Integer                                      │
│ - availableCopies: Integer                                  │
│ - category: Category (FK)                                   │
│ - status: BookStatus                                        │
├─────────────────────────────────────────────────────────────┤
│ + isAvailable(): boolean                                    │
│ + addAuthor(author: Author): void                           │
│ + removeAuthor(author: Author): void                        │
└─────────────────────────────────────────────────────────────┘
         │                                │
         │ *                              │ *
         │                                │
         │ *                              │ 1
┌────────▼──────────┐          ┌─────────▼──────────┐
│   <<Entity>>      │          │    <<Entity>>      │
│     Author        │          │     Category       │
├───────────────────┤          ├────────────────────┤
│- authorId: Long   │          │- categoryId: Long  │
│- authorName: Str  │          │- categoryName: Str │
│- biography: Text  │          │- description: Text │
│- birthDate: Date  │          └────────────────────┘
│- nationality: Str │
└───────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                    <<DAO>>                                   │
│                  GenericDAO<T,ID>                            │
├─────────────────────────────────────────────────────────────┤
│ # entityClass: Class<T>                                     │
├─────────────────────────────────────────────────────────────┤
│ + save(entity: T): T                                        │
│ + update(entity: T): T                                      │
│ + delete(id: ID): void                                      │
│ + findById(id: ID): Optional<T>                             │
│ + findAll(): List<T>                                        │
│ + findAll(offset: int, limit: int): List<T>                │
│ + count(): long                                             │
│ # executeUpdate(jpql: String, params: Object...): int       │
└─────────────────────────────────────────────────────────────┘
                            △
                            │ extends
                 ┌──────────┼──────────┐
                 │          │          │
         ┌───────▼───┐ ┌───▼────┐ ┌───▼──────┐
         │  UserDAO  │ │BookDAO │ │ BorrowingRecordDAO│
         ├───────────┤ ├────────┤ ├────────────┤
         │+ findBy...│ │+ findBy│ │+ findOverdue()│
         │+ search() │ │+ search│ │+ findActive()│
         └───────────┘ └────────┘ └──────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   <<Service>>                                │
│                  AuthService                                 │
├─────────────────────────────────────────────────────────────┤
│ - userDAO: UserDAO                                          │
├─────────────────────────────────────────────────────────────┤
│ + register(username, email, password, ...): Map             │
│ + login(username, password): Map                            │
│ + validateToken(token: String): Map                         │
│ + changePassword(userId, oldPass, newPass): Map             │
│ - getUserInfo(user: User): Map                              │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   <<Service>>                                │
│                 BorrowingService                             │
├─────────────────────────────────────────────────────────────┤
│ - borrowingRecordDAO: BorrowingRecordDAO                    │
│ - bookDAO: BookDAO                                          │
│ - userDAO: UserDAO                                          │
│ - DEFAULT_BORROW_DAYS: int = 14                             │
│ - FINE_PER_DAY: BigDecimal = 1.00                           │
├─────────────────────────────────────────────────────────────┤
│ + borrowBook(userId, bookId, issuedById, days): Map         │
│ + returnBook(recordId, returnedToId): Map                   │
│ + getUserBorrowingHistory(userId): List<Map>                │
│ + getUserActiveBorrowings(userId): List<Map>                │
│ + getOverdueBorrowings(): List<Map>                         │
│ + getAllBorrowings(): List<Map>                             │
│ + getActiveBorrowings(): List<Map>                          │
│ - getBorrowingRecordInfo(record): Map                       │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                  <<Resource>>                                │
│                 AuthResource                                 │
├─────────────────────────────────────────────────────────────┤
│ - authService: AuthService                                  │
│ - gson: Gson                                                │
├─────────────────────────────────────────────────────────────┤
│ + register(jsonPayload: String): Response                   │
│ + login(jsonPayload: String): Response                      │
│ + getCurrentUser(authHeader: String): Response              │
│ + changePassword(authHeader, jsonPayload): Response         │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   <<Utility>>                                │
│                  PasswordUtil                                │
├─────────────────────────────────────────────────────────────┤
│ - BCRYPT_ROUNDS: int = 10                                   │
├─────────────────────────────────────────────────────────────┤
│ + hashPassword(plainPassword: String): String               │
│ + verifyPassword(plainPassword, hashedPassword): boolean    │
└─────────────────────────────────────────────────────────────┘

┌─────────────────────────────────────────────────────────────┐
│                   <<Utility>>                                │
│                    JWTUtil                                   │
├─────────────────────────────────────────────────────────────┤
│ - SECRET_KEY: Key                                           │
│ - EXPIRATION_TIME: long = 24 * 60 * 60 * 1000              │
├─────────────────────────────────────────────────────────────┤
│ + generateToken(user: User): String                         │
│ + validateToken(token: String): boolean                     │
│ + getUsernameFromToken(token: String): String               │
│ + getUserIdFromToken(token: String): Long                   │
│ + getRoleFromToken(token: String): String                   │
│ + getClaimsFromToken(token: String): Claims                 │
│ + isTokenExpired(token: String): boolean                    │
└─────────────────────────────────────────────────────────────┘
```

### 2.3.4 Component Interactions

**Component Dependency Diagram:**

```
┌──────────────────┐
│  Client (Browser)│
│  HTML/CSS/JS     │
└────────┬─────────┘
         │ HTTP/JSON
         │
┌────────▼─────────────────────────────────────────┐
│         JAX-RS Resources (Web Tier)              │
│  ┌──────────┐ ┌──────────┐ ┌────────────────┐  │
│  │  Auth    │ │  User    │ │  Borrowing     │  │
│  │ Resource │ │ Resource │ │   Resource     │  │
│  └──────────┘ └──────────┘ └────────────────┘  │
└────────┬─────────────────────────────────────────┘
         │ Method Calls
         │
┌────────▼─────────────────────────────────────────┐
│        Service Layer (Business Tier)             │
│  ┌──────────┐ ┌──────────┐ ┌────────────────┐  │
│  │  Auth    │ │  User    │ │  Borrowing     │  │
│  │ Service  │ │ Service  │ │   Service      │  │
│  └──────────┘ └──────────┘ └────────────────┘  │
│         │              │              │          │
│         └──────┬───────┴──────┬───────┘          │
│                │              │                  │
│  ┌─────────────▼──────────────▼───────────────┐ │
│  │    Security & Utility Components           │ │
│  │  ┌────────────┐  ┌──────────┐             │ │
│  │  │ PasswordUtil│  │ JWTUtil  │             │ │
│  │  │  (BCrypt)  │  │  (Token) │             │ │
│  │  └────────────┘  └──────────┘             │ │
│  └───────────────────────────────────────────┘ │
└────────┬─────────────────────────────────────────┘
         │ DAO Calls
         │
┌────────▼─────────────────────────────────────────┐
│         DAO Layer (Data Access Tier)             │
│  ┌──────────┐ ┌──────────┐ ┌────────────────┐  │
│  │ UserDAO  │ │ BookDAO  │ │BorrowingRecord │  │
│  │          │ │          │ │     DAO        │  │
│  └──────────┘ └──────────┘ └────────────────┘  │
│         │              │              │          │
│         └──────┬───────┴──────┬───────┘          │
│                │              │                  │
│  ┌─────────────▼──────────────▼───────────────┐ │
│  │         GenericDAO<T, ID>                  │ │
│  │         (Base Class)                       │ │
│  └───────────────────────────────────────────┘ │
│                      │                          │
│  ┌───────────────────▼──────────────────────┐  │
│  │          JPAUtil                         │  │
│  │    (EntityManagerFactory)                │  │
│  └──────────────────────────────────────────┘  │
└────────┬─────────────────────────────────────────┘
         │ JDBC
         │
┌────────▼─────────────────────────────────────────┐
│            MySQL Database (EIS Tier)             │
│  ┌──────────────────────────────────────────┐   │
│  │  Tables: users, books, borrowing_records │   │
│  │  authors, categories, reservations, etc. │   │
│  └──────────────────────────────────────────┘   │
└──────────────────────────────────────────────────┘
```

### 2.3.5 Key Design Patterns in Components

1. **Generic DAO Pattern**
   - Eliminates code duplication
   - Type-safe operations
   - Consistent interface

2. **Service Facade Pattern**
   - Simplifies complex operations
   - Orchestrates multiple DAOs
   - Transaction boundaries

3. **Data Transfer Object (DTO)**
   - Maps returned as DTOs
   - Prevents entity exposure
   - Flexible JSON structure

4. **Template Method Pattern**
   - GenericDAO defines template
   - Subclasses implement specifics
   - Code reuse

5. **Strategy Pattern**
   - Different authentication strategies
   - Swappable password algorithms
   - Token validation strategies

---

## 2.4 Database Design [20 marks]

### 2.4.1 Entity-Relationship (ER) Diagram

```
┌─────────────────────────────────────────────────────────────────────────┐
│                     LIBRARY MANAGEMENT SYSTEM                            │
│                        ER DIAGRAM                                        │
└─────────────────────────────────────────────────────────────────────────┘

                         ┌──────────────┐
                         │    USERS     │
                         ├──────────────┤
                         │ PK: user_id  │
                         │    username  │
                         │    email     │
                         │    password  │
                         │    firstName │
                         │    lastName  │
                         │    phone     │
                         │    address   │
                         │    role      │
                         │    status    │
                         └──────┬───────┘
                                │
                ┌───────────────┼───────────────┐
                │               │               │
         borrows│1             │1              │1 writes
                │               │               │
                │*              │*              │*
        ┌───────▼──────┐ ┌──────▼──────┐ ┌────▼──────┐
        │  BORROWING   │ │ RESERVATION │ │  REVIEWS  │
        │   RECORDS    │ │             │ │           │
        ├──────────────┤ ├─────────────┤ ├───────────┤
        │PK:record_id  │ │PK:reserv_id │ │PK:review_id│
        │FK:user_id    │ │FK:user_id   │ │FK:user_id │
        │FK:book_id    │ │FK:book_id   │ │FK:book_id │
        │  borrow_date │ │ reserve_date│ │  rating   │
        │  due_date    │ │ expiry_date │ │  review   │
        │  return_date │ │  status     │ │  date     │
        │  status      │ │  notified   │ └───────────┘
        │  fine_amount │ └─────────────┘
        │  notes       │
        │FK:issued_by  │        │
        │FK:returned_to│        │* belongs to
        └──────┬───────┘        │
               │                │
               │* for           │1
               │                │
        ┌──────▼────────────────▼─────┐
        │         BOOKS                │
        ├──────────────────────────────┤
        │ PK: book_id                  │
        │     isbn (UNIQUE)            │
        │     title                    │
        │     subtitle                 │
        │     publisher                │
        │     publication_date         │
        │     edition                  │
        │     language                 │
        │     pages                    │
        │     description              │
        │     cover_image_url          │
        │     total_copies             │
        │     available_copies         │
        │ FK: category_id              │
        │     status                   │
        └──────┬──────────┬────────────┘
               │          │
               │*         │* written by
               │          │
               │          │* (Many-to-Many)
               │          │
               │          │
        ┌──────▼─────┐  ┌▼──────────────┐
        │ CATEGORIES │  │ BOOK_AUTHORS  │
        ├────────────┤  │ (Junction)    │
        │PK:category │  ├───────────────┤
        │   _id      │  │PK,FK:book_id  │
        │ category   │  │PK,FK:author_id│
        │   _name    │  └───────┬───────┘
        │ description│          │
        └────────────┘          │* authored by
                                │
                                │1
                         ┌──────▼──────┐
                         │   AUTHORS   │
                         ├─────────────┤
                         │PK:author_id │
                         │  author_name│
                         │  biography  │
                         │  birth_date │
                         │  nationality│
                         └─────────────┘

        ┌──────────────┐
        │    FINES     │
        ├──────────────┤
        │PK:fine_id    │
        │FK:record_id  │─────┐
        │FK:user_id    │     │
        │  fine_amount │     │1 has fine
        │  fine_reason │     │
        │  fine_date   │     │1
        │  payment_date│  ┌──▼──────────┐
        │  status      │  │  BORROWING  │
        │  payment_    │  │  RECORDS    │
        │  method      │  └─────────────┘
        └──────────────┘

CARDINALITY LEGEND:
─────────────────
1    : One (Exactly one)
*    : Many (Zero or more)
1..* : One or more
0..1 : Zero or one

RELATIONSHIP TYPES:
───────────────────
→ : One-to-Many
↔ : Many-to-Many (requires junction table)

KEY RELATIONSHIPS:
──────────────────
1. User (1) ──borrows──> (Many) BorrowingRecord
2. User (1) ──reserves──> (Many) Reservation  
3. User (1) ──writes──> (Many) Review
4. Book (1) ──has──> (Many) BorrowingRecord
5. Book (1) ──has──> (Many) Reservation
6. Book (1) ──has──> (Many) Review
7. Book (Many) ──belongs to──> (1) Category
8. Book (Many) ↔──written by──↔ (Many) Author (via BOOK_AUTHORS)
9. BorrowingRecord (1) ──generates──> (1) Fine
```

### 2.4.2 Detailed ER Diagram Description

#### **Entities and Attributes:**

**1. USERS**
- **Primary Key:** user_id (BIGINT, AUTO_INCREMENT)
- **Attributes:**
  - username (VARCHAR(50), UNIQUE, NOT NULL)
  - email (VARCHAR(100), UNIQUE, NOT NULL)
  - password_hash (VARCHAR(255), NOT NULL)
  - first_name (VARCHAR(50), NOT NULL)
  - last_name (VARCHAR(50), NOT NULL)
  - phone (VARCHAR(20))
  - address (TEXT)
  - role (ENUM: 'ADMIN', 'INSTRUCTOR', 'USER')
  - status (ENUM: 'ACTIVE', 'INACTIVE', 'SUSPENDED')
  - registration_date (TIMESTAMP)
  - last_login (TIMESTAMP)
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**2. BOOKS**
- **Primary Key:** book_id (BIGINT, AUTO_INCREMENT)
- **Foreign Key:** category_id → categories(category_id)
- **Attributes:**
  - isbn (VARCHAR(20), UNIQUE, NOT NULL)
  - title (VARCHAR(255), NOT NULL)
  - subtitle (VARCHAR(255))
  - publisher (VARCHAR(100))
  - publication_date (DATE)
  - edition (VARCHAR(50))
  - language (VARCHAR(50), DEFAULT 'English')
  - pages (INT)
  - description (TEXT)
  - cover_image_url (VARCHAR(500))
  - total_copies (INT, NOT NULL, DEFAULT 1)
  - available_copies (INT, NOT NULL, DEFAULT 1)
  - status (ENUM: 'AVAILABLE', 'UNAVAILABLE', 'DAMAGED', 'LOST')
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**3. BORROWING_RECORDS**
- **Primary Key:** record_id (BIGINT, AUTO_INCREMENT)
- **Foreign Keys:**
  - user_id → users(user_id)
  - book_id → books(book_id)
  - issued_by → users(user_id)
  - returned_to → users(user_id)
- **Attributes:**
  - borrow_date (TIMESTAMP, DEFAULT CURRENT_TIMESTAMP)
  - due_date (TIMESTAMP, NOT NULL)
  - return_date (TIMESTAMP)
  - status (ENUM: 'BORROWED', 'RETURNED', 'OVERDUE', 'LOST')
  - fine_amount (DECIMAL(10,2), DEFAULT 0.00)
  - notes (TEXT)
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**4. CATEGORIES**
- **Primary Key:** category_id (BIGINT, AUTO_INCREMENT)
- **Attributes:**
  - category_name (VARCHAR(100), UNIQUE, NOT NULL)
  - description (TEXT)
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**5. AUTHORS**
- **Primary Key:** author_id (BIGINT, AUTO_INCREMENT)
- **Attributes:**
  - author_name (VARCHAR(100), NOT NULL)
  - biography (TEXT)
  - birth_date (DATE)
  - nationality (VARCHAR(50))
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**6. BOOK_AUTHORS (Junction Table)**
- **Composite Primary Key:** (book_id, author_id)
- **Foreign Keys:**
  - book_id → books(book_id) ON DELETE CASCADE
  - author_id → authors(author_id) ON DELETE CASCADE

**7. RESERVATIONS**
- **Primary Key:** reservation_id (BIGINT, AUTO_INCREMENT)
- **Foreign Keys:**
  - user_id → users(user_id)
  - book_id → books(book_id)
- **Attributes:**
  - reservation_date (TIMESTAMP)
  - expiry_date (TIMESTAMP, NOT NULL)
  - status (ENUM: 'ACTIVE', 'FULFILLED', 'CANCELLED', 'EXPIRED')
  - notified (BOOLEAN, DEFAULT FALSE)
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**8. REVIEWS**
- **Primary Key:** review_id (BIGINT, AUTO_INCREMENT)
- **Foreign Keys:**
  - user_id → users(user_id)
  - book_id → books(book_id)
- **Unique Constraint:** (user_id, book_id)
- **Attributes:**
  - rating (INT, CHECK: rating BETWEEN 1 AND 5)
  - review_text (TEXT)
  - review_date (TIMESTAMP)
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

**9. FINES**
- **Primary Key:** fine_id (BIGINT, AUTO_INCREMENT)
- **Foreign Keys:**
  - record_id → borrowing_records(record_id)
  - user_id → users(user_id)
- **Attributes:**
  - fine_amount (DECIMAL(10,2), NOT NULL)
  - fine_reason (VARCHAR(255))
  - fine_date (TIMESTAMP)
  - payment_date (TIMESTAMP)
  - status (ENUM: 'PENDING', 'PAID', 'WAIVED')
  - payment_method (VARCHAR(50))
  - created_at (TIMESTAMP)
  - updated_at (TIMESTAMP)

### 2.4.3 Relationship Cardinalities

| Relationship | Entity 1 | Cardinality | Entity 2 | Type |
|--------------|----------|-------------|----------|------|
| R1 | User | 1..* | BorrowingRecord | One-to-Many |
| R2 | Book | 1..* | BorrowingRecord | One-to-Many |
| R3 | User | 1..* | Reservation | One-to-Many |
| R4 | Book | 1..* | Reservation | One-to-Many |
| R5 | User | 1..* | Review | One-to-Many |
| R6 | Book | 1..* | Review | One-to-Many |
| R7 | Category | 1..* | Book | One-to-Many |
| R8 | Book | *..* | Author | Many-to-Many |
| R9 | BorrowingRecord | 1..1 | Fine | One-to-One |
| R10 | User | 1..* | Fine | One-to-Many |

### 2.4.4 Database Schema Design

#### **Schema Translation from ER Diagram**

**Normalization Level:** Third Normal Form (3NF)

**Normalization Analysis:**

1. **First Normal Form (1NF):** ✓
   - All attributes contain atomic values
   - No repeating groups
   - Each table has a primary key

2. **Second Normal Form (2NF):** ✓
   - All non-key attributes are fully dependent on primary key
   - No partial dependencies

3. **Third Normal Form (3NF):** ✓
   - No transitive dependencies
   - All attributes depend only on primary key

#### **Table Definitions:**

```sql
-- ============================================
-- TABLE 1: users
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
-- TABLE 2: categories
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
-- TABLE 3: authors
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
-- TABLE 4: books
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
    status ENUM('AVAILABLE', 'UNAVAILABLE', 'DAMAGED', 'LOST') 
           NOT NULL DEFAULT 'AVAILABLE',
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (category_id) REFERENCES categories(category_id) 
        ON DELETE SET NULL,
    
    INDEX idx_isbn (isbn),
    INDEX idx_title (title),
    INDEX idx_category (category_id),
    INDEX idx_status (status),
    
    CONSTRAINT chk_copies 
        CHECK (available_copies >= 0 AND available_copies <= total_copies)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE 5: book_authors (Junction Table)
-- ============================================
CREATE TABLE book_authors (
    book_id BIGINT NOT NULL,
    author_id BIGINT NOT NULL,
    
    PRIMARY KEY (book_id, author_id),
    
    FOREIGN KEY (book_id) REFERENCES books(book_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (author_id) REFERENCES authors(author_id) 
        ON DELETE CASCADE,
    
    INDEX idx_book (book_id),
    INDEX idx_author (author_id)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE 6: borrowing_records
-- ============================================
CREATE TABLE borrowing_records (
    record_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    borrow_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    due_date TIMESTAMP NOT NULL,
    return_date TIMESTAMP NULL,
    status ENUM('BORROWED', 'RETURNED', 'OVERDUE', 'LOST') 
           NOT NULL DEFAULT 'BORROWED',
    fine_amount DECIMAL(10, 2) DEFAULT 0.00,
    notes TEXT,
    issued_by BIGINT,
    returned_to BIGINT,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (issued_by) REFERENCES users(user_id) 
        ON DELETE SET NULL,
    FOREIGN KEY (returned_to) REFERENCES users(user_id) 
        ON DELETE SET NULL,
    
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_status (status),
    INDEX idx_borrow_date (borrow_date),
    INDEX idx_due_date (due_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE 7: reservations
-- ============================================
CREATE TABLE reservations (
    reservation_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    user_id BIGINT NOT NULL,
    book_id BIGINT NOT NULL,
    reservation_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    expiry_date TIMESTAMP NOT NULL,
    status ENUM('ACTIVE', 'FULFILLED', 'CANCELLED', 'EXPIRED') 
           NOT NULL DEFAULT 'ACTIVE',
    notified BOOLEAN DEFAULT FALSE,
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) 
        ON DELETE CASCADE,
    
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_status (status),
    INDEX idx_reservation_date (reservation_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE 8: reviews
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
    
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (book_id) REFERENCES books(book_id) 
        ON DELETE CASCADE,
    
    UNIQUE KEY unique_user_book_review (user_id, book_id),
    
    INDEX idx_user (user_id),
    INDEX idx_book (book_id),
    INDEX idx_rating (rating)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;

-- ============================================
-- TABLE 9: fines
-- ============================================
CREATE TABLE fines (
    fine_id BIGINT AUTO_INCREMENT PRIMARY KEY,
    record_id BIGINT NOT NULL,
    user_id BIGINT NOT NULL,
    fine_amount DECIMAL(10, 2) NOT NULL,
    fine_reason VARCHAR(255),
    fine_date TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    payment_date TIMESTAMP NULL,
    status ENUM('PENDING', 'PAID', 'WAIVED') 
           NOT NULL DEFAULT 'PENDING',
    payment_method VARCHAR(50),
    created_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP,
    updated_at TIMESTAMP DEFAULT CURRENT_TIMESTAMP ON UPDATE CURRENT_TIMESTAMP,
    
    FOREIGN KEY (record_id) REFERENCES borrowing_records(record_id) 
        ON DELETE CASCADE,
    FOREIGN KEY (user_id) REFERENCES users(user_id) 
        ON DELETE CASCADE,
    
    INDEX idx_user (user_id),
    INDEX idx_status (status),
    INDEX idx_fine_date (fine_date)
) ENGINE=InnoDB DEFAULT CHARSET=utf8mb4 COLLATE=utf8mb4_unicode_ci;
```

### 2.4.5 Database Views

**Purpose:** Simplify complex queries and improve performance

```sql
-- View 1: Available Books with Details
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

-- View 2: Current Borrowings
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

-- View 3: User Borrowing History
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
```

### 2.4.6 Stored Procedures

**Business Logic Implementation at Database Level**

```sql
-- Procedure 1: Borrow Book
DELIMITER //
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
        VALUES (p_user_id, p_book_id, 
                DATE_ADD(NOW(), INTERVAL p_days DAY), p_issued_by);
        
        -- Update available copies
        UPDATE books
        SET available_copies = available_copies - 1
        WHERE book_id = p_book_id;
        
        SELECT 'Book borrowed successfully' AS message;
    ELSE
        SELECT 'Book not available' AS message;
    END IF;
END //
DELIMITER ;

-- Procedure 2: Return Book
DELIMITER //
CREATE PROCEDURE return_book(
    IN p_record_id BIGINT,
    IN p_returned_to BIGINT
)
BEGIN
    DECLARE v_book_id BIGINT;
    DECLARE v_due_date TIMESTAMP;
    DECLARE v_fine DECIMAL(10, 2);
    DECLARE v_days_overdue INT;
    DECLARE v_user_id BIGINT;
    
    -- Get record details
    SELECT book_id, due_date, user_id 
    INTO v_book_id, v_due_date, v_user_id
    FROM borrowing_records
    WHERE record_id = p_record_id 
    AND status IN ('BORROWED', 'OVERDUE');
    
    IF v_book_id IS NOT NULL THEN
        -- Calculate fine if overdue ($1 per day)
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
            VALUES (p_record_id, v_user_id, v_fine, 
                    CONCAT('Overdue by ', v_days_overdue, ' days'));
        END IF;
        
        SELECT 'Book returned successfully' AS message, v_fine AS fine_amount;
    ELSE
        SELECT 'Invalid record or book already returned' AS message;
    END IF;
END //
DELIMITER ;
```

### 2.4.7 Database Triggers

**Automatic Business Rule Enforcement**

```sql
-- Trigger 1: Auto-update overdue status
DELIMITER //
CREATE TRIGGER update_overdue_status
BEFORE UPDATE ON borrowing_records
FOR EACH ROW
BEGIN
    IF NEW.status = 'BORROWED' AND NEW.due_date < NOW() THEN
        SET NEW.status = 'OVERDUE';
    END IF;
END //
DELIMITER ;

-- Trigger 2: Prevent negative available copies
DELIMITER //
CREATE TRIGGER check_available_copies
BEFORE UPDATE ON books
FOR EACH ROW
BEGIN
    IF NEW.available_copies < 0 THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Available copies cannot be negative';
    END IF;
    
    IF NEW.available_copies > NEW.total_copies THEN
        SIGNAL SQLSTATE '45000'
        SET MESSAGE_TEXT = 'Available copies cannot exceed total copies';
    END IF;
END //
DELIMITER ;

-- Trigger 3: Auto-create timestamps
DELIMITER //
CREATE TRIGGER before_user_insert
BEFORE INSERT ON users
FOR EACH ROW
BEGIN
    SET NEW.created_at = CURRENT_TIMESTAMP;
    SET NEW.updated_at = CURRENT_TIMESTAMP;
    SET NEW.registration_date = CURRENT_TIMESTAMP;
END //
DELIMITER ;
```

### 2.4.8 Indexes and Query Optimization

**Index Strategy:**

1. **Primary Key Indexes** (Automatic)
   - All tables have AUTO_INCREMENT primary keys
   - Clustered index for InnoDB

2. **Unique Indexes**
   - users.username
   - users.email
   - books.isbn
   - categories.category_name
   - reviews(user_id, book_id) - composite unique

3. **Foreign Key Indexes** (Automatic in InnoDB)
   - All foreign key columns

4. **Search Optimization Indexes**
   - books.title (for LIKE searches)
   - authors.author_name (for name searches)
   - users.username, users.email (for login)

5. **Date Indexes**
   - borrowing_records.borrow_date
   - borrowing_records.due_date
   - For date range queries

6. **Status Indexes**
   - users.status, users.role
   - books.status
   - borrowing_records.status
   - For filtering queries

**Query Optimization Examples:**

```sql
-- Optimized: Uses index on title
SELECT * FROM books 
WHERE title LIKE 'Harry%' 
AND status = 'AVAILABLE';

-- Optimized: Uses composite index
SELECT * FROM borrowing_records 
WHERE user_id = 123 
AND status IN ('BORROWED', 'OVERDUE')
ORDER BY due_date;

-- Optimized: Uses view with pre-aggregated data
SELECT * FROM available_books_view 
WHERE category_name = 'Fiction';
```

### 2.4.9 Data Integrity Constraints

**1. Entity Integrity**
- Primary keys (NOT NULL, UNIQUE)
- Auto-increment for surrogate keys

**2. Referential Integrity**
- Foreign key constraints
- ON DELETE CASCADE for dependent data
- ON DELETE SET NULL for optional relationships

**3. Domain Integrity**
- ENUM types for fixed value sets
- CHECK constraints (rating 1-5, copies >= 0)
- NOT NULL constraints for required fields
- DEFAULT values

**4. Business Rule Constraints**
- Unique constraints (username, email, ISBN)
- Check constraints (available_copies <= total_copies)
- Trigger-based validation
- Application-level validation

### 2.4.10 Database Backup and Recovery Strategy

**Backup Strategy:**
```sql
-- Daily full backup
mysqldump -u root -p library_management > backup_$(date +%Y%m%d).sql

-- Incremental backup using binary logs
mysqlbinlog --start-datetime="2025-12-07 00:00:00" \
            --stop-datetime="2025-12-07 23:59:59" \
            mysql-bin.000001 > incremental_backup.sql
```

**Recovery Strategy:**
```sql
-- Restore from backup
mysql -u root -p library_management < backup_20251207.sql

-- Point-in-time recovery
mysql -u root -p library_management < backup_20251207.sql
mysql -u root -p library_management < incremental_backup.sql
```

---

## 2.5 Interface Design [15 marks]

### 2.5.1 Sitemap

```
┌─────────────────────────────────────────────────────────────┐
│                  LIBRARY MANAGEMENT SYSTEM                   │
│                         SITEMAP                              │
└─────────────────────────────────────────────────────────────┘

                        ┌──────────────┐
                        │     HOME     │
                        │  (Landing)   │
                        └──────┬───────┘
                               │
        ┌──────────────────────┼──────────────────────┐
        │                      │                      │
┌───────▼──────┐      ┌────────▼────────┐   ┌────────▼────────┐
│    BOOKS     │      │ MY BORROWINGS   │   │ ADMIN DASHBOARD │
│  (Public)    │      │ (Authenticated) │   │(Admin/Instructor)│
└──────┬───────┘      └────────┬────────┘   └────────┬────────┘
       │                       │                      │
       │                       │             ┌────────┴────────┐
       │                       │             │                 │
┌──────▼────────┐     ┌────────▼────────┐  ▼                 ▼
│  BOOK DETAIL  │     │  ACTIVE         │ USERS         BOOKS
│  (Modal)      │     │  BORROWINGS     │ MANAGEMENT    MANAGEMENT
└───────────────┘     └─────────────────┘  │              │
                                            │              │
                      ┌─────────────────────┼──────────────┤
                      │                     │              │
                      ▼                     ▼              ▼
            BORROWINGS              OVERDUE         ADD/EDIT
            MANAGEMENT              BOOKS           BOOK
            
┌──────────────────┐
│  AUTHENTICATION  │
│  (Modal Overlay) │
├──────────────────┤
│  • Login         │
│  • Register      │
│  • Change Pass   │
└──────────────────┘

NAVIGATION HIERARCHY:
─────────────────────
Level 0: Home (Landing Page)
Level 1: Main Sections (Books, My Borrowings, Admin)
Level 2: Sub-sections (Management Tabs, Modals)
Level 3: Actions (Forms, Details)

USER ACCESS BY ROLE:
────────────────────
GUEST:
  └─ Home
  └─ Books (Read-only)
  └─ Book Details
  └─ Login/Register

USER (Authenticated):
  └─ Home
  └─ Books (Can borrow)
  └─ Book Details
  └─ My Borrowings
      └─ Active Borrowings
      └─ Return Books
  └─ Profile Settings

INSTRUCTOR:
  └─ All User access
  └─ Admin Dashboard
      └─ Books Management (Issue books)
      └─ Borrowings Management (View all)
      └─ Overdue Books (Process returns)

ADMIN:
  └─ All Instructor access
  └─ Admin Dashboard
      └─ Users Management
          └─ Add/Edit/Delete Users
      └─ Books Management
          └─ Add/Edit/Delete Books
      └─ Borrowings Management (Full control)
      └─ Overdue Books (Full control)
      └─ Statistics Dashboard
```

### 2.5.2 Wireframes and Mockups

#### **Wireframe 1: Home Page (Landing)**

```
┌─────────────────────────────────────────────────────────────┐
│  [LOGO] Library MS    [Home] [Books] [Login] [Register]     │
└─────────────────────────────────────────────────────────────┘
│                                                              │
│  ┌────────────────────────────────────────────────────────┐ │
│  │                                                         │ │
│  │     HERO SECTION                                        │ │
│  │                                                         │ │
│  │  Welcome to Library Management System                  │ │
│  │  Discover, borrow, and manage your favorite books      │ │
│  │                                                         │ │
│  │  [Explore Books]  [Learn More]                         │ │
│  │                                                         │ │
│  └─────────────────────────────────────────────────────────┘ │
│                                                              │
│  FEATURES                                                    │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐      │
│  │ [icon]  │  │ [icon]  │  │ [icon]  │  │ [icon]  │      │
│  │ Search  │  │ Borrow  │  │ Review  │  │ Track   │      │
│  │ Books   │  │ Books   │  │ & Rate  │  │ Due     │      │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘      │
│                                                              │
│  STATISTICS                                                  │
│  ┌─────────┐  ┌─────────┐  ┌─────────┐  ┌─────────┐      │
│  │  1000+  │  │  500+   │  │  800+   │  │   95%   │      │
│  │  Books  │  │  Users  │  │Borrowed │  │Available│      │
│  └─────────┘  └─────────┘  └─────────┘  └─────────┘      │
│                                                              │
└─────────────────────────────────────────────────────────────┘
│  © 2025 Library Management System                           │
└─────────────────────────────────────────────────────────────┘
```

#### **Wireframe 2: Books Page**

```
┌─────────────────────────────────────────────────────────────┐
│  [LOGO] Library MS    [Home] [Books] [My Borrowings] [User▼]│
└─────────────────────────────────────────────────────────────┘
│                                                              │
│  Browse Books                                                │
│  ┌──────────────────────────────────────────────────────┐   │
│  │  [🔍] Search by title, author, ISBN...               │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ [Cover]  │  │ [Cover]  │  │ [Cover]  │  │ [Cover]  │   │
│  │          │  │          │  │          │  │          │   │
│  │ Book 1   │  │ Book 2   │  │ Book 3   │  │ Book 4   │   │
│  │ Author   │  │ Author   │  │ Author   │  │ Author   │   │
│  │ Category │  │ Category │  │ Category │  │ Category │   │
│  │[Available]│ │[Available]│ │[Borrowed]│ │[Available]│   │
│  │ 5/10     │  │ 3/5      │  │ 0/2      │  │ 8/10     │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ [Cover]  │  │ [Cover]  │  │ [Cover]  │  │ [Cover]  │   │
│  │ Book 5   │  │ Book 6   │  │ Book 7   │  │ Book 8   │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│                                                              │
│  [Load More Books...]                                        │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

#### **Wireframe 3: Book Detail Modal**

```
┌─────────────────────────────────────────────────────────────┐
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Book Details                                    [X]│    │
│  ├─────────────────────────────────────────────────────┤    │
│  │                                                     │    │
│  │  ┌──────────┐  ISBN: 978-1234567890               │    │
│  │  │          │  Title: Harry Potter                 │    │
│  │  │  Book    │  Author: J.K. Rowling               │    │
│  │  │  Cover   │  Category: Fiction                   │    │
│  │  │  Image   │  Publisher: Bloomsbury              │    │
│  │  │          │  Language: English                   │    │
│  │  │          │  Pages: 223                          │    │
│  │  └──────────┘  Available: 8/10 copies             │    │
│  │                                                     │    │
│  │  Description:                                      │    │
│  │  The first novel in the Harry Potter series...    │    │
│  │  ......................................................│    │
│  │                                                     │    │
│  │  Reviews:  ★★★★★ 4.5 (120 reviews)              │    │
│  │                                                     │    │
│  │  [Borrow This Book]      [Add to Wishlist]       │    │
│  │                                                     │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

#### **Wireframe 4: My Borrowings Page**

```
┌─────────────────────────────────────────────────────────────┐
│  [LOGO] Library MS    [Home] [Books] [My Borrowings] [User▼]│
└─────────────────────────────────────────────────────────────┘
│                                                              │
│  My Borrowings                                               │
│                                                              │
│  Active Borrowings (3)                                       │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ ┌─────┐                                              │   │
│  │ │Cover│  Harry Potter and the Philosopher's Stone   │   │
│  │ └─────┘  Borrowed: Dec 1, 2025                       │   │
│  │          Due: Dec 15, 2025                           │   │
│  │          Status: [BORROWED] ●                        │   │
│  │          [Return Book]                               │   │
│  ├──────────────────────────────────────────────────────┤   │
│  │ ┌─────┐                                              │   │
│  │ │Cover│  1984                                        │   │
│  │ └─────┘  Borrowed: Nov 28, 2025                      │   │
│  │          Due: Dec 12, 2025                           │   │
│  │          Status: [OVERDUE] ● (2 days)                │   │
│  │          Fine: $2.00                                 │   │
│  │          [Return Book]                               │   │
│  ├──────────────────────────────────────────────────────┤   │
│  │ ┌─────┐                                              │   │
│  │ │Cover│  The Shining                                 │   │
│  │ └─────┘  Borrowed: Dec 3, 2025                       │   │
│  │          Due: Dec 17, 2025                           │   │
│  │          Status: [BORROWED] ●                        │   │
│  │          [Return Book]                               │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

#### **Wireframe 5: Admin Dashboard**

```
┌─────────────────────────────────────────────────────────────┐
│  [LOGO] Library MS    [Home] [Books] [My Borrowings] [Admin]│
└─────────────────────────────────────────────────────────────┘
│                                                              │
│  Admin Dashboard                                             │
│                                                              │
│  ┌──────────┐  ┌──────────┐  ┌──────────┐  ┌──────────┐   │
│  │ [👥]     │  │ [📚]     │  │ [📖]     │  │ [⚠️]     │   │
│  │   150    │  │   500    │  │   42     │  │    5     │   │
│  │  Users   │  │  Books   │  │ Active   │  │ Overdue  │   │
│  └──────────┘  └──────────┘  └──────────┘  └──────────┘   │
│                                                              │
│  [Users Management] [Books Management] [Borrowings] [Overdue]│
│  ────────────────────────────────────────────────────────   │
│                                                              │
│  Users Management                          [+ Add User]      │
│  ┌──────────────────────────────────────────────────────┐   │
│  │ ID │ Username │ Email     │ Role  │ Status │ Actions │   │
│  ├────┼──────────┼───────────┼───────┼────────┼─────────┤   │
│  │ 1  │ admin    │admin@...  │ADMIN  │ACTIVE  │[Edit][Del│   │
│  │ 2  │ john_doe │john@...   │USER   │ACTIVE  │[Edit][Del│   │
│  │ 3  │ jane_doe │jane@...   │INSTR..│ACTIVE  │[Edit][Del│   │
│  │ 4  │ bob_user │bob@...    │USER   │INACTIVE│[Edit][Del│   │
│  │ ... (more rows) ...                                   │   │
│  └──────────────────────────────────────────────────────┘   │
│                                                              │
│  [1] [2] [3] ... [10] (Pagination)                          │
│                                                              │
└─────────────────────────────────────────────────────────────┘
```

#### **Wireframe 6: Login Modal**

```
┌─────────────────────────────────────────────────────────────┐
│  ┌─────────────────────────────────────────────────────┐    │
│  │  Login                                           [X]│    │
│  ├─────────────────────────────────────────────────────┤    │
│  │                                                     │    │
│  │  Username or Email:                                │    │
│  │  ┌─────────────────────────────────────────────┐   │    │
│  │  │                                             │   │    │
│  │  └─────────────────────────────────────────────┘   │    │
│  │                                                     │    │
│  │  Password:                                         │    │
│  │  ┌─────────────────────────────────────────────┐   │    │
│  │  │ ●●●●●●●●●●                                  │   │    │
│  │  └─────────────────────────────────────────────┘   │    │
│  │                                                     │    │
│  │  [ ] Remember me          [Forgot Password?]      │    │
│  │                                                     │    │
│  │  ┌─────────────────────────────────────────────┐   │    │
│  │  │             [Login]                         │   │    │
│  │  └─────────────────────────────────────────────┘   │    │
│  │                                                     │    │
│  │  Don't have an account? [Register]                │    │
│  │                                                     │    │
│  └─────────────────────────────────────────────────────┘    │
└─────────────────────────────────────────────────────────────┘
```

### 2.5.3 Sequence Diagrams

#### **Sequence Diagram 1: User Login Process**

```
User        Browser      JAX-RS       AuthService    PasswordUtil  JWTUtil    UserDAO    Database
 │             │            │              │              │           │          │          │
 │  Enter      │            │              │              │           │          │          │
 │ credentials │            │              │              │           │          │          │
 ├────────────>│            │              │              │           │          │          │
 │             │            │              │              │           │          │          │
 │             │ POST /api/auth/login     │              │           │          │          │
 │             ├───────────>│              │              │           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │ login(username, password)   │           │          │          │
 │             │            ├─────────────>│              │           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │ findByUsername(username) │          │          │
 │             │            │              ├──────────────────────────────────────>         │
 │             │            │              │              │           │          │          │
 │             │            │              │              │           │          │ SELECT * FROM users
 │             │            │              │              │           │          ├─────────>│
 │             │            │              │              │           │          │          │
 │             │            │              │              │           │          │<─────────┤
 │             │            │              │              │           │          │  User    │
 │             │            │              │<──────────────────────────────────────          │
 │             │            │              │  User entity │           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │ verifyPassword(password, hash)      │          │
 │             │            │              ├─────────────>│           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │              │ BCrypt.checkpw()      │          │
 │             │            │              │              ├──────────>│          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │              │<──────────┤          │          │
 │             │            │              │              │  true     │          │          │
 │             │            │              │<─────────────┤           │          │          │
 │             │            │              │  true        │           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │ generateToken(user)      │          │          │
 │             │            │              ├──────────────────────────>          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │              │ Jwts.builder()...     │          │
 │             │            │              │              │           ├─────────>│          │
 │             │            │              │              │           │          │          │
 │             │            │              │              │           │<─────────┤          │
 │             │            │              │              │           │  token   │          │
 │             │            │              │<──────────────────────────          │          │
 │             │            │              │  token       │           │          │          │
 │             │            │              │              │           │          │          │
 │             │            │              │ update(lastLogin)        │          │          │
 │             │            │              ├──────────────────────────────────────>         │
 │             │            │              │              │           │          │          │
 │             │            │              │              │           │          │ UPDATE users
 │             │            │              │              │           │          ├─────────>│
 │             │            │              │              │           │          │          │
 │             │            │              │              │           │          │<─────────┤
 │             │            │              │              │           │          │          │
 │             │            │<─────────────┤              │           │          │          │
 │             │            │  {success: true, token, user}          │          │          │
 │             │<───────────┤              │              │           │          │          │
 │             │ 200 OK     │              │              │           │          │          │
 │             │ JSON       │              │              │           │          │          │
 │<────────────┤            │              │              │           │          │          │
 │  Display    │            │              │              │           │          │          │
 │  Welcome    │            │              │              │           │          │          │
 │             │ Store token in localStorage              │           │          │          │
 │             ├────────────────────────────────────────>│           │          │          │
 │             │            │              │              │           │          │          │
```

#### **Sequence Diagram 2: Borrow Book Process**

```
User      Browser    JAX-RS     BorrowingService  BookDAO   UserDAO  BorrowingRecordDAO  Database
 │           │          │              │              │         │            │              │
 │  Click    │          │              │              │         │            │              │
 │  "Borrow" │          │              │              │         │            │              │
 ├──────────>│          │              │              │         │            │              │
 │           │          │              │              │         │            │              │
 │           │ POST /api/borrowing/borrow            │         │            │              │
 │           ├─────────>│              │              │         │            │              │
 │           │  {userId, bookId, days} │              │         │            │              │
 │           │          │              │              │         │            │              │
 │           │          │ borrowBook(userId, bookId, days)     │            │              │
 │           │          ├─────────────>│              │         │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │ findById(userId)      │            │              │
 │           │          │              ├──────────────────────>│            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │ SELECT * FROM users       │
 │           │          │              │              │         ├───────────────────────────>
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │<───────────────────────────
 │           │          │              │<──────────────────────┤            │      User     │
 │           │          │              │  User        │         │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │ findById(bookId)      │            │              │
 │           │          │              ├──────────────>        │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │              │ SELECT * FROM books  │              │
 │           │          │              │              ├─────────────────────────────────────>
 │           │          │              │              │         │            │              │
 │           │          │              │              │<─────────────────────────────────────
 │           │          │              │<──────────────        │            │      Book     │
 │           │          │              │  Book        │         │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │ Check: availableCopies > 0          │              │
 │           │          │              ├─────────────────────────────────────>              │
 │           │          │              │              │         │            │              │
 │           │          │              │ Check: user active borrowings < 5   │              │
 │           │          │              ├──────────────────────────────────────────────────>│
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │            │ SELECT COUNT(*)
 │           │          │              │              │         │            ├─────────────>│
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │            │<─────────────┤
 │           │          │              │<──────────────────────────────────────────────────┤  3  │
 │           │          │              │  count = 3   │         │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │ Create BorrowingRecord entity       │              │
 │           │          │              │  - dueDate = now + 14 days          │              │
 │           │          │              │  - status = BORROWED                │              │
 │           │          │              ├─────────────────────────────────────>              │
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │   save(record)            │
 │           │          │              │              │         │            ├─────────────>│
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │            │ INSERT INTO
 │           │          │              │              │         │            │ borrowing_records
 │           │          │              │              │         │            ├─────────────>│
 │           │          │              │              │         │            │              │
 │           │          │              │              │         │            │<─────────────┤
 │           │          │              │              │         │            │   record     │
 │           │          │              │              │         │<───────────┤              │
 │           │          │              │              │         │  record    │              │
 │           │          │              │              │         │            │              │
 │           │          │              │ Update book: available_copies - 1   │              │
 │           │          │              ├──────────────>        │            │              │
 │           │          │              │              │         │            │              │
 │           │          │              │              │ UPDATE books SET available_copies   │
 │           │          │              │              ├─────────────────────────────────────>
 │           │          │              │              │         │            │              │
 │           │          │              │              │<─────────────────────────────────────
 │           │          │              │<──────────────        │            │              │
 │           │          │              │              │         │            │              │
 │           │          │<─────────────┤              │         │            │              │
 │           │          │  {success: true, record}    │         │            │              │
 │           │<─────────┤              │              │         │            │              │
 │           │  200 OK  │              │              │         │            │              │
 │<──────────┤          │              │              │         │            │              │
 │  Display  │          │              │              │         │            │              │
 │  Success  │          │              │              │         │            │              │
 │           │          │              │              │         │            │              │
```

#### **Sequence Diagram 3: Return Book with Fine Calculation**

```
Staff     Browser    JAX-RS      BorrowingService  BorrowingRecordDAO  BookDAO  FineDAO   Database
 │           │          │              │                  │                │        │          │
 │  Click    │          │              │                  │                │        │          │
 │  "Return" │          │              │                  │                │        │          │
 ├──────────>│          │              │                  │                │        │          │
 │           │          │              │                  │                │        │          │
 │           │ POST /api/borrowing/return/{recordId}      │                │        │          │
 │           ├─────────>│              │                  │                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │ returnBook(recordId, staffId)   │                │        │          │
 │           │          ├─────────────>│                  │                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ findById(recordId)                │        │          │
 │           │          │              ├─────────────────>│                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │ SELECT * FROM borrowing_records   │
 │           │          │              │                  ├───────────────────────────────────>
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │<───────────────────────────────────
 │           │          │              │<─────────────────┤                │        │  Record  │
 │           │          │              │  BorrowingRecord │                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ Check if already returned          │        │          │
 │           │          │              ├───────────────────────────────────>        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ Check if overdue:                  │        │          │
 │           │          │              │   if (now > dueDate)               │        │          │
 │           │          │              │     daysOverdue = (now - dueDate)  │        │          │
 │           │          │              │     fine = daysOverdue * $1.00     │        │          │
 │           │          │              ├───────────────────────────────────>        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │  daysOverdue = 2 │                │        │          │
 │           │          │              │  fine = $2.00    │                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ Update record:   │                │        │          │
 │           │          │              │  - returnDate = now                │        │          │
 │           │          │              │  - status = RETURNED               │        │          │
 │           │          │              │  - fineAmount = $2.00              │        │          │
 │           │          │              │  - returnedTo = staffId            │        │          │
 │           │          │              ├─────────────────>│                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │ UPDATE borrowing_records          │
 │           │          │              │                  ├───────────────────────────────────>
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │<───────────────────────────────────
 │           │          │              │<─────────────────┤                │        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ Update book: available_copies + 1  │        │          │
 │           │          │              ├────────────────────────────────────>        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │ UPDATE books SET available_copies │
 │           │          │              │                  │                ├───────────────────>
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │                │<───────────────────
 │           │          │              │<────────────────────────────────────        │          │
 │           │          │              │                  │                │        │          │
 │           │          │              │ IF fine > 0: Create fine record    │        │          │
 │           │          │              ├────────────────────────────────────────────>          │
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │                │  save(fine)       │
 │           │          │              │                  │                │        ├─────────>│
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │                │        │ INSERT INTO fines
 │           │          │              │                  │                │        ├─────────>│
 │           │          │              │                  │                │        │          │
 │           │          │              │                  │                │        │<─────────┤
 │           │          │              │<────────────────────────────────────────────          │
 │           │          │              │                  │                │        │          │
 │           │          │<─────────────┤                  │                │        │          │
 │           │          │  {success: true, record, fine: $2.00}            │        │          │
 │           │<─────────┤              │                  │                │        │          │
 │           │  200 OK  │              │                  │                │        │          │
 │<──────────┤          │              │                  │                │        │          │
 │  Display: │          │              │                  │                │        │          │
 │  "Book    │          │              │                  │                │        │          │
 │  returned.│          │              │                  │                │        │          │
 │  Fine: $2"│          │              │                  │                │        │          │
 │           │          │              │                  │                │        │          │
```

#### **Sequence Diagram 4: Admin Add Book**

```
Admin     Browser    JAX-RS     BookService    BookDAO    CategoryDAO   Database
 │           │          │            │             │            │            │
 │  Fill     │          │            │             │            │            │
 │  Form     │          │            │             │            │            │
 ├──────────>│          │            │             │            │            │
 │           │          │            │             │            │            │
 │  Submit   │          │            │             │            │            │
 ├──────────>│          │            │             │            │            │
 │           │          │            │             │            │            │
 │           │ POST /api/books       │             │            │            │
 │           ├─────────>│            │             │            │            │
 │           │  {isbn, title, ...}   │             │            │            │
 │           │          │            │             │            │            │
 │           │          │ createBook(bookData)     │            │            │
 │           │          ├───────────>│             │            │            │
 │           │          │            │             │            │            │
 │           │          │            │ Validate required fields │            │
 │           │          │            ├───────────────────────────>           │
 │           │          │            │             │            │            │
 │           │          │            │ findByIsbn(isbn)         │            │
 │           │          │            ├────────────>│            │            │
 │           │          │            │             │            │            │
 │           │          │            │             │ SELECT * FROM books     │
 │           │          │            │             │ WHERE isbn = ...        │
 │           │          │            │             ├────────────────────────>│
 │           │          │            │             │            │            │
 │           │          │            │             │<────────────────────────┤
 │           │          │            │<────────────┤            │   empty    │
 │           │          │            │  empty      │            │            │
 │           │          │            │             │            │            │
 │           │          │            │ findById(categoryId)     │            │
 │           │          │            ├─────────────────────────>│            │
 │           │          │            │             │            │            │
 │           │          │            │             │            │ SELECT * FROM categories
 │           │          │            │             │            ├───────────>│
 │           │          │            │             │            │            │
 │           │          │            │             │            │<───────────┤
 │           │          │            │<─────────────────────────┤  Category  │
 │           │          │            │  Category   │            │            │
 │           │          │            │             │            │            │
 │           │          │            │ Create Book entity       │            │
 │           │          │            │   - set all properties   │            │
 │           │          │            │   - set category         │            │
 │           │          │            │   - available = total    │            │
 │           │          │            ├───────────────────────────>           │
 │           │          │            │             │            │            │
 │           │          │            │ save(book)  │            │            │
 │           │          │            ├────────────>│            │            │
 │           │          │            │             │            │            │
 │           │          │            │             │ INSERT INTO books       │
 │           │          │            │             ├────────────────────────>│
 │           │          │            │             │            │            │
 │           │          │            │             │<────────────────────────┤
 │           │          │            │<────────────┤            │   book     │
 │           │          │            │  Book       │            │            │
 │           │          │            │             │            │            │
 │           │          │<───────────┤             │            │            │
 │           │          │  {success: true, book}   │            │            │
 │           │<─────────┤            │             │            │            │
 │           │  201     │            │             │            │            │
 │           │  Created │            │             │            │            │
 │<──────────┤          │            │             │            │            │
 │  Display  │          │            │             │            │            │
 │  "Book    │          │            │             │            │            │
 │  added"   │          │            │             │            │            │
 │           │          │            │             │            │            │
 │           │ Refresh book list     │             │            │            │
 │           ├─────────────────────────────────────────────────>            │
 │           │          │            │             │            │            │
```

### 2.5.4 User Interface Design Principles

**1. Consistency**
- Uniform color scheme across all pages
- Consistent button styles and placements
- Standard navigation patterns
- Predictable user interactions

**2. Simplicity**
- Clean, uncluttered layouts
- Clear call-to-action buttons
- Minimal text, maximum clarity
- Progressive disclosure of information

**3. Feedback**
- Toast notifications for actions
- Loading indicators
- Success/error messages
- Visual state changes (hover, active)

**4. Accessibility**
- High contrast text
- Keyboard navigation support
- Screen reader friendly
- Clear focus indicators
- Semantic HTML

**5. Responsiveness**
- Mobile-first design approach
- Flexible grid layouts
- Touch-friendly interface
- Adaptive typography
- Responsive images

**6. Aesthetics**
- Modern flat design
- Professional color palette
- Generous white space
- Smooth animations
- Quality typography

---

## 3. Conclusion

### 3.1 Project Summary

The Library Management System successfully implements a comprehensive solution for managing library operations using Jakarta EE 9.1 technology stack. The system provides:

**Core Achievements:**
1. ✅ Complete user management with three-tier role system
2. ✅ Comprehensive book inventory management
3. ✅ Efficient borrowing and return processes
4. ✅ Automated fine calculation system
5. ✅ Secure authentication with BCrypt and JWT
6. ✅ RESTful API architecture
7. ✅ Responsive, modern web interface
8. ✅ Normalized database design (3NF)
9. ✅ Comprehensive documentation

**Technical Excellence:**
- Clean 4-tier architecture (Client, Web, Business, EIS)
- Generic DAO pattern for code reusability
- Service layer for business logic separation
- JPA/Hibernate for database abstraction
- Stored procedures and triggers for database logic
- Comprehensive error handling
- Transaction management

**User Experience:**
- Intuitive navigation
- Real-time search functionality
- Responsive design for all devices
- Clear visual feedback
- Role-based interface adaptation

### 3.2 System Benefits

**For Library Members:**
- Easy book discovery and borrowing
- 24/7 access to library system
- Track borrowing history
- Receive overdue notifications
- Rate and review books

**For Library Staff:**
- Streamlined book issuing process
- Quick return processing
- Automated fine calculation
- Comprehensive borrowing reports
- Efficient overdue management

**For Administrators:**
- Complete system control
- Real-time statistics and reporting
- User and inventory management
- Fine and payment tracking
- System scalability and maintainability

### 3.3 Future Enhancements

**Phase 2 Features:**
1. Email notifications system
2. SMS reminders for due dates
3. Mobile application (iOS/Android)
4. Advanced search with filters
5. Book recommendation engine
6. E-book and digital media support
7. Online payment integration
8. Barcode/QR code scanning
9. Export reports (PDF/Excel)
10. Multi-language support

**Technical Improvements:**
1. Redis caching layer
2. Elasticsearch for advanced search
3. WebSocket for real-time notifications
4. Microservices architecture migration
5. Container deployment (Docker/Kubernetes)
6. CI/CD pipeline implementation
7. Comprehensive unit and integration testing
8. Performance monitoring and analytics
9. API rate limiting
10. Advanced security features (2FA, OAuth)

### 3.4 Lessons Learned

**Technical Insights:**
- Importance of proper architecture design
- Benefits of layered approach for maintainability
- Value of generic patterns for code reuse
- Critical role of database design in performance
- Security must be built-in from the start

**Development Best Practices:**
- Comprehensive requirement analysis saves time
- Early prototyping validates design decisions
- Documentation improves team collaboration
- Testing throughout development catches issues early
- User feedback is invaluable for UX improvements

### 3.5 Conclusion Statement

The Library Management System demonstrates a professional, production-ready application that successfully addresses all functional and non-functional requirements. The system leverages modern Jakarta EE technologies to provide a secure, scalable, and user-friendly platform for library operations.

The modular architecture ensures easy maintenance and future enhancements, while the comprehensive documentation facilitates knowledge transfer and system understanding. The project serves as a solid foundation for continued development and feature expansion.

---

## Appendices

### Appendix A: Installation Guide

See `DEPLOYMENT.md` for comprehensive installation instructions.

### Appendix B: User Manual

See `FEATURES.md` for detailed feature documentation.

### Appendix C: API Documentation

Complete REST API endpoint documentation available in the source code comments and `README.md`.

### Appendix D: Database Schema

Complete DDL script available in `database/library_management.sql`.

### Appendix E: Source Code Structure

```
src/
├── main/
│   ├── java/com/library/
│   │   ├── entity/          # JPA Entities (8 classes)
│   │   ├── dao/             # Data Access Objects (9 classes)
│   │   ├── service/         # Business Logic (4 classes)
│   │   ├── rest/            # REST Resources (4 classes)
│   │   ├── security/        # Security Components (3 classes)
│   │   └── util/            # Utilities (3 classes)
│   ├── resources/
│   │   └── META-INF/
│   │       └── persistence.xml
│   └── webapp/
│       ├── index.html
│       ├── css/style.css
│       ├── js/app.js
│       └── WEB-INF/web.xml
```

### Appendix F: Technologies Used

| Category | Technology | Version | Purpose |
|----------|-----------|---------|---------|
| Platform | Jakarta EE | 9.1 | Enterprise framework |
| ORM | Hibernate | 5.6.15 | Object-relational mapping |
| Database | MySQL | 8.0 | Data persistence |
| Server | Apache Tomcat | 10.x | Application server |
| Security | BCrypt | 0.4 | Password hashing |
| Security | JWT | 0.11.5 | Token authentication |
| JSON | Gson | 2.10.1 | JSON processing |
| Build | Maven | 3.6+ | Dependency management |
| Frontend | HTML5/CSS3/JS | - | User interface |

### Appendix G: References

1. Jakarta EE 9.1 Specification - https://jakarta.ee/specifications/platform/9.1/
2. JPA 3.0 Specification - https://jakarta.ee/specifications/persistence/3.0/
3. JAX-RS 3.0 Specification - https://jakarta.ee/specifications/restful-ws/3.0/
4. MySQL 8.0 Documentation - https://dev.mysql.com/doc/refman/8.0/
5. Hibernate ORM Documentation - https://hibernate.org/orm/documentation/
6. BCrypt Documentation - https://github.com/jeremyh/jBCrypt
7. JWT (JSON Web Tokens) - https://jwt.io/
8. RESTful API Design Best Practices
9. Database Normalization Theory
10. UML Class Diagram Notation

---

## End of Report

**Total Pages:** [This Report]  
**Word Count:** Approximately 15,000 words  
**Figures:** 15+ diagrams and wireframes  
**Tables:** 10+ tables  

**Prepared by:** [Your Name]  
**Student Number:** [Your Student Number]  
**Date:** December 2025  
**Course:** Jakarta EE Web Application Development

---
