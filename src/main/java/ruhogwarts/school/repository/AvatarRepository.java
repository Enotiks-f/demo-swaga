package ruhogwarts.school.repository;

import org.springframework.data.jpa.repository.JpaRepository;
import ruhogwarts.school.model.Avatar;

import java.util.Optional;

public interface AvatarRepository extends JpaRepository<Avatar, Long> {
    Optional<Avatar> findByStudentId(Long studentId);
}
