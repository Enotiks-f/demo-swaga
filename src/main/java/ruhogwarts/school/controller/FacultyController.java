package ruhogwarts.school.controller;

import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.service.FacultyService;

import java.util.Collection;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private final FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @GetMapping
    public ResponseEntity<Collection<Faculty>> getAllFaculties() {
        Collection<Faculty> faculties = facultyService.getAllFaculties();
        return ResponseEntity.ok(faculties);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Faculty> getFaculty(@PathVariable Long id) {
        Faculty faculty = facultyService.getFaculty(id);
        if (faculty != null) {
            return ResponseEntity.ok(faculty);
        }
        return ResponseEntity.notFound().build();
    }

    @PostMapping
    public ResponseEntity<Faculty> addFaculty(@RequestBody Faculty faculty) {
        Faculty addF = facultyService.addFaculty(faculty);
        return ResponseEntity.status(HttpStatus.CREATED).body(addF);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Faculty> updateFaculty(@PathVariable Long id, @RequestBody Faculty faculty) {
        Faculty upd = facultyService.updateFaculty(id, faculty);
        if (upd != null) {
            return ResponseEntity.ok(upd);
        }
        return ResponseEntity.notFound().build();
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteFaculty(@PathVariable Long id) {
        facultyService.removeFaculty(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/get/students")
    public ResponseEntity<List<Student>> getStudentsByFaculty(Long facultyId) {
        List<Student> students = facultyService.getStudentsByFaculty(facultyId);
        return ResponseEntity.ok(students);
    }


    @GetMapping("longer/name")
    public ResponseEntity<String> getFacultyNames() {
        String name = facultyService.getLongerNameFaculty();
        return ResponseEntity.ok(name);
    }
}