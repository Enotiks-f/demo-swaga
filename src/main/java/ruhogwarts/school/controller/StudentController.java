package ruhogwarts.school.controller;

import jakarta.servlet.http.HttpServletResponse;
import org.springframework.http.HttpHeaders;
import org.springframework.http.HttpStatus;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ruhogwarts.school.model.Avatar;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.service.AvatarService;
import ruhogwarts.school.service.StudentService;

import java.io.IOException;
import java.io.InputStream;
import java.io.OutputStream;
import java.nio.file.Files;
import java.nio.file.Path;
import java.util.Collection;

@RestController
@RequestMapping("/student")
public class StudentController {

    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }


    @GetMapping
    public ResponseEntity<Collection<Student>> getAllStudents() {
        Collection<Student> getAll = studentService.getAllStudents();
        return ResponseEntity.ok(getAll);
    }

    @GetMapping("/{id}")
    public ResponseEntity<Student> getStudent(@PathVariable Long id) {
        return studentService.getStudent(id)
                .map(ResponseEntity::ok)
                .orElseGet(() -> ResponseEntity.notFound().build());
    }

    @PostMapping
    public ResponseEntity<Student> addStudent(@RequestBody Student student) {
        Student adSt1 = studentService.addStudent(student);
        return ResponseEntity.status(HttpStatus.CREATED).body(adSt1);
    }

    @PutMapping("/{id}")
    public ResponseEntity<Student> updateStudent(@PathVariable Long id,
                                                 @RequestBody Student student) {
        student.setId(id);  // <- ВАЖНО
        Student upd1 = studentService.updateStudent(student);
        return ResponseEntity.ok(upd1);
    }

    @DeleteMapping("/{id}")
    public ResponseEntity<Void> deleteStudent(@PathVariable Long id) {
        studentService.removeStudent(id);
        return ResponseEntity.noContent().build();
    }

    @GetMapping("/between/age")
    public ResponseEntity<Collection<Student>> getStudentsBetween(@RequestParam Integer min,
                                                                  @RequestParam Integer max) {
        Collection<Student> getBetAge = studentService.getBetweenAgeStudent(min, max);
        return ResponseEntity.ok(getBetAge);
    }

    @GetMapping("/get/faculty")
    public ResponseEntity<Faculty> getFacultyByStudent(@RequestParam Long id) {
        Faculty facultyStudent = studentService.getFacultyStudent(id);
        return ResponseEntity.ok(facultyStudent);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestPart MultipartFile file) throws IOException {
        if (file.getSize() >= 1024 * 300) {
            return ResponseEntity.badRequest().build();
        }

        avatarService.uploadAvatar(id, file);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/{id}/avatar")
    public void downloadAvatar(@PathVariable Long id, HttpServletResponse response) throws IOException {
        Avatar avatar = avatarService.findOrCreateAvatar(id);

        Path path = Path.of(avatar.getFilePath());

        try (InputStream is = Files.newInputStream(path);
             OutputStream os = response.getOutputStream()) {
            response.setContentType(avatar.getMediaType());
            response.setContentLength((int) avatar.getFileSize());
            is.transferTo(os);
        }
    }

    @GetMapping("/{id}/avatar/download")
    public ResponseEntity<byte[]> downloadAvatar(@PathVariable Long id) throws IOException {
        Avatar avatar = avatarService.findOrCreateAvatar(id);

        HttpHeaders headers = new HttpHeaders();
        headers.setContentType(MediaType.parseMediaType(avatar.getMediaType()));
        headers.setContentLength(avatar.getFileSize());
        return ResponseEntity.status(HttpStatus.OK).headers(headers).body(avatar.getData());
    }
}