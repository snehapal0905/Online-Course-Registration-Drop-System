package com.learnhub.app.repository;
import com.learnhub.app.model.Student;
import org.springframework.data.jpa.repository.JpaRepository;
import java.util.Optional;
public interface StudentRepository extends JpaRepository<Student, Long> {
    Optional<Student> findByStudentCode(String studentCode);
}
