package com.thecodealchemist.entity;

import jakarta.persistence.*;

@Entity
public class Major {
    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long majorId;

    @ManyToOne
    @JoinColumn(name = "deptId")
    private Department department;

    @ManyToOne
    @JoinColumn(name = "userId")
    private User user;

    @Enumerated(EnumType.STRING)
    private MajorType type;

    // Getters and Setters

    public Long getMajorId() {
        return majorId;
    }

    public void setMajorId(Long majorId) {
        this.majorId = majorId;
    }

    public Department getDepartment() {
        return department;
    }

    public void setDepartment(Department department) {
        this.department = department;
    }

    public User getUser() {
        return user;
    }

    public void setUser(User user) {
        this.user = user;
    }

    public MajorType getType() {
        return type;
    }

    public void setType(MajorType type) {
        this.type = type;
    }
}
