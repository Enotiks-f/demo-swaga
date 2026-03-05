package ruhogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    Logger logger = LoggerFactory.getLogger(FacultyService.class);

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Faculty> getAllFaculties() {
        logger.info("Was invoked method for getting all faculties");
        return facultyRepository.findAll();
    }

    public Faculty getFaculty(Long id) {
        logger.info("Was invoked method for getting faculty with id {}", id);
        return facultyRepository.findById(id).orElse(null);
    }

    public Faculty addFaculty(Faculty faculty) {
        logger.info("Was invoked method for adding faculty with name {}", faculty.getName());
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty updated)
    {
        logger.info("Was invoked method for updating faculty with id {}", id);
        return facultyRepository.save(updated);
    }

    public void removeFaculty(Long id) {
        logger.info("Was invoked method for removing faculty with id {}", id);
        facultyRepository.deleteById(id);
    }

    public List<Student> getStudentsByFaculty(Long facultyId) {
        logger.info("Was invoked method for getting students by faculty with id {}", facultyId);
        Faculty f = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id: " + facultyId + " not found"));
        return  f.getStudents();
    }

    public String getLongerNameFaculty() {
        logger.info("Was");

        List<Faculty> faculties = facultyRepository.findAll();
        return faculties.stream()
                .map(Faculty::getName)
                .filter(Objects::nonNull)
                .max(Comparator.comparingInt(String::length))
                .orElse("");
    }


}