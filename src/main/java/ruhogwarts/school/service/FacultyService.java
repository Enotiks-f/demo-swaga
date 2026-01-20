package ruhogwarts.school.service;

import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.FacultyRepository;

import java.util.*;

@Service
public class FacultyService {

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Collection<Faculty> getAllFaculties() {
        return facultyRepository.findAll();
    }

    public Faculty getFaculty(Long id) {
        return facultyRepository.getById(id);
    }

    public Faculty addFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty updateFaculty(Long id, Faculty updated) {
        return facultyRepository.save(updated);
    }

    public void removeFaculty(Long id) {
        facultyRepository.deleteById(id);
    }

    public List<Student> getStudentsByFaculty(Long facultyId) {
        Faculty f = facultyRepository.findById(facultyId)
                .orElseThrow(() -> new IllegalArgumentException("Faculty with id: " + facultyId + " not found"));
        return  f.getStudents();
    }

}