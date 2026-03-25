package com.learnhub.app.repository;
import com.learnhub.app.model.Course;
import com.learnhub.app.model.Enrollment;
import com.learnhub.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
import java.util.Optional;
public interface EnrollmentRepository extends JpaRepository<Enrollment, Long> {
    List<Enrollment> findByStudentOrderByEnrolledAtDesc(Student student);
    Optional<Enrollment> findByStudentAndCourse(Student student, Course course);
    boolean existsByStudentAndCourse(Student student, Course course);
    List<Enrollment> findByCourse(Course course);
    @Query("SELECT COALESCE(SUM(e.course.creditHours), 0) FROM Enrollment e WHERE e.student = :student AND e.semesterLabel = :semester AND e.enrollmentStatus = 'ACTIVE'")
    int getTotalCredits(Student student, String semester);
}
