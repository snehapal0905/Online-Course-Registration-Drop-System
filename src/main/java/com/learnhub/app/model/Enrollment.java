package com.learnhub.app.model;

import jakarta.persistence.*;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.temporal.ChronoUnit;

@Entity
@Table(name = "enrollments",
    uniqueConstraints = @UniqueConstraint(columnNames = {"student_id", "course_id"}))
public class Enrollment {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "student_id", nullable = false)
    private Student student;

    @ManyToOne(fetch = FetchType.EAGER)
    @JoinColumn(name = "course_id", nullable = false)
    private Course course;

    @Column(name = "enrolled_at", nullable = false)
    private LocalDateTime enrolledAt;

    @Column(name = "drop_deadline", nullable = false)
    private LocalDate dropDeadline;

    @Column(name = "amount_paid", nullable = false)
    private double amountPaid;

    @Column(name = "dropped_at")
    private LocalDateTime droppedAt;

    @Column(name = "semester_label")
    private String semesterLabel;

    @Enumerated(EnumType.STRING)
    @Column(name = "enrollment_status", nullable = false)
    private EnrollmentStatus enrollmentStatus = EnrollmentStatus.ACTIVE;

    public enum EnrollmentStatus { ACTIVE, DROPPED, COMPLETED }

    public Enrollment() {}

    // --- helpers ---
    public boolean isDroppable() {
        return enrollmentStatus == EnrollmentStatus.ACTIVE
                && !LocalDate.now().isAfter(dropDeadline);
    }

    public long getDaysUntilDeadline() {
        return ChronoUnit.DAYS.between(LocalDate.now(), dropDeadline);
    }

    // --- getters ---
    public Long getId() { return id; }
    public Student getStudent() { return student; }
    public Course getCourse() { return course; }
    public LocalDateTime getEnrolledAt() { return enrolledAt; }
    public LocalDate getDropDeadline() { return dropDeadline; }
    public double getAmountPaid() { return amountPaid; }
    public LocalDateTime getDroppedAt() { return droppedAt; }
    public String getSemesterLabel() { return semesterLabel; }
    public EnrollmentStatus getEnrollmentStatus() { return enrollmentStatus; }

    // --- setters ---
    public void setId(Long v) { id = v; }
    public void setStudent(Student v) { student = v; }
    public void setCourse(Course v) { course = v; }
    public void setEnrolledAt(LocalDateTime v) { enrolledAt = v; }
    public void setDropDeadline(LocalDate v) { dropDeadline = v; }
    public void setAmountPaid(double v) { amountPaid = v; }
    public void setDroppedAt(LocalDateTime v) { droppedAt = v; }
    public void setSemesterLabel(String v) { semesterLabel = v; }
    public void setEnrollmentStatus(EnrollmentStatus v) { enrollmentStatus = v; }

    // builder
    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private Student student; private Course course;
        private LocalDateTime enrolledAt; private LocalDate dropDeadline;
        private double amountPaid; private String semesterLabel;
        private EnrollmentStatus enrollmentStatus = EnrollmentStatus.ACTIVE;

        public Builder student(Student v) { student = v; return this; }
        public Builder course(Course v) { course = v; return this; }
        public Builder enrolledAt(LocalDateTime v) { enrolledAt = v; return this; }
        public Builder dropDeadline(LocalDate v) { dropDeadline = v; return this; }
        public Builder amountPaid(double v) { amountPaid = v; return this; }
        public Builder semesterLabel(String v) { semesterLabel = v; return this; }
        public Builder enrollmentStatus(EnrollmentStatus v) { enrollmentStatus = v; return this; }
        public Enrollment build() {
            Enrollment e = new Enrollment();
            e.student = student; e.course = course; e.enrolledAt = enrolledAt;
            e.dropDeadline = dropDeadline; e.amountPaid = amountPaid;
            e.semesterLabel = semesterLabel; e.enrollmentStatus = enrollmentStatus;
            return e;
        }
    }
}
