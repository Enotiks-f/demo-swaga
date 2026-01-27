package ruhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruhogwarts.school.model.Student;

public interface StudentRepository extends JpaRepository<Student, Long> {
}
