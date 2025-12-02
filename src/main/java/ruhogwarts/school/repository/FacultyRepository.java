package ruhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruhogwarts.school.model.Faculty;

public interface FacultyRepository extends JpaRepository<Faculty, Long> {
}
