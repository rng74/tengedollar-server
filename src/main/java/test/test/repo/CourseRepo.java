package test.test.repo;

import org.springframework.data.jpa.repository.JpaRepository;
import test.test.domain.Course;

public interface CourseRepo extends JpaRepository<Course, Long> {
}
