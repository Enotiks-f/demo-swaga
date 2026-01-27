package ruhogwarts.school.service;


import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getAllStudents() {
        return studentRepository.findAll();
    }

    public Collection<Student> getBetweenAgeStudent(int min, int max) {
        return studentRepository.findByAgeBetween(min, max);
    }

    public Optional<Student> getStudent(Long id) {
        return  studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student updateStudent(Student updated) {
        return studentRepository.save(updated);
    }

    public void removeStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public Faculty getFacultyStudent(Long studentId) {
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student with id: " + studentId + " not found"));
        return student.getFaculty();
    }
}
