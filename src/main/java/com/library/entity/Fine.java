package com.library.entity;

import jakarta.persistence.*;
import java.math.BigDecimal;
import java.time.LocalDateTime;

@Entity
@Table(name = "fines")
public class Fine {
    
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    @Column(name = "fine_id")
    private Long fineId;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "record_id", nullable = false)
    private BorrowingRecord borrowingRecord;
    
    @ManyToOne(fetch = FetchType.LAZY)
    @JoinColumn(name = "user_id", nullable = false)
    private User user;
    
    @Column(name = "fine_amount", nullable = false, precision = 10, scale = 2)
    private BigDecimal fineAmount;
    
    @Column(name = "fine_reason", length = 255)
    private String fineReason;
    
    @Column(name = "fine_date")
    private LocalDateTime fineDate;
    
    @Column(name = "payment_date")
    private LocalDateTime paymentDate;
    
    @Enumerated(EnumType.STRING)
    @Column(nullable = false)
    private FineStatus status = FineStatus.PENDING;
    
    @Column(name = "payment_method", length = 50)
    private String paymentMethod;
    
    @Column(name = "created_at")
    private LocalDateTime createdAt;
    
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    
    // Enums
    public enum FineStatus {
        PENDING, PAID, WAIVED
    }
    
    // Constructors
    public Fine() {
    }
    
    public Fine(BorrowingRecord borrowingRecord, User user, BigDecimal fineAmount, String fineReason) {
        this.borrowingRecord = borrowingRecord;
        this.user = user;
        this.fineAmount = fineAmount;
        this.fineReason = fineReason;
        this.fineDate = LocalDateTime.now();
    }
    
    // Lifecycle callbacks
    @PrePersist
    protected void onCreate() {
        createdAt = LocalDateTime.now();
        updatedAt = LocalDateTime.now();
        if (fineDate == null) {
            fineDate = LocalDateTime.now();
        }
    }
    
    @PreUpdate
    protected void onUpdate() {
        updatedAt = LocalDateTime.now();
    }
    
    // Business methods
    public void markAsPaid(String paymentMethod) {
        this.status = FineStatus.PAID;
        this.paymentDate = LocalDateTime.now();
        this.paymentMethod = paymentMethod;
    }
    
    public void waive() {
        this.status = FineStatus.WAIVED;
    }
    
    public boolean isPending() {
        return status == FineStatus.PENDING;
    }
    
    // Getters and Setters
    public Long getFineId() {
        return fineId;
    }
    
    public void setFineId(Long fineId) {
        this.fineId = fineId;
    }
    
    public BorrowingRecord getBorrowingRecord() {
        return borrowingRecord;
    }
    
    public void setBorrowingRecord(BorrowingRecord borrowingRecord) {
        this.borrowingRecord = borrowingRecord;
    }
    
    public User getUser() {
        return user;
    }
    
    public void setUser(User user) {
        this.user = user;
    }
    
    public BigDecimal getFineAmount() {
        return fineAmount;
    }
    
    public void setFineAmount(BigDecimal fineAmount) {
        this.fineAmount = fineAmount;
    }
    
    public String getFineReason() {
        return fineReason;
    }
    
    public void setFineReason(String fineReason) {
        this.fineReason = fineReason;
    }
    
    public LocalDateTime getFineDate() {
        return fineDate;
    }
    
    public void setFineDate(LocalDateTime fineDate) {
        this.fineDate = fineDate;
    }
    
    public LocalDateTime getPaymentDate() {
        return paymentDate;
    }
    
    public void setPaymentDate(LocalDateTime paymentDate) {
        this.paymentDate = paymentDate;
    }
    
    public FineStatus getStatus() {
        return status;
    }
    
    public void setStatus(FineStatus status) {
        this.status = status;
    }
    
    public String getPaymentMethod() {
        return paymentMethod;
    }
    
    public void setPaymentMethod(String paymentMethod) {
        this.paymentMethod = paymentMethod;
    }
    
    public LocalDateTime getCreatedAt() {
        return createdAt;
    }
    
    public void setCreatedAt(LocalDateTime createdAt) {
        this.createdAt = createdAt;
    }
    
    public LocalDateTime getUpdatedAt() {
        return updatedAt;
    }
    
    public void setUpdatedAt(LocalDateTime updatedAt) {
        this.updatedAt = updatedAt;
    }
    
    @Override
    public String toString() {
        return "Fine{" +
                "fineId=" + fineId +
                ", fineAmount=" + fineAmount +
                ", fineReason='" + fineReason + '\'' +
                ", status=" + status +
                ", fineDate=" + fineDate +
                '}';
    }
}

