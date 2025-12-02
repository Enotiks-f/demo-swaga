package ruhogwarts.school.service;


import org.springframework.stereotype.Service;
import ruhogwarts.school.model.Student;

import java.util.*;

@Service
public class StudentService {

    private final Map<Long, Student> students = new HashMap<>();
    private Long currentId = 1L;

    public Collection<Student> getAllStudents() {
        return students.values();
    }

    public Student getStudent(Long id) {
        return students.get(id);
    }

    public Student addStudent(Student student) {
        student.setId(currentId);
        students.put(currentId, student);
        currentId++;
        return student;
    }

    public Student updateStudent(Long id, Student updated) {
        Student student = students.get(id);

        if (student == null) {
            throw new RuntimeException("Student not found");
        }

        student.setName(updated.getName());
        student.setAge(updated.getAge());

        return student;
    }

    public void removeStudent(Long id) {
        students.remove(id);
    }

    public Collection<Student> findByAge(int age) {
        List<Student> result = new ArrayList<>();
        for (Student s : students.values()) {
            if (s.getAge() == age) {
                result.add(s);
            }
        }
        return result;
    }
}
