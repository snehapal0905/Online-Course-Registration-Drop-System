package com.learnhub.app.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "students")
public class Student {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "student_code", nullable = false, unique = true)
    private String studentCode;

    @Column(nullable = false)
    private String fullname;

    @Column(nullable = false, unique = true)
    private String email;

    @Column(name = "max_credits", nullable = false)
    private int maxCredits = 30;

    @OneToMany(mappedBy = "student", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Student() {}

    public Long getId() { return id; }
    public String getStudentCode() { return studentCode; }
    public String getFullname() { return fullname; }
    public String getEmail() { return email; }
    public int getMaxCredits() { return maxCredits; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    public void setId(Long v) { this.id = v; }
    public void setStudentCode(String v) { this.studentCode = v; }
    public void setFullname(String v) { this.fullname = v; }
    public void setEmail(String v) { this.email = v; }
    public void setMaxCredits(int v) { this.maxCredits = v; }
    public void setEnrollments(List<Enrollment> v) { this.enrollments = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String studentCode, fullname, email;
        private int maxCredits = 30;
        public Builder studentCode(String v) { studentCode = v; return this; }
        public Builder fullname(String v) { fullname = v; return this; }
        public Builder email(String v) { email = v; return this; }
        public Builder maxCredits(int v) { maxCredits = v; return this; }
        public Student build() {
            Student s = new Student();
            s.studentCode = studentCode; s.fullname = fullname;
            s.email = email; s.maxCredits = maxCredits;
            return s;
        }
    }
}
