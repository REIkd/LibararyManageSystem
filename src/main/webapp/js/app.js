// ===============================================
// Library Management System - JavaScript
// ===============================================

// API Base URL
const API_URL = '/library-management-system/api';

// Global State
let currentUser = null;
let currentToken = null;

// ===============================================
// Utility Functions
// ===============================================

// Show toast notification
function showToast(message, type = 'success') {
    const toast = document.getElementById('toast');
    toast.textContent = message;
    toast.className = `toast show ${type}`;
    
    setTimeout(() => {
        toast.classList.remove('show');
    }, 3000);
}

// API Request Helper
async function apiRequest(endpoint, options = {}) {
    const url = `${API_URL}${endpoint}`;
    const headers = {
        'Content-Type': 'application/json',
        ...options.headers
    };
    
    if (currentToken) {
        headers['Authorization'] = `Bearer ${currentToken}`;
    }
    
    try {
        const response = await fetch(url, {
            ...options,
            headers
        });
        
        const data = await response.json();
        return data;
    } catch (error) {
        console.error('API Request Error:', error);
        throw error;
    }
}

// ===============================================
// Modal Functions
// ===============================================

function openModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.add('show');
}

function closeModal(modalId) {
    const modal = document.getElementById(modalId);
    modal.classList.remove('show');
}

// Close modal when clicking outside
window.addEventListener('click', (e) => {
    if (e.target.classList.contains('modal')) {
        e.target.classList.remove('show');
    }
});

// Modal close buttons
document.querySelectorAll('.modal-close').forEach(btn => {
    btn.addEventListener('click', (e) => {
        const modalId = e.target.dataset.modal;
        closeModal(modalId);
    });
});

// ===============================================
// Navigation
// ===============================================

// Page navigation
document.querySelectorAll('.nav-link').forEach(link => {
    link.addEventListener('click', (e) => {
        e.preventDefault();
        const page = e.target.dataset.page;
        navigateToPage(page);
    });
});

function navigateToPage(page) {
    // Hide all pages
    document.querySelectorAll('.page').forEach(p => {
        p.style.display = 'none';
    });
    
    // Show selected page
    const targetPage = document.getElementById(`${page}-page`);
    if (targetPage) {
        targetPage.style.display = 'block';
    }
    
    // Update active nav link
    document.querySelectorAll('.nav-link').forEach(link => {
        link.classList.remove('active');
        if (link.dataset.page === page) {
            link.classList.add('active');
        }
    });
    
    // Load page data
    loadPageData(page);
}

function loadPageData(page) {
    switch(page) {
        case 'home':
            loadHomeData();
            break;
        case 'books':
            loadBooks();
            break;
        case 'my-borrowings':
            loadUserBorrowings();
            break;
        case 'admin':
            loadAdminData();
            break;
    }
}

// Hamburger menu
document.getElementById('hamburger').addEventListener('click', () => {
    document.getElementById('nav-menu').classList.toggle('active');
});

// ===============================================
// Authentication
// ===============================================

// Check for stored token on load
window.addEventListener('DOMContentLoaded', () => {
    const token = localStorage.getItem('token');
    if (token) {
        currentToken = token;
        validateToken();
    }
    loadHomeData();
});

// Validate token
async function validateToken() {
    try {
        const response = await apiRequest('/auth/me');
        if (response.success) {
            currentUser = response.user;
            updateUIForLoggedInUser();
        } else {
            logout();
        }
    } catch (error) {
        logout();
    }
}

// Login
document.getElementById('login-btn').addEventListener('click', () => {
    openModal('login-modal');
});

document.getElementById('login-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const username = document.getElementById('login-username').value;
    const password = document.getElementById('login-password').value;
    
    try {
        const response = await apiRequest('/auth/login', {
            method: 'POST',
            body: JSON.stringify({ username, password })
        });
        
        if (response.success) {
            currentToken = response.token;
            currentUser = response.user;
            localStorage.setItem('token', currentToken);
            updateUIForLoggedInUser();
            closeModal('login-modal');
            showToast('Login successful!');
            document.getElementById('login-form').reset();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Login failed. Please try again.', 'error');
    }
});

// Register
document.getElementById('register-btn').addEventListener('click', () => {
    openModal('register-modal');
});

document.getElementById('register-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const formData = {
        username: document.getElementById('register-username').value,
        email: document.getElementById('register-email').value,
        password: document.getElementById('register-password').value,
        firstName: document.getElementById('register-firstname').value,
        lastName: document.getElementById('register-lastname').value
    };
    
    try {
        const response = await apiRequest('/auth/register', {
            method: 'POST',
            body: JSON.stringify(formData)
        });
        
        if (response.success) {
            currentToken = response.token;
            currentUser = response.user;
            localStorage.setItem('token', currentToken);
            updateUIForLoggedInUser();
            closeModal('register-modal');
            showToast('Registration successful!');
            document.getElementById('register-form').reset();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Registration failed. Please try again.', 'error');
    }
});

// Logout
document.getElementById('logout-btn').addEventListener('click', () => {
    logout();
});

function logout() {
    currentToken = null;
    currentUser = null;
    localStorage.removeItem('token');
    updateUIForLoggedOutUser();
    navigateToPage('home');
    showToast('Logged out successfully');
}

// Update UI based on login state
function updateUIForLoggedInUser() {
    document.getElementById('auth-buttons').style.display = 'none';
    document.getElementById('user-info').style.display = 'flex';
    document.getElementById('user-name').textContent = currentUser.fullName;
    document.getElementById('my-borrowings-link').style.display = 'block';
    
    if (currentUser.role === 'ADMIN' || currentUser.role === 'INSTRUCTOR') {
        document.getElementById('admin-link').style.display = 'block';
    }
}

function updateUIForLoggedOutUser() {
    document.getElementById('auth-buttons').style.display = 'flex';
    document.getElementById('user-info').style.display = 'none';
    document.getElementById('my-borrowings-link').style.display = 'none';
    document.getElementById('admin-link').style.display = 'none';
}

// ===============================================
// Home Page
// ===============================================

async function loadHomeData() {
    try {
        // Load statistics
        const booksResponse = await apiRequest('/books');
        if (booksResponse.success) {
            const books = booksResponse.data;
            document.getElementById('total-books').textContent = books.length;
            const availableBooks = books.filter(b => b.availableCopies > 0);
            document.getElementById('available-books').textContent = availableBooks.length;
        }
    } catch (error) {
        console.error('Error loading home data:', error);
    }
}

// Hero buttons
document.getElementById('explore-books-btn').addEventListener('click', () => {
    navigateToPage('books');
});

// ===============================================
// Books Page
// ===============================================

async function loadBooks(searchQuery = '') {
    try {
        let endpoint = '/books';
        if (searchQuery) {
            endpoint = `/books/search?query=${encodeURIComponent(searchQuery)}`;
        }
        
        const response = await apiRequest(endpoint);
        if (response.success) {
            displayBooks(response.data);
        }
    } catch (error) {
        console.error('Error loading books:', error);
        showToast('Failed to load books', 'error');
    }
}

function displayBooks(books) {
    const grid = document.getElementById('books-grid');
    
    if (books.length === 0) {
        grid.innerHTML = '<p>No books found.</p>';
        return;
    }
    
    grid.innerHTML = books.map(book => `
        <div class="book-card" onclick="showBookDetail(${book.bookId})">
            <div class="book-cover">
                <i class="fas fa-book"></i>
            </div>
            <div class="book-info">
                <h3 class="book-title">${book.title}</h3>
                <p class="book-author">${book.authors && book.authors.length > 0 ? 
                    book.authors.map(a => a.authorName).join(', ') : 'Unknown Author'}</p>
                ${book.category ? `<p class="book-author">${book.category.categoryName}</p>` : ''}
                <div class="book-meta">
                    <span class="book-status ${book.availableCopies > 0 ? 'available' : 'unavailable'}">
                        ${book.availableCopies > 0 ? 'Available' : 'Unavailable'}
                    </span>
                    <span>${book.availableCopies}/${book.totalCopies}</span>
                </div>
            </div>
        </div>
    `).join('');
}

// Book search
let searchTimeout;
document.getElementById('book-search').addEventListener('input', (e) => {
    clearTimeout(searchTimeout);
    searchTimeout = setTimeout(() => {
        loadBooks(e.target.value);
    }, 500);
});

// Show book detail
async function showBookDetail(bookId) {
    try {
        const response = await apiRequest(`/books/${bookId}`);
        if (response.success) {
            const book = response.data;
            displayBookDetail(book);
            openModal('book-detail-modal');
        }
    } catch (error) {
        console.error('Error loading book detail:', error);
        showToast('Failed to load book details', 'error');
    }
}

function displayBookDetail(book) {
    const content = document.getElementById('book-detail-content');
    document.getElementById('book-detail-title').textContent = book.title;
    
    content.innerHTML = `
        <div style="padding: 1.5rem;">
            <div style="display: grid; gap: 1rem;">
                <div>
                    <strong>ISBN:</strong> ${book.isbn}
                </div>
                <div>
                    <strong>Authors:</strong> ${book.authors && book.authors.length > 0 ? 
                        book.authors.map(a => a.authorName).join(', ') : 'Unknown'}
                </div>
                <div>
                    <strong>Category:</strong> ${book.category ? book.category.categoryName : 'N/A'}
                </div>
                <div>
                    <strong>Publisher:</strong> ${book.publisher || 'N/A'}
                </div>
                <div>
                    <strong>Language:</strong> ${book.language}
                </div>
                <div>
                    <strong>Pages:</strong> ${book.pages || 'N/A'}
                </div>
                <div>
                    <strong>Available Copies:</strong> ${book.availableCopies} / ${book.totalCopies}
                </div>
                ${book.description ? `
                <div>
                    <strong>Description:</strong>
                    <p style="margin-top: 0.5rem;">${book.description}</p>
                </div>
                ` : ''}
                ${currentUser && book.availableCopies > 0 ? `
                <button class="btn btn-primary btn-block" onclick="borrowBook(${book.bookId})">
                    Borrow This Book
                </button>
                ` : ''}
            </div>
        </div>
    `;
}

// Borrow book
async function borrowBook(bookId) {
    if (!currentUser) {
        showToast('Please login to borrow books', 'warning');
        return;
    }
    
    try {
        const response = await apiRequest('/borrowing/borrow', {
            method: 'POST',
            body: JSON.stringify({
                userId: currentUser.userId,
                bookId: bookId,
                days: 14
            })
        });
        
        if (response.success) {
            showToast('Book borrowed successfully!');
            closeModal('book-detail-modal');
            loadBooks();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        console.error('Error borrowing book:', error);
        showToast('Failed to borrow book', 'error');
    }
}

// ===============================================
// Borrowings Page
// ===============================================

async function loadUserBorrowings() {
    if (!currentUser) {
        return;
    }
    
    try {
        const response = await apiRequest(`/borrowing/user/${currentUser.userId}/active`);
        if (response.success) {
            displayBorrowings(response.data);
        }
    } catch (error) {
        console.error('Error loading borrowings:', error);
        showToast('Failed to load borrowings', 'error');
    }
}

function displayBorrowings(borrowings) {
    const container = document.getElementById('borrowings-container');
    
    if (borrowings.length === 0) {
        container.innerHTML = '<p>No active borrowings.</p>';
        return;
    }
    
    container.innerHTML = borrowings.map(record => `
        <div class="book-card">
            <div class="book-info">
                <h3 class="book-title">${record.book.title}</h3>
                <p><strong>Borrowed:</strong> ${new Date(record.borrowDate).toLocaleDateString()}</p>
                <p><strong>Due:</strong> ${new Date(record.dueDate).toLocaleDateString()}</p>
                <p><strong>Status:</strong> <span class="badge badge-${record.isOverdue ? 'danger' : 'success'}">${record.status}</span></p>
                ${record.isOverdue ? `<p class="text-danger">Overdue by ${record.daysOverdue} days</p>` : ''}
                <button class="btn btn-primary btn-block" onclick="returnBook(${record.recordId})" style="margin-top: 1rem;">
                    Return Book
                </button>
            </div>
        </div>
    `).join('');
}

// Return book
async function returnBook(recordId) {
    try {
        const response = await apiRequest(`/borrowing/return/${recordId}`, {
            method: 'POST',
            body: JSON.stringify({})
        });
        
        if (response.success) {
            if (response.fine > 0) {
                showToast(`Book returned. Fine: $${response.fine}`, 'warning');
            } else {
                showToast('Book returned successfully!');
            }
            loadUserBorrowings();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        console.error('Error returning book:', error);
        showToast('Failed to return book', 'error');
    }
}

// ===============================================
// Admin Page
// ===============================================

// Admin tabs
document.querySelectorAll('.tab-btn').forEach(btn => {
    btn.addEventListener('click', (e) => {
        const tab = e.target.dataset.tab;
        
        // Update active tab button
        document.querySelectorAll('.tab-btn').forEach(b => b.classList.remove('active'));
        e.target.classList.add('active');
        
        // Show corresponding content
        document.querySelectorAll('.tab-content').forEach(c => c.classList.remove('active'));
        document.getElementById(`${tab}-tab`).classList.add('active');
        
        // Load tab data
        loadAdminTabData(tab);
    });
});

function loadAdminData() {
    loadAdminStatistics();
    loadAdminTabData('users');
}

async function loadAdminStatistics() {
    try {
        // Load statistics
        const usersResponse = await apiRequest('/users');
        const booksResponse = await apiRequest('/books');
        const activeResponse = await apiRequest('/borrowing/active');
        const overdueResponse = await apiRequest('/borrowing/overdue');
        
        if (usersResponse.success) {
            document.getElementById('admin-total-users').textContent = usersResponse.data.length;
        }
        
        if (booksResponse.success) {
            document.getElementById('admin-total-books').textContent = booksResponse.data.length;
        }
        
        if (activeResponse.success) {
            document.getElementById('admin-active-borrowings').textContent = activeResponse.data.length;
        }
        
        if (overdueResponse.success) {
            document.getElementById('admin-overdue').textContent = overdueResponse.data.length;
        }
    } catch (error) {
        console.error('Error loading statistics:', error);
    }
}

function loadAdminTabData(tab) {
    switch(tab) {
        case 'users':
            loadAdminUsers();
            break;
        case 'books':
            loadAdminBooks();
            break;
        case 'borrowings':
            loadAllBorrowings();
            break;
        case 'overdue':
            loadOverdueBooks();
            break;
    }
}

// ===============================================
// User Management
// ===============================================

async function loadAdminUsers() {
    try {
        const response = await apiRequest('/users');
        if (response.success) {
            displayAdminUsers(response.data);
            // Update statistics
            document.getElementById('admin-total-users').textContent = response.data.length;
        } else {
            const tbody = document.getElementById('users-table-body');
            tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">Failed to load users</td></tr>';
        }
    } catch (error) {
        console.error('Error loading users:', error);
        const tbody = document.getElementById('users-table-body');
        tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">Error loading users. Please try again.</td></tr>';
        showToast('Failed to load users', 'error');
    }
}

function displayAdminUsers(users) {
    const tbody = document.getElementById('users-table-body');
    
    if (users.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">No users found</td></tr>';
        return;
    }
    
    tbody.innerHTML = users.map(user => `
        <tr>
            <td>${user.userId}</td>
            <td>${user.username}</td>
            <td>${user.email}</td>
            <td>${user.fullName}</td>
            <td><span class="role-${user.role.toLowerCase()}">${user.role}</span></td>
            <td><span class="status-${user.status.toLowerCase()}">${user.status}</span></td>
            <td>${new Date(user.registrationDate).toLocaleDateString()}</td>
            <td class="table-actions">
                <button class="btn btn-sm btn-outline" onclick="editUser(${user.userId})">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-sm btn-danger" onclick="deleteUser(${user.userId})" 
                    ${user.role === 'ADMIN' ? 'disabled title="Cannot delete admin"' : ''}>
                    <i class="fas fa-trash"></i> Delete
                </button>
            </td>
        </tr>
    `).join('');
}

// Edit User
async function editUser(userId) {
    try {
        const response = await apiRequest(`/users/${userId}`);
        if (response.success) {
            const user = response.data;
            document.getElementById('user-form-title').textContent = 'Edit User';
            document.getElementById('user-form-id').value = user.userId;
            document.getElementById('user-firstname').value = user.firstName;
            document.getElementById('user-lastname').value = user.lastName;
            document.getElementById('user-username').value = user.username;
            document.getElementById('user-email').value = user.email;
            document.getElementById('user-role').value = user.role;
            document.getElementById('user-status').value = user.status;
            document.getElementById('user-phone').value = user.phone || '';
            document.getElementById('user-address').value = user.address || '';
            document.getElementById('user-password-group').style.display = 'none';
            openModal('user-form-modal');
        }
    } catch (error) {
        showToast('Failed to load user details', 'error');
    }
}

// Delete User
async function deleteUser(userId) {
    if (!confirm('Are you sure you want to delete this user?')) {
        return;
    }
    
    try {
        const response = await apiRequest(`/users/${userId}`, {
            method: 'DELETE'
        });
        
        if (response.success) {
            showToast('User deleted successfully!');
            loadAdminUsers();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to delete user', 'error');
    }
}

// Add User Button
document.getElementById('add-user-btn').addEventListener('click', () => {
    document.getElementById('user-form-title').textContent = 'Add New User';
    document.getElementById('user-form').reset();
    document.getElementById('user-form-id').value = '';
    document.getElementById('user-password-group').style.display = 'block';
    openModal('user-form-modal');
});

// User Form Submit
document.getElementById('user-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const userId = document.getElementById('user-form-id').value;
    const userData = {
        firstName: document.getElementById('user-firstname').value,
        lastName: document.getElementById('user-lastname').value,
        username: document.getElementById('user-username').value,
        email: document.getElementById('user-email').value,
        role: document.getElementById('user-role').value,
        status: document.getElementById('user-status').value,
        phone: document.getElementById('user-phone').value,
        address: document.getElementById('user-address').value
    };
    
    // Add password only if creating new user
    if (!userId) {
        userData.password = document.getElementById('user-password').value;
    }
    
    try {
        let response;
        if (userId) {
            // Update existing user
            response = await apiRequest(`/users/${userId}`, {
                method: 'PUT',
                body: JSON.stringify(userData)
            });
        } else {
            // Create new user via registration
            response = await apiRequest('/auth/register', {
                method: 'POST',
                body: JSON.stringify(userData)
            });
        }
        
        if (response.success) {
            showToast(userId ? 'User updated successfully!' : 'User created successfully!');
            closeModal('user-form-modal');
            loadAdminUsers();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to save user', 'error');
    }
});

// ===============================================
// Book Management
// ===============================================

async function loadAdminBooks() {
    try {
        const response = await apiRequest('/books');
        if (response.success) {
            displayAdminBooks(response.data);
        }
    } catch (error) {
        console.error('Error loading books:', error);
        showToast('Failed to load books', 'error');
    }
}

function displayAdminBooks(books) {
    const tbody = document.getElementById('books-table-body');
    
    if (books.length === 0) {
        tbody.innerHTML = '<tr><td colspan="8" style="text-align: center;">No books found</td></tr>';
        return;
    }
    
    tbody.innerHTML = books.map(book => `
        <tr>
            <td>${book.bookId}</td>
            <td>${book.isbn}</td>
            <td>${book.title}</td>
            <td>${book.authors && book.authors.length > 0 ? 
                book.authors.map(a => a.authorName).join(', ') : 'N/A'}</td>
            <td>${book.category ? book.category.categoryName : 'N/A'}</td>
            <td>${book.availableCopies}/${book.totalCopies}</td>
            <td><span class="badge badge-success">${book.status}</span></td>
            <td class="table-actions">
                <button class="btn btn-sm btn-outline" onclick="editBook(${book.bookId})">
                    <i class="fas fa-edit"></i> Edit
                </button>
                <button class="btn btn-sm btn-danger" onclick="deleteBook(${book.bookId})">
                    <i class="fas fa-trash"></i> Delete
                </button>
                ${currentUser && (currentUser.role === 'INSTRUCTOR' || currentUser.role === 'ADMIN') ? `
                <button class="btn btn-sm btn-primary" onclick="showIssueBookModal(${book.bookId}, '${book.title.replace(/'/g, "\\'")}')">
                    <i class="fas fa-hand-holding"></i> Issue
                </button>
                ` : ''}
            </td>
        </tr>
    `).join('');
}

// Add Book Button
document.getElementById('add-book-btn').addEventListener('click', () => {
    document.getElementById('book-form-title').textContent = 'Add New Book';
    document.getElementById('book-form').reset();
    document.getElementById('book-form-id').value = '';
    openModal('book-form-modal');
});

// Book Form Submit
document.getElementById('book-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const bookData = {
        isbn: document.getElementById('book-isbn').value,
        title: document.getElementById('book-title').value,
        subtitle: document.getElementById('book-subtitle').value,
        publisher: document.getElementById('book-publisher').value,
        language: document.getElementById('book-language').value,
        pages: parseInt(document.getElementById('book-pages').value) || null,
        totalCopies: parseInt(document.getElementById('book-total-copies').value),
        description: document.getElementById('book-description').value
    };
    
    const bookId = document.getElementById('book-form-id').value;
    
    try {
        const url = bookId ? `/books/${bookId}` : '/books';
        const method = bookId ? 'PUT' : 'POST';
        
        const response = await apiRequest(url, {
            method: method,
            body: JSON.stringify(bookData)
        });
        
        if (response.success) {
            showToast(bookId ? 'Book updated successfully!' : 'Book created successfully!');
            closeModal('book-form-modal');
            loadAdminBooks();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to save book', 'error');
    }
});

// Edit Book
async function editBook(bookId) {
    try {
        const response = await apiRequest(`/books/${bookId}`);
        if (response.success) {
            const book = response.data;
            document.getElementById('book-form-title').textContent = 'Edit Book';
            document.getElementById('book-form-id').value = book.bookId;
            document.getElementById('book-isbn').value = book.isbn;
            document.getElementById('book-title').value = book.title;
            document.getElementById('book-subtitle').value = book.subtitle || '';
            document.getElementById('book-publisher').value = book.publisher || '';
            document.getElementById('book-language').value = book.language;
            document.getElementById('book-pages').value = book.pages || '';
            document.getElementById('book-total-copies').value = book.totalCopies;
            document.getElementById('book-description').value = book.description || '';
            openModal('book-form-modal');
        }
    } catch (error) {
        showToast('Failed to load book details', 'error');
    }
}

// Delete Book
async function deleteBook(bookId) {
    if (!confirm('Are you sure you want to delete this book?')) {
        return;
    }
    
    try {
        const response = await apiRequest(`/books/${bookId}`, {
            method: 'DELETE'
        });
        
        if (response.success) {
            showToast('Book deleted successfully!');
            loadAdminBooks();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to delete book', 'error');
    }
}

// Admin Book Search
let adminBookSearchTimeout;
document.getElementById('admin-book-search').addEventListener('input', (e) => {
    clearTimeout(adminBookSearchTimeout);
    adminBookSearchTimeout = setTimeout(async () => {
        const query = e.target.value;
        if (query) {
            try {
                const response = await apiRequest(`/books/search?query=${encodeURIComponent(query)}`);
                if (response.success) {
                    displayAdminBooks(response.data);
                }
            } catch (error) {
                console.error('Error searching books:', error);
            }
        } else {
            loadAdminBooks();
        }
    }, 500);
});

// ===============================================
// Borrowing Management
// ===============================================

async function loadAllBorrowings() {
    try {
        const response = await apiRequest('/borrowing/all');
        if (response.success) {
            displayAllBorrowings(response.data);
        }
    } catch (error) {
        console.error('Error loading borrowings:', error);
        const tbody = document.getElementById('borrowings-table-body');
        tbody.innerHTML = '<tr><td colspan="9" style="text-align: center;">Error loading borrowings</td></tr>';
        showToast('Failed to load borrowings', 'error');
    }
}

function displayAllBorrowings(records) {
    const tbody = document.getElementById('borrowings-table-body');
    
    if (records.length === 0) {
        tbody.innerHTML = '<tr><td colspan="9" style="text-align: center;">No borrowing records found</td></tr>';
        return;
    }
    
    tbody.innerHTML = records.map(record => `
        <tr>
            <td>${record.recordId}</td>
            <td>${record.user.fullName} (${record.user.username})</td>
            <td>${record.book.title}</td>
            <td>${new Date(record.borrowDate).toLocaleDateString()}</td>
            <td>${new Date(record.dueDate).toLocaleDateString()}</td>
            <td>${record.returnDate ? new Date(record.returnDate).toLocaleDateString() : 'Not returned'}</td>
            <td><span class="badge badge-${getStatusBadgeClass(record.status)}">${record.status}</span></td>
            <td>${record.fineAmount > 0 ? '$' + record.fineAmount : '-'}</td>
            <td class="table-actions">
                ${record.status === 'BORROWED' || record.status === 'OVERDUE' ? `
                <button class="btn btn-sm btn-success" onclick="processReturn(${record.recordId})">
                    <i class="fas fa-check"></i> Return
                </button>
                ` : '-'}
            </td>
        </tr>
    `).join('');
}

function getStatusBadgeClass(status) {
    switch(status) {
        case 'BORROWED': return 'info';
        case 'RETURNED': return 'success';
        case 'OVERDUE': return 'danger';
        case 'LOST': return 'warning';
        default: return 'info';
    }
}

// ===============================================
// Overdue Books Management
// ===============================================

async function loadOverdueBooks() {
    try {
        const response = await apiRequest('/borrowing/overdue');
        if (response.success) {
            displayOverdueBooks(response.data);
        }
    } catch (error) {
        console.error('Error loading overdue books:', error);
        showToast('Failed to load overdue books', 'error');
    }
}

function displayOverdueBooks(records) {
    const tbody = document.getElementById('overdue-table-body');
    
    if (records.length === 0) {
        tbody.innerHTML = '<tr><td colspan="7" style="text-align: center;">No overdue books</td></tr>';
        return;
    }
    
    tbody.innerHTML = records.map(record => `
        <tr>
            <td>${record.user.fullName} (${record.user.username})</td>
            <td>${record.book.title}</td>
            <td>${new Date(record.dueDate).toLocaleDateString()}</td>
            <td><span class="badge badge-danger">${record.daysOverdue} days</span></td>
            <td>$${(record.daysOverdue * 1.00).toFixed(2)}</td>
            <td>${record.user.email || 'N/A'}</td>
            <td class="table-actions">
                <button class="btn btn-sm btn-success" onclick="processReturn(${record.recordId})">
                    <i class="fas fa-check"></i> Process Return
                </button>
            </td>
        </tr>
    `).join('');
}

async function processReturn(recordId) {
    if (!confirm('Process book return? Fine will be calculated automatically.')) {
        return;
    }
    
    try {
        const response = await apiRequest(`/borrowing/return/${recordId}`, {
            method: 'POST',
            body: JSON.stringify({ returnedToId: currentUser.userId })
        });
        
        if (response.success) {
            const fine = response.fine || 0;
            if (fine > 0) {
                showToast(`Book returned. Fine: $${fine}`, 'warning');
            } else {
                showToast('Book returned successfully!');
            }
            loadOverdueBooks();
            loadAdminStatistics();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to process return', 'error');
    }
}

// ===============================================
// Issue Book (Instructor Feature)
// ===============================================

function showIssueBookModal(bookId, bookTitle) {
    document.getElementById('issue-book-id').value = bookId;
    document.getElementById('issue-book-info').textContent = bookTitle;
    document.getElementById('issue-book-form').reset();
    document.getElementById('issue-user-id').value = '';
    document.getElementById('user-search-results').innerHTML = '';
    openModal('issue-book-modal');
}

// User search for issuing books
let userSearchTimeout;
document.getElementById('issue-user-search').addEventListener('input', (e) => {
    clearTimeout(userSearchTimeout);
    const query = e.target.value;
    
    if (query.length < 2) {
        document.getElementById('user-search-results').innerHTML = '';
        return;
    }
    
    userSearchTimeout = setTimeout(() => {
        // In a real implementation, this would call a user search API
        document.getElementById('user-search-results').innerHTML = 
            '<p style="color: #6b7280; font-size: 0.875rem;">User search requires additional backend API</p>';
    }, 500);
});

// Issue Book Form
document.getElementById('issue-book-form').addEventListener('submit', async (e) => {
    e.preventDefault();
    
    const userId = document.getElementById('issue-user-id').value;
    const bookId = document.getElementById('issue-book-id').value;
    const days = document.getElementById('issue-days').value;
    
    if (!userId) {
        showToast('Please select a user', 'warning');
        return;
    }
    
    try {
        const response = await apiRequest('/borrowing/borrow', {
            method: 'POST',
            body: JSON.stringify({
                userId: parseInt(userId),
                bookId: parseInt(bookId),
                issuedById: currentUser.userId,
                days: parseInt(days)
            })
        });
        
        if (response.success) {
            showToast('Book issued successfully!');
            closeModal('issue-book-modal');
            loadAdminBooks();
        } else {
            showToast(response.message, 'error');
        }
    } catch (error) {
        showToast('Failed to issue book', 'error');
    }
});

// Make functions globally available
window.showBookDetail = showBookDetail;
window.borrowBook = borrowBook;
window.returnBook = returnBook;
window.editBook = editBook;
window.deleteBook = deleteBook;
window.editUser = editUser;
window.deleteUser = deleteUser;
window.processReturn = processReturn;
window.showIssueBookModal = showIssueBookModal;
window.loadAdminTabData = loadAdminTabData;
window.loadOverdueBooks = loadOverdueBooks;

