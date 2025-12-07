# Library Management System - Detailed Sitemap

## Application Sitemap Structure

This document illustrates all logical pages and their interactions within the Library Management System.

---

## Complete Sitemap Diagram

```
                                    ┌─────────────────┐
                                    │   APPLICATION   │
                                    │   Entry Point   │
                                    └────────┬────────┘
                                             │
                        ┌────────────────────┼────────────────────┐
                        │                    │                    │
                 ┌──────▼──────┐     ┌───────▼───────┐    ┌──────▼──────┐
                 │  PUBLIC      │     │ AUTHENTICATED │    │   ADMIN     │
                 │  ACCESS      │     │    ACCESS     │    │   ACCESS    │
                 └──────┬──────┘     └───────┬───────┘    └──────┬──────┘
                        │                    │                    │
        ┌───────────────┼──────────┐        │         ┌──────────┼──────────────┐
        │               │          │        │         │          │              │
 ┌──────▼─────┐  ┌──────▼─────┐  │  ┌─────▼────┐   │   ┌──────▼─────┐  ┌──────▼─────┐
 │    HOME    │  │   BOOKS    │  │  │    MY    │   │   │   USERS    │  │   BOOKS    │
 │   PAGE     │  │   PAGE     │  │  │ BORROWINGS│  │   │ MANAGEMENT │  │ MANAGEMENT │
 │  (Landing) │  │ (Catalog)  │  │  │   PAGE   │   │   │   (Admin)  │  │   (Admin)  │
 └────────────┘  └──────┬─────┘  │  └─────┬────┘   │   └──────┬─────┘  └──────┬─────┘
                        │        │        │        │          │                │
                        │        │        │        │          │                │
                 ┌──────▼─────┐ │  ┌─────▼────┐   │   ┌──────▼─────┐   ┌──────▼─────┐
                 │   BOOK     │ │  │  RETURN  │   │   │  ADD/EDIT  │   │  ADD/EDIT  │
                 │  DETAILS   │ │  │   BOOK   │   │   │   USER     │   │    BOOK    │
                 │  (Modal)   │ │  │ (Action) │   │   │  (Modal)   │   │  (Modal)   │
                 └──────┬─────┘ │  └──────────┘   │   └────────────┘   └──────┬─────┘
                        │       │                 │                            │
                 ┌──────▼─────┐ │                 │                     ┌──────▼─────┐
                 │   BORROW   │ │                 │                     │   ISSUE    │
                 │    BOOK    │ │                 │                     │    BOOK    │
                 │  (Action)  │ │                 │                     │ (Instructor)│
                 └────────────┘ │                 │                     └────────────┘
                                │                 │
                         ┌──────▼──────┐          │
                         │    AUTH     │          │
                         │   MODALS    │          │
                         └──────┬──────┘          │
                                │                 │
                    ┌───────────┼───────────┐     │
                    │           │           │     │
             ┌──────▼─────┐ ┌──▼─────┐ ┌──▼─────▼──────┐
             │   LOGIN    │ │ REGISTER│ │  BORROWINGS   │
             │   MODAL    │ │  MODAL  │ │  MANAGEMENT   │
             └────────────┘ └─────────┘ │  (Admin/Instr)│
                                        └───────┬───────┘
                                                │
                                         ┌──────▼──────┐
                                         │   OVERDUE   │
                                         │    BOOKS    │
                                         │  MANAGEMENT │
                                         └─────────────┘

LEGEND:
───────
┌─────┐
│Page │  = Static Page or Section
└─────┘

┌─────┐
│Modal│  = Modal Dialog/Popup
└─────┘

  ───>   = Navigation/Flow
  
  ▼      = Hierarchical Relationship
```

---

## Page-by-Page Breakdown

### 1. HOME PAGE (/)
**Access:** Public (All users)
**Purpose:** Landing page and system introduction

**Components:**
- Navigation Bar (persistent across all pages)
- Hero Section
  - Main heading
  - Call-to-action buttons
    - → Explore Books (navigates to Books Page)
    - → Learn More (scrolls to features)
- Features Section
  - Search Books feature
  - Borrow Books feature
  - Review & Rate feature
  - Track Due Dates feature
- Statistics Section
  - Total Books counter
  - Total Users counter
  - Available Books counter
  - Active Borrowings counter
- Footer (persistent across all pages)

**Navigation Paths:**
- → Books Page (via "Explore Books" button or nav link)
- → Login Modal (via "Login" button)
- → Register Modal (via "Register" button)

**Interactions:**
- Click "Explore Books" → Navigate to Books Page
- Click "Login" → Open Login Modal
- Click "Register" → Open Register Modal
- Scroll to view features and statistics

---

### 2. BOOKS PAGE (/books)
**Access:** Public (Browse), Authenticated (Borrow)
**Purpose:** Browse and search book catalog

**Components:**
- Page Header
  - Page title: "Browse Books"
- Search Bar
  - Real-time search input
  - Search icon
  - Debounced search (500ms delay)
- Books Grid
  - Book cards (grid layout)
  - Each card shows:
    - Book cover (icon/image)
    - Title
    - Authors
    - Category
    - Availability status badge
    - Available/Total copies

**Navigation Paths:**
- → Book Details Modal (click any book card)
- ← Home Page (via nav link)
- → My Borrowings (via nav link, if authenticated)
- → Admin Dashboard (via nav link, if admin/instructor)

**Interactions:**
- Type in search → Real-time filter results
- Click book card → Open Book Details Modal
- Scroll to view more books

**Data Flow:**
- Loads books from API: GET /api/books
- Search query: GET /api/books/search?query={term}
- Updates display dynamically

---

### 3. BOOK DETAILS MODAL (overlay)
**Access:** Public
**Purpose:** Display comprehensive book information

**Components:**
- Modal Header
  - Book title
  - Close button (×)
- Book Information
  - ISBN
  - Authors
  - Category
  - Publisher
  - Language
  - Pages
  - Available copies
  - Description
- Review Section (if applicable)
  - Average rating (stars)
  - Review count
- Action Buttons
  - "Borrow This Book" (if authenticated and available)
  - "Add to Wishlist" (future feature)
  - "Close" button

**Navigation Paths:**
- → Borrow Action (if authenticated)
  - Creates borrowing record
  - Updates book availability
  - → My Borrowings Page
- ← Books Page (close modal)

**Interactions:**
- Click "Borrow This Book" → API call → Success toast → Close modal
- Click "×" or outside → Close modal
- View all book details

**Data Flow:**
- Load: GET /api/books/{id}
- Borrow: POST /api/borrowing/borrow

---

### 4. MY BORROWINGS PAGE (/my-borrowings)
**Access:** Authenticated Users Only
**Purpose:** View and manage personal borrowings

**Components:**
- Page Header
  - Page title: "My Borrowings"
- Active Borrowings List
  - Borrowing cards
  - Each card shows:
    - Book cover and title
    - Borrow date
    - Due date
    - Status badge (Borrowed/Overdue)
    - Days overdue (if applicable)
    - Fine amount (if overdue)
    - "Return Book" button
- Empty State
  - Message: "No active borrowings"
  - Link to browse books

**Navigation Paths:**
- → Return Book Action
  - Processes return
  - Calculates fine
  - Updates availability
  - Refreshes list
- ← Home Page (via nav)
- → Books Page (via "Browse Books" link if empty)

**Interactions:**
- Click "Return Book" → Confirmation → API call → Success toast
- View borrowing status
- See overdue warnings

**Data Flow:**
- Load: GET /api/borrowing/user/{userId}/active
- Return: POST /api/borrowing/return/{recordId}

---

### 5. ADMIN DASHBOARD (/admin)
**Access:** Admin and Instructor Only
**Purpose:** System management and administration

**Components:**
- Dashboard Header
  - Page title: "Admin Dashboard"
- Statistics Cards (4 cards)
  - Total Users
  - Total Books
  - Active Borrowings
  - Overdue Books
- Tab Navigation
  - Users Management (Admin only)
  - Books Management (Admin only)
  - Borrowings (Admin & Instructor)
  - Overdue Books (Admin & Instructor)
- Tab Content Area (dynamic)

**Navigation Paths:**
- → Users Management Tab
  - → Add User Modal
  - → Edit User Modal
  - → Delete User Action
- → Books Management Tab
  - → Add Book Modal
  - → Edit Book Modal
  - → Delete Book Action
  - → Issue Book Modal (Instructor)
- → Borrowings Tab
  - → Process Return Action
- → Overdue Books Tab
  - → Process Return Action

**Interactions:**
- Switch tabs → Load corresponding data
- Click action buttons → Open modals or execute actions
- View real-time statistics

**Data Flow:**
- Statistics: Multiple API calls
- Users: GET /api/users
- Books: GET /api/books
- Borrowings: GET /api/borrowing/all
- Overdue: GET /api/borrowing/overdue

---

### 6. USERS MANAGEMENT TAB (Admin Dashboard)
**Access:** Admin Only
**Purpose:** Manage user accounts

**Components:**
- Tab Header
  - Section title: "Manage Users"
  - "Add User" button
- Users Table
  - Columns: ID, Username, Email, Full Name, Role, Status, Registration Date, Actions
  - Rows: All users
  - Action buttons per row:
    - Edit (pencil icon)
    - Delete (trash icon)
- Pagination (if implemented)

**Sub-Pages/Modals:**
- Add User Modal
  - Form fields: firstName, lastName, username, email, password, role, status, phone, address
  - Submit → POST /api/auth/register (with role)
  - Cancel → Close modal
- Edit User Modal
  - Pre-filled form with user data
  - Submit → PUT /api/users/{id}
  - No password field (use change password separately)

**Interactions:**
- Click "Add User" → Open Add User Modal
- Click "Edit" → Load user data → Open Edit User Modal
- Click "Delete" → Confirmation → DELETE /api/users/{id}
- Search users (future feature)

---

### 7. BOOKS MANAGEMENT TAB (Admin Dashboard)
**Access:** Admin Only (View/Edit), Instructor (Issue only)
**Purpose:** Manage book inventory

**Components:**
- Tab Header
  - Section title: "Manage Books"
  - "Add Book" button
- Search Bar
  - Real-time book search
- Books Table
  - Columns: ID, ISBN, Title, Authors, Category, Available/Total, Status, Actions
  - Action buttons:
    - Edit (Admin)
    - Delete (Admin)
    - Issue (Instructor)

**Sub-Pages/Modals:**
- Add Book Modal
  - Form: ISBN, title, subtitle, publisher, language, pages, totalCopies, description
  - Submit → POST /api/books
- Edit Book Modal
  - Pre-filled form
  - Submit → PUT /api/books/{id}
- Issue Book Modal (Instructor)
  - User search field
  - Borrowing period selector
  - Submit → POST /api/borrowing/borrow

**Interactions:**
- Click "Add Book" → Open Add Book Modal
- Click "Edit" → Load book data → Open Edit Book Modal
- Click "Delete" → Confirmation → DELETE /api/books/{id} → Refresh
- Click "Issue" → Open Issue Book Modal
- Search books → Filter results

---

### 8. BORROWINGS MANAGEMENT TAB (Admin Dashboard)
**Access:** Admin and Instructor
**Purpose:** View and manage all borrowing records

**Components:**
- Tab Header
  - Section title: "All Borrowings"
  - Refresh button
- Borrowings Table
  - Columns: ID, User, Book, Borrow Date, Due Date, Return Date, Status, Fine, Actions
  - Status badges with colors
  - Fine amounts highlighted
  - Action buttons:
    - "Return" (for active borrowings)

**Interactions:**
- Click "Refresh" → Reload data
- Click "Return" → Process return → Update record → Calculate fine
- View all borrowing history
- Filter by status (future)

**Data Flow:**
- Load: GET /api/borrowing/all
- Return: POST /api/borrowing/return/{recordId}

---

### 9. OVERDUE BOOKS TAB (Admin Dashboard)
**Access:** Admin and Instructor
**Purpose:** Manage overdue books and fines

**Components:**
- Tab Header
  - Section title: "Overdue Books"
  - "Refresh Overdue" button
- Overdue Table
  - Columns: User, Book, Due Date, Days Overdue, Fine Amount, Contact, Actions
  - Red highlighting for critical overdue
  - Contact information (email/phone)
  - "Process Return" button

**Interactions:**
- Click "Refresh" → Reload overdue list
- Click "Process Return" → Return book → Calculate fine → Update
- View overdue details
- Contact user (click email/phone)

**Data Flow:**
- Load: GET /api/borrowing/overdue
- Return: POST /api/borrowing/return/{recordId}
- Fine calculation: Automatic (daysOverdue × $1.00)

---

### 10. AUTHENTICATION MODALS (Overlay)

#### **10.1 LOGIN MODAL**
**Access:** All users (not logged in)
**Purpose:** User authentication

**Components:**
- Modal Header: "Login"
- Form Fields:
  - Username or Email (text input)
  - Password (password input)
- Remember Me checkbox (future)
- Forgot Password link (future)
- Submit button: "Login"
- Register link: "Don't have an account? Register"

**Flow:**
1. User enters credentials
2. Submit form
3. API: POST /api/auth/login
4. If success:
   - Store JWT token in localStorage
   - Update UI (show user name)
   - Close modal
   - Show success toast
5. If fail:
   - Show error toast
   - Keep modal open

**Navigation:**
- → Register Modal (click "Register" link)
- ← Close modal (click × or outside)

#### **10.2 REGISTER MODAL**
**Access:** All users (not logged in)
**Purpose:** New user registration

**Components:**
- Modal Header: "Register"
- Form Fields:
  - First Name (required)
  - Last Name (required)
  - Username (required, unique)
  - Email (required, unique)
  - Password (required, min 6 chars)
- Submit button: "Register"
- Login link: "Already have an account? Login"

**Flow:**
1. User fills registration form
2. Client-side validation
3. Submit form
4. API: POST /api/auth/register
5. If success:
   - Auto-login with returned token
   - Close modal
   - Show welcome message
6. If fail:
   - Show error (username/email exists)
   - Keep modal open

**Navigation:**
- → Login Modal (click "Login" link)
- ← Close modal (click × or outside)

---

## User Flow Diagrams

### Flow 1: Guest User Journey

```
START (Homepage)
    │
    ├─> View Features and Statistics
    │
    ├─> Navigate to Books Page
    │   │
    │   ├─> Browse Books
    │   │
    │   ├─> Search for Books
    │   │
    │   └─> Click Book → View Details Modal
    │       │
    │       └─> Click "Borrow" → Redirect to Login
    │
    ├─> Click "Login" → Login Modal
    │   │
    │   ├─> Enter credentials → Submit
    │   │
    │   └─> Success → Close Modal → User Dashboard
    │
    └─> Click "Register" → Register Modal
        │
        ├─> Fill form → Submit
        │
        └─> Success → Auto-login → User Dashboard
```

### Flow 2: Regular User Journey

```
START (Logged in as USER)
    │
    ├─> HOME PAGE
    │   └─> View Statistics
    │
    ├─> BOOKS PAGE
    │   ├─> Browse/Search Books
    │   │
    │   └─> Select Book → Book Details
    │       │
    │       └─> Click "Borrow This Book"
    │           │
    │           ├─> API validates availability
    │           │
    │           ├─> Check borrowing limit
    │           │
    │           ├─> Create borrowing record
    │           │
    │           └─> Success → Book borrowed
    │
    ├─> MY BORROWINGS PAGE
    │   ├─> View Active Borrowings
    │   │   ├─> See due dates
    │   │   ├─> Overdue warnings
    │   │   └─> Fine amounts
    │   │
    │   └─> Click "Return Book"
    │       │
    │       ├─> API processes return
    │       │
    │       ├─> Calculate fine (if overdue)
    │       │
    │       └─> Success → Book returned
    │
    └─> LOGOUT
        └─> Return to Homepage
```

### Flow 3: Instructor Journey

```
START (Logged in as INSTRUCTOR)
    │
    ├─> All USER functions
    │
    └─> ADMIN DASHBOARD
        │
        ├─> BOOKS MANAGEMENT TAB
        │   │
        │   ├─> View all books
        │   │
        │   └─> Click "Issue" on a book
        │       │
        │       ├─> Issue Book Modal opens
        │       │
        │       ├─> Search for user
        │       │
        │       ├─> Select user
        │       │
        │       ├─> Set borrowing period
        │       │
        │       └─> Submit → Book issued to user
        │
        ├─> BORROWINGS TAB
        │   │
        │   ├─> View all borrowings
        │   │
        │   └─> Process returns
        │
        └─> OVERDUE BOOKS TAB
            │
            ├─> View overdue list
            │
            ├─> See calculated fines
            │
            └─> Click "Process Return"
                │
                ├─> Record returned
                │
                ├─> Fine created
                │
                └─> Success message with fine amount
```

### Flow 4: Admin Journey

```
START (Logged in as ADMIN)
    │
    ├─> All INSTRUCTOR functions
    │
    └─> ADMIN DASHBOARD
        │
        ├─> View Statistics Dashboard
        │   ├─> Total Users count
        │   ├─> Total Books count
        │   ├─> Active Borrowings count
        │   └─> Overdue Books count
        │
        ├─> USERS MANAGEMENT TAB
        │   │
        │   ├─> View all users table
        │   │
        │   ├─> Click "Add User"
        │   │   └─> Fill form → Select role → Submit → User created
        │   │
        │   ├─> Click "Edit" on user
        │   │   └─> Modify details → Change role/status → Submit → Updated
        │   │
        │   └─> Click "Delete" on user
        │       └─> Confirm → User deleted → Refresh table
        │
        ├─> BOOKS MANAGEMENT TAB
        │   │
        │   ├─> View all books table
        │   │
        │   ├─> Search books (real-time)
        │   │
        │   ├─> Click "Add Book"
        │   │   └─> Fill form (ISBN, title, etc.) → Submit → Book added
        │   │
        │   ├─> Click "Edit" on book
        │   │   └─> Modify details → Submit → Book updated
        │   │
        │   ├─> Click "Delete" on book
        │   │   └─> Confirm → Book deleted → Refresh
        │   │
        │   └─> Click "Issue" on book
        │       └─> Search user → Select → Set days → Issue
        │
        ├─> BORROWINGS TAB
        │   └─> View/manage all borrowing records
        │
        └─> OVERDUE BOOKS TAB
            └─> Manage overdue returns and fines
```

---

## Page Interaction Matrix

| From Page | To Page/Modal | Action Required | User Role |
|-----------|---------------|-----------------|-----------|
| Home | Books | Click nav link | All |
| Home | Login Modal | Click "Login" button | Guest |
| Home | Register Modal | Click "Register" button | Guest |
| Books | Book Details | Click book card | All |
| Books | My Borrowings | Click nav link | Authenticated |
| Book Details | Borrow Action | Click "Borrow" | Authenticated |
| My Borrowings | Return Action | Click "Return" | Authenticated |
| Any Page | Admin Dashboard | Click "Admin" nav | Admin/Instructor |
| Admin | Users Tab | Click tab | Admin |
| Admin | Books Tab | Click tab | Admin/Instructor |
| Admin | Borrowings Tab | Click tab | Admin/Instructor |
| Admin | Overdue Tab | Click tab | Admin/Instructor |
| Users Tab | Add User Modal | Click "Add User" | Admin |
| Users Tab | Edit User Modal | Click "Edit" | Admin |
| Books Tab | Add Book Modal | Click "Add Book" | Admin |
| Books Tab | Edit Book Modal | Click "Edit" | Admin |
| Books Tab | Issue Book Modal | Click "Issue" | Instructor |

---

## State Management Flow

### Global Application State

```
┌─────────────────────────────────────────┐
│      Application State                  │
├─────────────────────────────────────────┤
│ • currentUser: User | null              │
│ • currentToken: JWT | null              │
│ • currentPage: string                   │
│ • isAuthenticated: boolean              │
└─────────────────────────────────────────┘
         │
         │ Persisted in
         ▼
┌─────────────────────────────────────────┐
│      Browser localStorage                │
├─────────────────────────────────────────┤
│ • token: JWT string                     │
└─────────────────────────────────────────┘

STATE TRANSITIONS:
──────────────────

Guest State → Authenticated State
  Trigger: Successful login/register
  Actions: 
    - Store token in localStorage
    - Set currentUser object
    - Update navbar UI
    - Show role-specific nav links

Authenticated State → Guest State
  Trigger: Logout or token expiration
  Actions:
    - Remove token from localStorage
    - Clear currentUser
    - Update navbar UI
    - Redirect to Home
    - Hide protected nav links

Page Navigation:
  - Update currentPage
  - Hide all pages
  - Show selected page
  - Update nav link active state
  - Load page-specific data
```

---

## API Interaction Map

### RESTful API Endpoints and Page Usage

```
┌─────────────────────────────────────────────────────────────┐
│                    API ENDPOINTS                             │
└─────────────────────────────────────────────────────────────┘

/api/auth/*
├─ POST /login              Used by: Login Modal
├─ POST /register           Used by: Register Modal, Add User Modal
├─ GET  /me                 Used by: App initialization
└─ POST /change-password    Used by: Profile Settings (future)

/api/users/*
├─ GET  /                   Used by: Users Management Tab
├─ GET  /{id}               Used by: Edit User Modal
├─ POST /                   Used by: Add User Modal
├─ PUT  /{id}               Used by: Edit User Modal (submit)
├─ DELETE /{id}             Used by: Users Management Tab (delete)
├─ GET  /search?query=x     Used by: User search (Issue Book Modal)
└─ GET  /statistics         Used by: Admin Dashboard Stats

/api/books/*
├─ GET  /                   Used by: Books Page, Admin Books Tab
├─ GET  /available          Used by: Books Page (filter)
├─ GET  /search?query=x     Used by: Books Page search, Admin search
├─ GET  /{id}               Used by: Book Details Modal
├─ POST /                   Used by: Add Book Modal
├─ PUT  /{id}               Used by: Edit Book Modal
└─ DELETE /{id}             Used by: Books Management Tab (delete)

/api/borrowing/*
├─ POST /borrow             Used by: Book Details Modal, Issue Book Modal
├─ POST /return/{id}        Used by: My Borrowings, Admin tabs
├─ GET  /user/{userId}      Used by: My Borrowings (history)
├─ GET  /user/{userId}/active  Used by: My Borrowings (active)
├─ GET  /overdue            Used by: Overdue Books Tab
├─ GET  /all                Used by: Borrowings Tab
└─ GET  /active             Used by: Admin Statistics
```

---

## Navigation Hierarchy Tree

```
Application Root
│
├─ Public Pages (No Authentication Required)
│  ├─ Home Page (/)
│  │  ├─ Hero Section
│  │  ├─ Features Section
│  │  └─ Statistics Section
│  │
│  ├─ Books Page (/books)
│  │  ├─ Search Bar
│  │  ├─ Books Grid
│  │  └─ Book Details Modal (overlay)
│  │
│  └─ Authentication Modals (overlays)
│     ├─ Login Modal
│     └─ Register Modal
│
├─ Authenticated Pages (Login Required)
│  ├─ My Borrowings Page (/my-borrowings)
│  │  ├─ Active Borrowings List
│  │  ├─ Borrowing History
│  │  └─ Return Book Action
│  │
│  └─ Profile Settings (future)
│     ├─ Personal Information
│     ├─ Change Password
│     └─ Preferences
│
└─ Admin Pages (Admin/Instructor Only)
   └─ Admin Dashboard (/admin)
      ├─ Statistics Cards
      ├─ Users Management Tab (Admin only)
      │  ├─ Users Table
      │  ├─ Add User Modal
      │  ├─ Edit User Modal
      │  └─ Delete User Action
      │
      ├─ Books Management Tab (Admin only)
      │  ├─ Books Table
      │  ├─ Search Books
      │  ├─ Add Book Modal
      │  ├─ Edit Book Modal
      │  ├─ Delete Book Action
      │  └─ Issue Book Modal (Instructor)
      │
      ├─ Borrowings Management Tab (Admin & Instructor)
      │  ├─ All Borrowings Table
      │  ├─ Filter Options
      │  └─ Process Return Action
      │
      └─ Overdue Books Tab (Admin & Instructor)
         ├─ Overdue Table
         ├─ Days Overdue Display
         ├─ Fine Calculation Display
         └─ Process Return Action
```

---

## Page Responsibility Chart

| Page/Component | Primary Function | Data Source | User Actions |
|----------------|------------------|-------------|--------------|
| Home | Information, CTA | Static + API stats | Navigate, Login, Register |
| Books | Browse catalog | GET /api/books | Search, View details, Borrow |
| Book Details | Show full info | GET /api/books/{id} | Borrow, Close |
| My Borrowings | User's books | GET /api/borrowing/user/{id} | Return, View |
| Admin Dashboard | System overview | Multiple APIs | Navigate tabs |
| Users Management | User admin | GET /api/users | CRUD operations |
| Books Management | Book admin | GET /api/books | CRUD operations, Issue |
| Borrowings Tab | View all loans | GET /api/borrowing/all | Process returns |
| Overdue Tab | Manage overdue | GET /api/borrowing/overdue | Process, Contact |
| Login Modal | Authentication | POST /api/auth/login | Login |
| Register Modal | New account | POST /api/auth/register | Register |

---

## Sitemap Summary

**Total Logical Pages:** 10 main pages/sections
**Total Modals:** 6 modal dialogs
**Total API Endpoints:** 25+ endpoints
**User Roles:** 3 (Guest, User, Instructor, Admin)
**Navigation Levels:** 3 (Primary, Secondary, Tertiary)

**Key Features:**
- ✅ Intuitive navigation
- ✅ Role-based page visibility
- ✅ Consistent layout across pages
- ✅ Modal-based forms (no page reload)
- ✅ Real-time data updates
- ✅ Clear user feedback
- ✅ Responsive design
- ✅ Accessible structure

---

**Document Version:** 1.0  
**Last Updated:** December 2025  
**Status:** Complete

