package com.learnhub.app.model;

import jakarta.persistence.*;
import java.util.ArrayList;
import java.util.List;

@Entity
@Table(name = "courses")
public class Course {

    @Id
    @GeneratedValue(strategy = GenerationType.IDENTITY)
    private Long id;

    @Column(name = "course_code", nullable = false, unique = true)
    private String courseCode;

    @Column(nullable = false)
    private String title;

    @Column(name = "course_description", length = 2000)
    private String description;

    @Column(nullable = false)
    private String instructor;

    @Column(nullable = false)
    private String category;

    @Column(name = "sub_category")
    private String subCategory;

    @Column(nullable = false)
    private double price;

    @Column(name = "original_price")
    private double originalPrice;

    @Column(name = "credit_hours", nullable = false)
    private int creditHours;

    @Column(name = "duration_hours", nullable = false)
    private int durationHours;

    @Column(name = "total_lectures")
    private int totalLectures;

    @Column(name = "difficulty_level", nullable = false)
    private String difficultyLevel;

    @Column(name = "drop_deadline_days", nullable = false)
    private int dropDeadlineDays;

    @Column(name = "rating_score")
    private double rating = 4.5;

    @Column(name = "total_ratings")
    private int totalRatings = 0;

    @Column(name = "thumbnail_color")
    private String thumbnailColor = "#6366f1";

    @Column(name = "is_bestseller")
    private boolean bestseller = false;

    @Column(name = "is_active")
    private boolean active = true;

    @Column(name = "what_you_learn", length = 2000)
    private String whatYouLearn;

    @OneToMany(mappedBy = "course", cascade = CascadeType.ALL, fetch = FetchType.LAZY)
    private List<Enrollment> enrollments = new ArrayList<>();

    public Course() {}

    public Long getId() { return id; }
    public String getCourseCode() { return courseCode; }
    public String getTitle() { return title; }
    public String getDescription() { return description; }
    public String getInstructor() { return instructor; }
    public String getCategory() { return category; }
    public String getSubCategory() { return subCategory; }
    public double getPrice() { return price; }
    public double getOriginalPrice() { return originalPrice; }
    public int getCreditHours() { return creditHours; }
    public int getDurationHours() { return durationHours; }
    public int getTotalLectures() { return totalLectures; }
    public String getDifficultyLevel() { return difficultyLevel; }
    public int getDropDeadlineDays() { return dropDeadlineDays; }
    public double getRating() { return rating; }
    public int getTotalRatings() { return totalRatings; }
    public String getThumbnailColor() { return thumbnailColor; }
    public boolean isBestseller() { return bestseller; }
    public boolean isActive() { return active; }
    public String getWhatYouLearn() { return whatYouLearn; }
    public List<Enrollment> getEnrollments() { return enrollments; }

    public int getEnrolledCount() {
        if (enrollments == null) return 0;
        return (int) enrollments.stream()
                .filter(e -> e.getEnrollmentStatus() == Enrollment.EnrollmentStatus.ACTIVE)
                .count();
    }

    public int getDiscountPercent() {
        if (originalPrice <= 0 || price >= originalPrice) return 0;
        return (int) Math.round((1 - price / originalPrice) * 100);
    }

    public void setId(Long v) { this.id = v; }
    public void setCourseCode(String v) { this.courseCode = v; }
    public void setTitle(String v) { this.title = v; }
    public void setDescription(String v) { this.description = v; }
    public void setInstructor(String v) { this.instructor = v; }
    public void setCategory(String v) { this.category = v; }
    public void setSubCategory(String v) { this.subCategory = v; }
    public void setPrice(double v) { this.price = v; }
    public void setOriginalPrice(double v) { this.originalPrice = v; }
    public void setCreditHours(int v) { this.creditHours = v; }
    public void setDurationHours(int v) { this.durationHours = v; }
    public void setTotalLectures(int v) { this.totalLectures = v; }
    public void setDifficultyLevel(String v) { this.difficultyLevel = v; }
    public void setDropDeadlineDays(int v) { this.dropDeadlineDays = v; }
    public void setRating(double v) { this.rating = v; }
    public void setTotalRatings(int v) { this.totalRatings = v; }
    public void setThumbnailColor(String v) { this.thumbnailColor = v; }
    public void setBestseller(boolean v) { this.bestseller = v; }
    public void setActive(boolean v) { this.active = v; }
    public void setWhatYouLearn(String v) { this.whatYouLearn = v; }
    public void setEnrollments(List<Enrollment> v) { this.enrollments = v; }

    public static Builder builder() { return new Builder(); }
    public static class Builder {
        private String courseCode, title, description, instructor, category, subCategory;
        private String difficultyLevel, thumbnailColor = "#6366f1", whatYouLearn;
        private double price, originalPrice, rating = 4.5;
        private int creditHours, durationHours, totalLectures, dropDeadlineDays, totalRatings;
        private boolean bestseller = false;

        public Builder courseCode(String v) { courseCode = v; return this; }
        public Builder title(String v) { title = v; return this; }
        public Builder description(String v) { description = v; return this; }
        public Builder instructor(String v) { instructor = v; return this; }
        public Builder category(String v) { category = v; return this; }
        public Builder subCategory(String v) { subCategory = v; return this; }
        public Builder price(double v) { price = v; return this; }
        public Builder originalPrice(double v) { originalPrice = v; return this; }
        public Builder creditHours(int v) { creditHours = v; return this; }
        public Builder durationHours(int v) { durationHours = v; return this; }
        public Builder totalLectures(int v) { totalLectures = v; return this; }
        public Builder difficultyLevel(String v) { difficultyLevel = v; return this; }
        public Builder dropDeadlineDays(int v) { dropDeadlineDays = v; return this; }
        public Builder rating(double v) { rating = v; return this; }
        public Builder totalRatings(int v) { totalRatings = v; return this; }
        public Builder thumbnailColor(String v) { thumbnailColor = v; return this; }
        public Builder bestseller(boolean v) { bestseller = v; return this; }
        public Builder whatYouLearn(String v) { whatYouLearn = v; return this; }

        public Course build() {
            Course c = new Course();
            c.courseCode = courseCode; c.title = title; c.description = description;
            c.instructor = instructor; c.category = category; c.subCategory = subCategory;
            c.price = price; c.originalPrice = originalPrice; c.creditHours = creditHours;
            c.durationHours = durationHours; c.totalLectures = totalLectures;
            c.difficultyLevel = difficultyLevel; c.dropDeadlineDays = dropDeadlineDays;
            c.rating = rating; c.totalRatings = totalRatings; c.thumbnailColor = thumbnailColor;
            c.bestseller = bestseller; c.whatYouLearn = whatYouLearn;
            return c;
        }
    }
}
