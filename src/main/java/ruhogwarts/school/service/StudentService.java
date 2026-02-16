package ruhogwarts.school.service;


import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.StudentRepository;

import java.util.*;

@Service
public class StudentService {

    Logger logger = LoggerFactory.getLogger(StudentService.class);

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Collection<Student> getAllStudents() {
        logger.info("Was invoked method for getting all students");
        return studentRepository.findAll();
    }

    public Collection<Student> getBetweenAgeStudent(int min, int max) {
        logger.info("Was invoked method for getting between age students");
        return studentRepository.findByAgeBetween(min, max);
    }

    public Optional<Student> getStudent(Long id) {
        logger.info("Was invoked method for getting student with id: {}", id);
        return  studentRepository.findById(id);
    }

    public Student addStudent(Student student) {
        logger.info("Was invoked method for adding student");
        return studentRepository.save(student);
    }

    public Student updateStudent(Student updated) {
        logger.info("Was invoked method for updating student");
        return studentRepository.save(updated);
    }

    public void removeStudent(Long id) {
        logger.info("Was invoked method for removing student");
        studentRepository.deleteById(id);
    }

    public Faculty getFacultyStudent(Long studentId) {
        logger.info("Was invoked method for getting faculty with id: {}", studentId);
        Student student = studentRepository.findById(studentId).orElseThrow(() -> new IllegalArgumentException("Student with id: " + studentId + " not found"));
        return student.getFaculty();
    }

    public long coundAllStudents() {
        logger.info("Was invoked method for counting all students");
        return studentRepository.count();
    }

    public Double getAvgAge()
    {
        logger.info("Was invoked method for avg age");
        return studentRepository.getAverageAge();
    }

    public List<Student> findTop5ByOrderByIdDesc() {
        logger.info("Was invoked method for finding top 5 students");
        return studentRepository.findTop5ByOrderByIdDesc();
    }

    public List<String> searchStudentByNameA() {
        logger.info("Was invoked method for searching student by name");

        List<Student> list = studentRepository.findAll();

        return list.stream()
                .map(s -> s.getName().toUpperCase())
                .filter(s -> s.toUpperCase().startsWith("А"))
                .sorted()
                .toList();

    }

    public int getAvgAgeStudent() {
        logger.info("Was invoked method for getting average age");

        return (int) studentRepository.findAll().stream()
                .mapToInt(Student::getAge).average().orElse(0);
    }


}
