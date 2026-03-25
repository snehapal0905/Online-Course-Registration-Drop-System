package com.learnhub.app;

import com.learnhub.app.model.Course;
import com.learnhub.app.model.Student;
import com.learnhub.app.repository.CourseRepository;
import com.learnhub.app.repository.StudentRepository;
import org.springframework.boot.CommandLineRunner;
import org.springframework.stereotype.Component;

import java.util.List;

@Component
public class DataLoader implements CommandLineRunner {

    private final CourseRepository courseRepo;
    private final StudentRepository studentRepo;

    public DataLoader(CourseRepository courseRepo, StudentRepository studentRepo) {
        this.courseRepo = courseRepo;
        this.studentRepo = studentRepo;
    }

    @Override
    public void run(String... args) {
        seedStudents();
        seedCourses();
    }

    private void seedStudents() {
        studentRepo.saveAll(List.of(
            Student.builder().studentCode("STU001").fullname("Alice Johnson").email("alice@learnhub.in").maxCredits(30).build(),
            Student.builder().studentCode("STU002").fullname("Bob Martinez").email("bob@learnhub.in").maxCredits(30).build(),
            Student.builder().studentCode("STU003").fullname("Carol White").email("carol@learnhub.in").maxCredits(25).build(),
            Student.builder().studentCode("STU004").fullname("David Lee").email("david@learnhub.in").maxCredits(35).build(),
            Student.builder().studentCode("STU005").fullname("Emma Davis").email("emma@learnhub.in").maxCredits(30).build()
        ));
    }

    private void seedCourses() {
        courseRepo.saveAll(List.of(

            // --- PROGRAMMING ---
            Course.builder().courseCode("PY101").title("Python Bootcamp: Zero to Hero")
                .description("Master Python programming from complete beginner to professional developer. Includes real projects, web scraping, automation, and data analysis.")
                .instructor("Dr. Angela Yu").category("Programming").subCategory("Python")
                .price(499).originalPrice(3499).creditHours(5)
                .durationHours(22).totalLectures(155).difficultyLevel("Beginner")
                .dropDeadlineDays(30).rating(4.7).totalRatings(342891)
                .thumbnailColor("#f59e0b").bestseller(true)
                .whatYouLearn("Python fundamentals|Web scraping with BeautifulSoup|Automation scripts|Data analysis with Pandas").build(),

            Course.builder().courseCode("JAVA201").title("Java Programming Masterclass")
                .description("Complete Java developer course covering OOP, collections, Spring Boot, multithreading and design patterns used in enterprise software.")
                .instructor("Tim Buchalka").category("Programming").subCategory("Java")
                .price(699).originalPrice(4499).creditHours(10)
                .durationHours(80).totalLectures(421).difficultyLevel("Intermediate")
                .dropDeadlineDays(60).rating(4.6).totalRatings(201345)
                .thumbnailColor("#ef4444").bestseller(true)
                .whatYouLearn("Core Java and OOP|Collections and Generics|Spring Boot REST APIs|Multithreading").build(),

            Course.builder().courseCode("JS301").title("The Complete JavaScript Course 2025")
                .description("From JavaScript basics to advanced concepts including ES6+, async/await, Node.js, Express and modern frontend frameworks.")
                .instructor("Jonas Schmedtmann").category("Programming").subCategory("JavaScript")
                .price(599).originalPrice(3999).creditHours(8)
                .durationHours(69).totalLectures(320).difficultyLevel("Beginner")
                .dropDeadlineDays(30).rating(4.8).totalRatings(178234)
                .thumbnailColor("#f97316").bestseller(true)
                .whatYouLearn("ES6+ Modern JavaScript|DOM manipulation|Async programming|Node.js basics").build(),

            Course.builder().courseCode("CPP101").title("C++ Programming: From Basics to Advanced")
                .description("Comprehensive C++ course covering pointers, memory management, STL, templates, and systems programming fundamentals.")
                .instructor("Prof. Brian Overland").category("Programming").subCategory("C++")
                .price(449).originalPrice(2999).creditHours(6)
                .durationHours(46).totalLectures(280).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.5).totalRatings(89234)
                .thumbnailColor("#8b5cf6")
                .whatYouLearn("Pointers and memory|OOP in C++|STL containers|File I/O").build(),

            // --- DATABASE ---
            Course.builder().courseCode("SQL101").title("SQL - MySQL for Data Analytics and BI")
                .description("Master SQL for databases, data analysis, and business intelligence. Learn joins, subqueries, stored procedures and performance tuning.")
                .instructor("365 Data Science").category("Database").subCategory("SQL")
                .price(349).originalPrice(2499).creditHours(5)
                .durationHours(21).totalLectures(142).difficultyLevel("Beginner")
                .dropDeadlineDays(30).rating(4.7).totalRatings(156789)
                .thumbnailColor("#06b6d4").bestseller(true)
                .whatYouLearn("SQL fundamentals|Complex JOINs|Stored procedures|Query optimization").build(),

            Course.builder().courseCode("MONGO201").title("MongoDB - The Complete Developers Guide")
                .description("Master MongoDB for modern applications. Covers CRUD, aggregation pipelines, indexes, replication and MongoDB Atlas cloud deployment.")
                .instructor("Maximilian Schwarzmuller").category("Database").subCategory("MongoDB")
                .price(449).originalPrice(2999).creditHours(6)
                .durationHours(17).totalLectures(158).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.6).totalRatings(67421)
                .thumbnailColor("#10b981")
                .whatYouLearn("CRUD operations|Aggregation framework|Indexes and performance|Atlas cloud").build(),

            Course.builder().courseCode("PSQL301").title("PostgreSQL Bootcamp")
                .description("Learn PostgreSQL for enterprise applications. Covers advanced data types, full-text search, JSON operations, and performance optimization.")
                .instructor("Luca Ferrari").category("Database").subCategory("PostgreSQL")
                .price(399).originalPrice(2799).creditHours(5)
                .durationHours(28).totalLectures(195).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.5).totalRatings(34512)
                .thumbnailColor("#3b82f6")
                .whatYouLearn("PostgreSQL advanced queries|JSON operations|Full-text search|Replication").build(),

            // --- DATA SCIENCE ---
            Course.builder().courseCode("ML401").title("Machine Learning A-Z")
                .description("Hands-on Python and R for machine learning. Supervised, unsupervised, reinforcement learning. Build 10+ real-world AI projects.")
                .instructor("Kirill Eremenko").category("Data Science").subCategory("Machine Learning")
                .price(799).originalPrice(5499).creditHours(12)
                .durationHours(44).totalLectures(291).difficultyLevel("Advanced")
                .dropDeadlineDays(90).rating(4.6).totalRatings(178923)
                .thumbnailColor("#ec4899").bestseller(true)
                .whatYouLearn("Supervised learning|Neural networks|NLP basics|Model evaluation").build(),

            Course.builder().courseCode("DS201").title("Data Science and Machine Learning Bootcamp")
                .description("Complete data science bootcamp with Python. Learn NumPy, Pandas, Matplotlib, Seaborn, Scikit-Learn and TensorFlow through 100+ exercises.")
                .instructor("Jose Portilla").category("Data Science").subCategory("Data Analysis")
                .price(599).originalPrice(3999).creditHours(8)
                .durationHours(25).totalLectures(165).difficultyLevel("Intermediate")
                .dropDeadlineDays(60).rating(4.6).totalRatings(124567)
                .thumbnailColor("#f59e0b")
                .whatYouLearn("Pandas and NumPy|Data visualization|Scikit-Learn|Capstone projects").build(),

            Course.builder().courseCode("DL501").title("Deep Learning Specialization")
                .description("From neural network basics to transformer architectures. Covers CNNs, RNNs, LSTMs, attention mechanisms, and modern LLM architectures.")
                .instructor("Andrew Ng").category("Data Science").subCategory("Deep Learning")
                .price(999).originalPrice(6999).creditHours(15)
                .durationHours(55).totalLectures(310).difficultyLevel("Advanced")
                .dropDeadlineDays(90).rating(4.9).totalRatings(234891)
                .thumbnailColor("#7c3aed").bestseller(true)
                .whatYouLearn("Neural networks from scratch|CNNs for vision|Sequence models|Transformers").build(),

            // --- WEB DEVELOPMENT ---
            Course.builder().courseCode("WD201").title("Full Stack Web Dev Bootcamp")
                .description("Become a full stack developer with HTML, CSS, JavaScript, React, Node.js, Express, MongoDB and PostgreSQL. Build 15 real-world projects.")
                .instructor("Colt Steele").category("Web Development").subCategory("Full Stack")
                .price(699).originalPrice(4999).creditHours(10)
                .durationHours(63).totalLectures(475).difficultyLevel("Beginner")
                .dropDeadlineDays(60).rating(4.7).totalRatings(289341)
                .thumbnailColor("#10b981").bestseller(true)
                .whatYouLearn("HTML5 and CSS3|React.js|Node.js and Express|MongoDB").build(),

            Course.builder().courseCode("REACT301").title("React - The Complete Guide 2025")
                .description("Deep dive into React including hooks, Redux Toolkit, React Router, Next.js, testing with Jest and deployment strategies.")
                .instructor("Maximilian Schwarzmuller").category("Web Development").subCategory("React")
                .price(599).originalPrice(3999).creditHours(8)
                .durationHours(68).totalLectures(692).difficultyLevel("Intermediate")
                .dropDeadlineDays(60).rating(4.8).totalRatings(198234)
                .thumbnailColor("#38bdf8").bestseller(true)
                .whatYouLearn("React hooks|Redux Toolkit|Next.js|Testing with Jest").build(),

            // --- CLOUD & DEVOPS ---
            Course.builder().courseCode("AWS301").title("AWS Certified Solutions Architect")
                .description("Pass the AWS SAA-C03 exam and master cloud architecture. Covers EC2, S3, RDS, Lambda, VPC, IAM, Route53, and CloudFormation.")
                .instructor("Stephane Maarek").category("Cloud").subCategory("AWS")
                .price(699).originalPrice(4999).creditHours(8)
                .durationHours(27).totalLectures(396).difficultyLevel("Intermediate")
                .dropDeadlineDays(60).rating(4.8).totalRatings(142356)
                .thumbnailColor("#f59e0b").bestseller(true)
                .whatYouLearn("EC2 and networking|S3 and storage|RDS databases|Serverless with Lambda").build(),

            Course.builder().courseCode("DOCKER401").title("Docker and Kubernetes: The Practical Guide")
                .description("Master containerization with Docker and orchestration with Kubernetes. Deploy microservices, set up CI/CD pipelines and manage clusters.")
                .instructor("Maximilian Schwarzmuller").category("Cloud").subCategory("DevOps")
                .price(599).originalPrice(3999).creditHours(7)
                .durationHours(23).totalLectures(274).difficultyLevel("Advanced")
                .dropDeadlineDays(60).rating(4.7).totalRatings(89234)
                .thumbnailColor("#0ea5e9")
                .whatYouLearn("Docker containers|Docker Compose|Kubernetes clusters|CI/CD pipelines").build(),

            // --- CYBERSECURITY ---
            Course.builder().courseCode("CYBER301").title("Ethical Hacking Bootcamp 2025")
                .description("Complete ethical hacking and penetration testing course. Learn network scanning, vulnerability exploitation, web app security, and CTF challenges.")
                .instructor("Zaid Sabih").category("Cybersecurity").subCategory("Ethical Hacking")
                .price(499).originalPrice(3499).creditHours(6)
                .durationHours(33).totalLectures(185).difficultyLevel("Advanced")
                .dropDeadlineDays(30).rating(4.6).totalRatings(98234)
                .thumbnailColor("#dc2626")
                .whatYouLearn("Network scanning|Metasploit framework|Web vulnerabilities|Password cracking").build(),

            // --- MOBILE ---
            Course.builder().courseCode("FLUTTER301").title("Flutter and Dart: Complete Developer Course")
                .description("Build beautiful iOS and Android apps with Flutter. State management, Firebase, REST APIs, animations and publishing to app stores.")
                .instructor("Dr. Angela Yu").category("Mobile").subCategory("Flutter")
                .price(549).originalPrice(3999).creditHours(7)
                .durationHours(31).totalLectures(261).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.8).totalRatings(112456)
                .thumbnailColor("#38bdf8").bestseller(true)
                .whatYouLearn("Flutter widgets|State management|Firebase integration|App store publishing").build(),

            Course.builder().courseCode("KOTLIN201").title("Android App Development with Kotlin")
                .description("Build Android apps professionally with Kotlin, Jetpack Compose, MVVM architecture, Room database, and Coroutines.")
                .instructor("Philipp Lackner").category("Mobile").subCategory("Android")
                .price(499).originalPrice(3499).creditHours(6)
                .durationHours(40).totalLectures(210).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.7).totalRatings(67234)
                .thumbnailColor("#a855f7")
                .whatYouLearn("Jetpack Compose|MVVM architecture|Room database|Kotlin Coroutines").build(),

            // --- DESIGN ---
            Course.builder().courseCode("UI201").title("UI/UX Design Bootcamp")
                .description("Master Figma, design systems, user research, wireframing, prototyping and design handoff. Build a portfolio of 6 real-world case studies.")
                .instructor("Gary Simon").category("Design").subCategory("UI/UX")
                .price(449).originalPrice(3299).creditHours(5)
                .durationHours(24).totalLectures(195).difficultyLevel("Beginner")
                .dropDeadlineDays(30).rating(4.6).totalRatings(78123)
                .thumbnailColor("#f43f5e")
                .whatYouLearn("Figma proficiency|Design systems|User research|Portfolio projects").build(),

            // --- SPRING BOOT ---
            Course.builder().courseCode("SB401").title("Spring Boot 3 and Spring Framework 6")
                .description("Master Spring Boot 3 with REST APIs, Spring Security, JPA, Hibernate, Microservices, Docker and cloud deployment on AWS.")
                .instructor("Chad Darby").category("Programming").subCategory("Spring Boot")
                .price(649).originalPrice(4299).creditHours(9)
                .durationHours(52).totalLectures(360).difficultyLevel("Advanced")
                .dropDeadlineDays(60).rating(4.7).totalRatings(89456)
                .thumbnailColor("#22c55e").bestseller(true)
                .whatYouLearn("Spring MVC and REST|Spring Security with JWT|JPA and Hibernate|Microservices").build(),

            // --- MATHEMATICS ---
            Course.builder().courseCode("DS401").title("Statistics and Probability for Data Science")
                .description("Master statistics for machine learning and data science: probability theory, hypothesis testing, Bayesian inference, and regression analysis.")
                .instructor("Prof. Jose Marin").category("Mathematics").subCategory("Statistics")
                .price(399).originalPrice(2799).creditHours(6)
                .durationHours(20).totalLectures(148).difficultyLevel("Intermediate")
                .dropDeadlineDays(30).rating(4.5).totalRatings(45678)
                .thumbnailColor("#6366f1")
                .whatYouLearn("Probability theory|Hypothesis testing|Bayesian inference|Regression").build()
        ));
    }
}
