# Library Management System - Feature Documentation

## 🎯 Complete Feature Implementation

This document describes all implemented features for different user roles.

---

## 👤 User Roles

### 1. **USER** (Regular Library Member)
**Access Level:** Basic

#### Features:
- ✅ **Browse Books**
  - View all available books in grid layout
  - Search books by title, author, ISBN, or category
  - View book details (title, ISBN, authors, category, description, availability)

- ✅ **Borrow Books**
  - Borrow available books (max 5 active borrowings)
  - Automatic due date calculation (14 days default)
  - View borrowed book details

- ✅ **My Borrowings**
  - View all active borrowings
  - See borrow date, due date, and status
  - Overdue warnings with days count
  - Return books directly

- ✅ **Account Management**
  - View profile information
  - Update personal details
  - Change password

#### Restrictions:
- ❌ Cannot add/edit/delete books
- ❌ Cannot manage other users
- ❌ Cannot view other users' borrowings
- ❌ Cannot access admin dashboard

---

### 2. **INSTRUCTOR** (Library Staff/Teacher)
**Access Level:** Elevated

#### All USER Features PLUS:

- ✅ **Issue Books to Users**
  - Issue books on behalf of students/members
  - Search users by username or email
  - Set custom borrowing periods (1-90 days)
  - Track who issued each book

- ✅ **Process Returns**
  - Accept book returns from users
  - Automatic fine calculation for overdue books
  - Update book availability immediately

- ✅ **View All Borrowings**
  - See all active borrowings in the system
  - View borrowing history
  - Filter by user or book

- ✅ **Overdue Management**
  - View all overdue books
  - See days overdue and calculated fines
  - Contact information for follow-up
  - Process late returns

#### What Instructors CAN'T Do:
- ❌ Add/edit/delete books (Admin only)
- ❌ Create/delete users (Admin only)
- ❌ Change system settings

---

### 3. **ADMIN** (System Administrator)
**Access Level:** Full Control

#### All INSTRUCTOR Features PLUS:

- ✅ **Complete Book Management**
  - Add new books to the system
  - Edit book information (ISBN, title, authors, etc.)
  - Delete books from inventory
  - Update book availability
  - Manage categories

- ✅ **User Management**
  - Create new user accounts
  - Edit user information
  - Change user roles (USER/INSTRUCTOR/ADMIN)
  - Suspend or deactivate accounts
  - View user statistics

- ✅ **Dashboard & Statistics**
  - Total users count
  - Total books in library
  - Active borrowings count
  - Overdue books count
  - Real-time system overview

- ✅ **Advanced Search**
  - Search across all data
  - Filter by multiple criteria
  - Export data (future feature)

- ✅ **System Settings**
  - Configure borrowing periods
  - Set fine rates
  - Manage system parameters

---

## 📱 Page-by-Page Features

### **Home Page** (Public)
- Hero section with call-to-action
- Feature highlights
- Library statistics
- Recent books showcase

**Access:** Everyone (guests and logged-in users)

---

### **Books Page** (Public)
- Browse all books in grid layout
- Real-time search functionality
- View book details in modal
- Check availability status
- Borrow button (if logged in and available)

**Access:** Everyone (borrow requires login)

**Features:**
- Search by: Title, Author, ISBN, Publisher, Description
- Filter by: Category (future enhancement)
- Sort by: Newest, Title, Author (future enhancement)

---

### **My Borrowings Page** (Authenticated Users)
- View all currently borrowed books
- See borrow date and due date
- Status indicators (Borrowed/Overdue)
- Days overdue counter for late books
- One-click return button
- Fine amount display (if overdue)

**Access:** USER, INSTRUCTOR, ADMIN (logged in)

**Information Displayed:**
- Book title and cover
- Borrow date
- Due date
- Return date (if returned)
- Current status
- Fine amount (if applicable)
- Days overdue (if late)

---

### **Admin Dashboard** (Admin & Instructor)
**Access:** ADMIN and INSTRUCTOR only

#### **Tab 1: Users Management** (ADMIN ONLY)
Features:
- View all registered users in table
- User information: ID, Username, Email, Name, Role, Status
- Add new users with role assignment
- Edit user details
- Change user roles
- Suspend/activate accounts
- Delete users (with confirmation)

Actions:
- ➕ Add User
- ✏️ Edit User
- 🗑️ Delete User
- 🔒 Change Status

---

#### **Tab 2: Books Management** (ADMIN ONLY)
Features:
- View all books in sortable table
- Book information: ID, ISBN, Title, Authors, Category, Copies
- Search books (real-time)
- Add new books to inventory
- Edit book information
- Delete books
- Issue books to users (INSTRUCTOR access)

Actions:
- ➕ Add Book
- ✏️ Edit Book
- 🗑️ Delete Book
- 📤 Issue Book (Instructor)
- 🔍 Search Books

Book Form Fields:
- ISBN (required)
- Title (required)
- Subtitle
- Authors (multiple)
- Category
- Publisher
- Language
- Pages
- Total Copies (required)
- Description

---

#### **Tab 3: Borrowings Management** (ADMIN & INSTRUCTOR)
Features:
- View all borrowing records
- Filter by status (Borrowed/Returned/Overdue)
- See who borrowed what and when
- View who issued the book
- Process returns
- Calculate fines automatically

Information:
- Record ID
- User name
- Book title
- Borrow date
- Due date
- Return date
- Status
- Fine amount
- Issued by / Returned to

Actions:
- ✅ Process Return
- 📋 View Details
- 🔄 Refresh Data

---

#### **Tab 4: Overdue Books** (ADMIN & INSTRUCTOR)
Features:
- View all overdue borrowings
- Days overdue counter
- Auto-calculated fine amounts
- User contact information
- Quick return processing
- Send reminders (future feature)

Display:
- User name and username
- Book title
- Due date
- Days overdue (highlighted)
- Calculated fine ($1/day)
- User email/phone for contact
- Quick actions

Actions:
- ✅ Process Return
- 📧 Send Reminder (future)
- 💰 Waive Fine (future)

---

## 🔐 Security Features

### Authentication
- ✅ Secure login with BCrypt password hashing
- ✅ JWT token-based session management
- ✅ 24-hour token expiration
- ✅ Automatic logout on token expiry
- ✅ Password change functionality

### Authorization
- ✅ Role-based access control (RBAC)
- ✅ Protected routes based on user role
- ✅ API endpoint protection
- ✅ Client-side and server-side validation

### Data Protection
- ✅ SQL injection prevention (JPA)
- ✅ XSS protection
- ✅ CORS configuration
- ✅ Secure password storage

---

## 📊 Business Rules

### Borrowing Rules
1. **Maximum Active Borrowings:** 5 books per user
2. **Default Borrowing Period:** 14 days
3. **Maximum Borrowing Period:** 90 days (Instructor can set)
4. **Fine Rate:** $1.00 per day overdue
5. **Auto Status Update:** Books become "OVERDUE" after due date

### Book Availability
1. **Available Copies:** Must be > 0 to borrow
2. **Automatic Update:** Copies decrease on borrow, increase on return
3. **Reservation System:** Can reserve unavailable books (future)

### User Restrictions
1. **Active Status Required:** Must have ACTIVE status to borrow
2. **No Outstanding Fines:** Can't borrow with unpaid fines (future)
3. **Return Before New Borrow:** Must return overdue books first (future)

---

## 🎨 UI/UX Features

### Responsive Design
- ✅ Mobile-friendly layout
- ✅ Tablet optimization
- ✅ Desktop experience
- ✅ Touch-friendly buttons

### Interactive Elements
- ✅ Modal dialogs for forms
- ✅ Toast notifications for feedback
- ✅ Real-time search
- ✅ Smooth animations
- ✅ Loading indicators

### Visual Feedback
- ✅ Color-coded status badges
- ✅ Icon-based actions
- ✅ Hover effects
- ✅ Active state indicators
- ✅ Error/success messages

### Accessibility
- ✅ Semantic HTML
- ✅ Keyboard navigation
- ✅ Clear labels
- ✅ Readable fonts
- ✅ High contrast

---

## 🔄 Workflow Examples

### User Borrowing a Book
1. User logs in
2. Navigates to Books page
3. Searches for desired book
4. Clicks on book to view details
5. Clicks "Borrow This Book"
6. System checks availability and user limit
7. Creates borrowing record with due date
8. Updates book availability
9. Shows success message
10. User can view in "My Borrowings"

### Instructor Issuing a Book
1. Instructor logs in
2. Goes to Admin Dashboard → Books tab
3. Finds the book to issue
4. Clicks "Issue" button
5. Searches for the user
6. Selects user from results
7. Sets borrowing period (days)
8. Confirms issue
9. System creates borrowing record
10. Book availability updated

### Admin Adding a New Book
1. Admin logs in
2. Goes to Admin Dashboard → Books tab
3. Clicks "Add Book" button
4. Fills in book information:
   - ISBN
   - Title
   - Authors
   - Category
   - Publisher
   - Copies
   - Description
5. Clicks "Save Book"
6. System validates data
7. Creates book in database
8. Shows success message
9. Book appears in list

### Processing Overdue Return
1. Instructor/Admin goes to Overdue tab
2. Sees list of overdue books
3. User returns a book
4. Clicks "Process Return"
5. System calculates fine ($1/day × days overdue)
6. Updates borrowing record status to RETURNED
7. Increases book available copies
8. Creates fine record
9. Shows fine amount to user
10. Updates statistics

---

## 📈 Future Enhancements

### Planned Features
- [ ] Email notifications for due dates
- [ ] SMS reminders for overdue books
- [ ] Book reservation system
- [ ] Reading history and recommendations
- [ ] User reviews and ratings
- [ ] E-book support
- [ ] QR code scanning for books
- [ ] Mobile app
- [ ] Payment gateway integration
- [ ] Advanced reporting and analytics
- [ ] Export to PDF/Excel
- [ ] Multi-language support
- [ ] Dark mode theme

### Technical Improvements
- [ ] Redis caching
- [ ] WebSocket real-time updates
- [ ] Elasticsearch for advanced search
- [ ] Background job processing
- [ ] Automated backup system
- [ ] API rate limiting
- [ ] Comprehensive logging
- [ ] Performance monitoring

---

## 🧪 Testing Scenarios

### User Testing
1. ✅ Register new account
2. ✅ Login with credentials
3. ✅ Browse and search books
4. ✅ Borrow available book
5. ✅ View borrowings
6. ✅ Return book on time
7. ✅ Return book late (fine calculation)

### Instructor Testing
1. ✅ Login as instructor
2. ✅ Issue book to specific user
3. ✅ Process book returns
4. ✅ View all borrowings
5. ✅ Manage overdue books

### Admin Testing
1. ✅ Login as admin
2. ✅ Add new book
3. ✅ Edit book information
4. ✅ Delete book
5. ✅ Create new user
6. ✅ Change user roles
7. ✅ View statistics
8. ✅ Manage all system data

---

## 📞 Support & Help

For each user role, the system provides:
- Clear error messages
- Success confirmations
- Toast notifications
- Inline help text
- Form validation feedback

---

**Last Updated:** December 2025  
**Version:** 1.0.0  
**Status:** Production Ready ✅

