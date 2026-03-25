package com.learnhub.app.service;

import com.learnhub.app.exception.BusinessException;
import com.learnhub.app.model.*;
import com.learnhub.app.repository.*;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.util.*;
import java.util.stream.Collectors;

@Service
@Transactional
public class LearnHubService {

    private static final String CURRENT_SEMESTER = "Spring 2025";

    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;
    private final EnrollmentRepository enrollRepo;

    public LearnHubService(CourseRepository courseRepo, StudentRepository studentRepo,
                           EnrollmentRepository enrollRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
        this.enrollRepo = enrollRepo;
    }

    // ---- COURSES ----
    @Transactional(readOnly = true)
    public List<Course> getAllCourses() { return courseRepo.findByActiveTrue(); }

    @Transactional(readOnly = true)
    public List<Course> getCoursesByCategory(String category) {
        if (category == null || category.isBlank()) return courseRepo.findByActiveTrue();
        return courseRepo.findByCategoryAndActiveTrue(category);
    }

    @Transactional(readOnly = true)
    public List<Course> searchCourses(String q) {
        if (q == null || q.isBlank()) return courseRepo.findByActiveTrue();
        return courseRepo.searchCourses(q.trim());
    }

    @Transactional(readOnly = true)
    public List<String> getCategories() { return courseRepo.findAllCategories(); }

    @Transactional(readOnly = true)
    public List<Course> getBestsellers() { return courseRepo.findByBestsellerTrueAndActiveTrue(); }

    @Transactional(readOnly = true)
    public Course getCourse(Long id) {
        return courseRepo.findById(id).orElseThrow(() -> new BusinessException("Course not found"));
    }

    // ---- STUDENTS ----
    @Transactional(readOnly = true)
    public List<Student> getAllStudents() { return studentRepo.findAll(); }

    @Transactional(readOnly = true)
    public Student getStudent(String code) {
        return studentRepo.findByStudentCode(code)
                .orElseThrow(() -> new BusinessException("Student not found: " + code));
    }

    // ---- ENROLL ----
    public Enrollment enroll(String studentCode, Long courseId) {
        Student student = studentRepo.findByStudentCode(studentCode)
                .orElseThrow(() -> new BusinessException("Student not found"));
        Course course = courseRepo.findById(courseId)
                .orElseThrow(() -> new BusinessException("Course not found"));

        if (!course.isActive())
            throw new BusinessException("This course is not currently available.");

        if (enrollRepo.existsByStudentAndCourse(student, course))
            throw new BusinessException("You are already enrolled in \"" + course.getTitle() + "\".");

        // Credit limit check
        int usedCredits = enrollRepo.getTotalCredits(student, CURRENT_SEMESTER);
        if (usedCredits + course.getCreditHours() > student.getMaxCredits()) {
            throw new BusinessException(
                "Credit limit exceeded! You have " + usedCredits + "/" + student.getMaxCredits() +
                " credits used. This course needs " + course.getCreditHours() + " more credits.");
        }

        LocalDate dropDeadline = LocalDate.now().plusDays(course.getDropDeadlineDays());

        Enrollment e = Enrollment.builder()
                .student(student).course(course)
                .enrolledAt(LocalDateTime.now())
                .dropDeadline(dropDeadline)
                .amountPaid(course.getPrice())
                .semesterLabel(CURRENT_SEMESTER)
                .enrollmentStatus(Enrollment.EnrollmentStatus.ACTIVE)
                .build();

        return enrollRepo.save(e);
    }

    // ---- DROP ----
    public Enrollment drop(Long enrollmentId) {
        Enrollment e = enrollRepo.findById(enrollmentId)
                .orElseThrow(() -> new BusinessException("Enrollment not found"));

        if (e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.DROPPED)
            throw new BusinessException("Already dropped.");

        if (LocalDate.now().isAfter(e.getDropDeadline()))
            throw new BusinessException(
                "Drop deadline was " + e.getDropDeadline() + ". Cannot drop after deadline.");

        e.setEnrollmentStatus(Enrollment.EnrollmentStatus.DROPPED);
        e.setDroppedAt(LocalDateTime.now());
        return enrollRepo.save(e);
    }

    // ---- MY COURSES ----
    @Transactional(readOnly = true)
    public List<Enrollment> getMyEnrollments(String studentCode) {
        Student student = studentRepo.findByStudentCode(studentCode)
                .orElseThrow(() -> new BusinessException("Student not found"));
        return enrollRepo.findByStudentOrderByEnrolledAtDesc(student);
    }

    @Transactional(readOnly = true)
    public int getUsedCredits(String studentCode) {
        Student student = studentRepo.findByStudentCode(studentCode)
                .orElseThrow(() -> new BusinessException("Student not found"));
        return enrollRepo.getTotalCredits(student, CURRENT_SEMESTER);
    }

    @Transactional(readOnly = true)
    public Set<Long> getEnrolledCourseIds(String studentCode) {
        try {
            Student student = studentRepo.findByStudentCode(studentCode).orElse(null);
            if (student == null) return Set.of();
            return enrollRepo.findByStudentOrderByEnrolledAtDesc(student).stream()
                    .filter(e -> e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                    .map(e -> e.getCourse().getId())
                    .collect(Collectors.toSet());
        } catch (Exception ex) { return Set.of(); }
    }

    // ---- ADMIN ----
    @Transactional(readOnly = true)
    public List<Enrollment> getAllEnrollments() { return enrollRepo.findAll(); }

    @Transactional(readOnly = true)
    public List<Enrollment> getEnrollmentsForCourse(Long courseId) {
        Course c = courseRepo.findById(courseId).orElseThrow(() -> new BusinessException("Course not found"));
        return enrollRepo.findByCourse(c);
    }
}
