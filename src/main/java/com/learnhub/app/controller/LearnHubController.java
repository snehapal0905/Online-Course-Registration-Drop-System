package com.learnhub.app.controller;

import com.learnhub.app.exception.BusinessException;
import com.learnhub.app.model.*;
import com.learnhub.app.service.LearnHubService;
import org.springframework.stereotype.Controller;
import org.springframework.ui.Model;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.servlet.mvc.support.RedirectAttributes;

import java.util.List;
import java.util.Set;

@Controller
public class LearnHubController {

    private final LearnHubService service;
    public LearnHubController(LearnHubService service) { this.service = service; }

    @GetMapping("/")
    public String home() { return "redirect:/courses"; }

    // ---- COURSES PAGE ----
    @GetMapping("/courses")
    public String courses(
            @RequestParam(defaultValue = "") String studentCode,
            @RequestParam(defaultValue = "") String category,
            @RequestParam(defaultValue = "") String search,
            Model model) {

        List<Course> courses;
        if (!search.isBlank()) courses = service.searchCourses(search);
        else if (!category.isBlank()) courses = service.getCoursesByCategory(category);
        else courses = service.getAllCourses();

        Set<Long> enrolledIds = Set.of();
        int usedCredits = 0;
        int maxCredits = 30;

        if (!studentCode.isBlank()) {
            enrolledIds = service.getEnrolledCourseIds(studentCode);
            try {
                Student s = service.getStudent(studentCode);
                usedCredits = service.getUsedCredits(studentCode);
                maxCredits = s.getMaxCredits();
                model.addAttribute("student", s);
            } catch (BusinessException ignored) {}
        }

        model.addAttribute("courses", courses);
        model.addAttribute("enrolledIds", enrolledIds);
        model.addAttribute("students", service.getAllStudents());
        model.addAttribute("categories", service.getCategories());
        model.addAttribute("bestsellers", service.getBestsellers());
        model.addAttribute("selectedStudentCode", studentCode);
        model.addAttribute("selectedCategory", category);
        model.addAttribute("search", search);
        model.addAttribute("usedCredits", usedCredits);
        model.addAttribute("maxCredits", maxCredits);
        return "courses";
    }

    // ---- ENROLL ----
    @PostMapping("/enroll")
    public String enroll(@RequestParam String studentCode, @RequestParam Long courseId,
                         @RequestParam(defaultValue = "") String returnTo, RedirectAttributes ra) {
        try {
            Enrollment e = service.enroll(studentCode, courseId);
            ra.addFlashAttribute("successMsg",
                "Enrolled in \"" + e.getCourse().getTitle() + "\"! Drop by: " + e.getDropDeadline());
        } catch (BusinessException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
        }
        String back = returnTo.isBlank() ? "/courses" : returnTo;
        return "redirect:" + back + "?studentCode=" + studentCode;
    }

    // ---- MY COURSES ----
    @GetMapping("/my-courses")
    public String myCourses(@RequestParam(defaultValue = "") String studentCode, Model model) {
        model.addAttribute("students", service.getAllStudents());
        model.addAttribute("selectedStudentCode", studentCode);
        model.addAttribute("usedCredits", 0);
        model.addAttribute("maxCredits", 30);
        model.addAttribute("search", "");
        model.addAttribute("selectedCategory", "");
        model.addAttribute("activeCount", 0L);
        model.addAttribute("droppedCount", 0L);

        if (!studentCode.isBlank()) {
            try {
                Student student = service.getStudent(studentCode);
                List<Enrollment> enrollments = service.getMyEnrollments(studentCode);
                int usedCredits = service.getUsedCredits(studentCode);

                long active  = enrollments.stream().filter(e -> e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.ACTIVE).count();
                long dropped = enrollments.stream().filter(e -> e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.DROPPED).count();

                model.addAttribute("student", student);
                model.addAttribute("enrollments", enrollments);
                model.addAttribute("usedCredits", usedCredits);
                model.addAttribute("activeCount", active);
                model.addAttribute("droppedCount", dropped);
            } catch (BusinessException ex) {
                model.addAttribute("errorMsg", ex.getMessage());
            }
        }
        return "my-courses";
    }

    // ---- DROP ----
    @PostMapping("/drop")
    public String drop(@RequestParam Long enrollmentId, @RequestParam String studentCode,
                       RedirectAttributes ra) {
        try {
            Enrollment e = service.drop(enrollmentId);
            ra.addFlashAttribute("successMsg", "Dropped \"" + e.getCourse().getTitle() + "\". Refund will be processed.");
        } catch (BusinessException ex) {
            ra.addFlashAttribute("errorMsg", ex.getMessage());
        }
        return "redirect:/my-courses?studentCode=" + studentCode;
    }

    // ---- ADMIN ----
    @GetMapping("/admin")
    public String admin(@RequestParam(defaultValue = "") String filterCourseId, Model model) {
        List<Enrollment> enrollments;
        if (!filterCourseId.isBlank()) {
            try { enrollments = service.getEnrollmentsForCourse(Long.parseLong(filterCourseId)); }
            catch (Exception e) { enrollments = service.getAllEnrollments(); }
        } else {
            enrollments = service.getAllEnrollments();
        }
        double revenue = enrollments.stream()
                .filter(e -> e.getEnrollmentStatus() != Enrollment.EnrollmentStatus.DROPPED)
                .mapToDouble(Enrollment::getAmountPaid).sum();

        model.addAttribute("enrollments", enrollments);
        model.addAttribute("allCourses", service.getAllCourses());
        model.addAttribute("allStudents", service.getAllStudents());
        model.addAttribute("filterCourseId", filterCourseId);
        model.addAttribute("totalRevenue", String.format("%.0f", revenue));
        model.addAttribute("totalEnrollments", enrollments.size());
        model.addAttribute("students", service.getAllStudents());
        model.addAttribute("selectedStudentCode", "");
        model.addAttribute("usedCredits", 0);
        model.addAttribute("maxCredits", 30);
        model.addAttribute("search", "");
        model.addAttribute("selectedCategory", "");
        return "admin";
    }
}
