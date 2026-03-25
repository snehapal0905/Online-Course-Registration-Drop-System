package com.learnhub.app.repository;
import com.learnhub.app.model.Course;
import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import java.util.List;
public interface CourseRepository extends JpaRepository<Course, Long> {
    List<Course> findByActiveTrue();
    List<Course> findByCategoryAndActiveTrue(String category);
    @Query("SELECT DISTINCT c.category FROM Course c WHERE c.active = true ORDER BY c.category")
    List<String> findAllCategories();
    List<Course> findByBestsellerTrueAndActiveTrue();
    @Query("SELECT c FROM Course c WHERE c.active = true AND (LOWER(c.title) LIKE LOWER(CONCAT('%',:q,'%')) OR LOWER(c.instructor) LIKE LOWER(CONCAT('%',:q,'%')) OR LOWER(c.category) LIKE LOWER(CONCAT('%',:q,'%')))")
    List<Course> searchCourses(String q);
}
