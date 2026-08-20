package com.becksoft.bazzar.entity;

import com.becksoft.bazzar.enums.EmployeeDocument;
import jakarta.persistence.*;
import lombok.Getter;
import lombok.NoArgsConstructor;

import java.time.LocalDateTime;

@Getter
@NoArgsConstructor
@Entity
@Table(name = "employees",
        uniqueConstraints = {
                @UniqueConstraint(
                        name = "uk_employees_document_type_document_number",
                        columnNames = {"document_type", "document_number"}
                )
        }
)
public class Employee {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;
    @OneToOne(fetch = FetchType.LAZY, optional = false)
    @JoinColumn(name = "user_id", nullable = false, unique = true, updatable = false)
    private User user;
    @Column(nullable = false, length = 100)
    private String name;
    @Enumerated(EnumType.STRING)
    @Column(name = "document_type", nullable = false)
    private EmployeeDocument documentType;
    @Column(name = "document_number", length = 20, nullable = false)
    private String documentNumber;
    @Column(length = 20)
    private String phone;
    @Column(name = "created_at", nullable = false, updatable = false)
    private LocalDateTime createdAt;
    @Column(name = "updated_at")
    private LocalDateTime updatedAt;
    @Column(name = "deleted_at")
    private LocalDateTime deletedAt;

    public Employee(User user, String name, EmployeeDocument documentType, String documentNumber, String phone) {
        this.user = user;
        this.name = name;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
        this.phone = phone;
    }

    public void updateIdentification(String name, EmployeeDocument documentType, String documentNumber) {
        this.name = name;
        this.documentType = documentType;
        this.documentNumber = documentNumber;
    }

    public void updatePhone(String phone) {
        this.phone = phone;
    }

    @PrePersist
    protected void onCreate() {
        LocalDateTime now = LocalDateTime.now();
        this.createdAt = now;
        this.updatedAt = now;
    }

    @PreUpdate
    protected void onUpdate() {
        this.updatedAt = LocalDateTime.now();
    }

    public void delete() {
        this.deletedAt = LocalDateTime.now();
    }

    public void restore() {
        this.deletedAt = null;
    }

}
