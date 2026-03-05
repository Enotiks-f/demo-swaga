package ruhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import org.springframework.data.jpa.repository.Query;
import ruhogwarts.school.model.Student;

import java.util.Collection;
import java.util.List;
import java.util.Optional;

public interface StudentRepository extends JpaRepository<Student, Long> {
    Collection<Student> findByAgeBetween(int min, int max);

    @Override
    Optional<Student> findById(Long aLong);

    long count();

    @Query("SELECT AVG(s.age) FROM Student s")
    Double getAverageAge();

    List<Student> findTop5ByOrderByIdDesc();
}
