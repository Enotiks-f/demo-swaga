package ruhogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.mock.web.MockMultipartFile;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.web.multipart.MultipartFile;
import ruhogwarts.school.controller.StudentController;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.service.AvatarService;
import ruhogwarts.school.service.StudentService;

import java.util.List;
import java.util.Optional;

import static org.mockito.ArgumentMatchers.any;
import static org.mockito.ArgumentMatchers.eq;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.multipart;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
@ExtendWith(SpringExtension.class) // необязательно, но можно
public class StudentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    private StudentService service;

    @MockitoBean
    private AvatarService avatarService;

    @Test
    void shouldReturnStudentBeetweenAge() throws Exception {
        Student student1 = new Student();
        student1.setId(1L);
        student1.setAge(30);
        student1.setName("John");

        Student student2 = new Student();
        student2.setId(2L);
        student2.setAge(24);
        student2.setName("Herry");


        Mockito.when(service.getBetweenAgeStudent(20, 35))
                .thenReturn(List.of(student1, student2));

        mockMvc.perform(get("/student/between/age")
                    .param("min", "20")
                    .param("max", "35"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.length()").value(2))
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[1].name").value("Herry"));

        Mockito.verify(service).getBetweenAgeStudent(20, 35);
    }

    @Test
    void shouldReturnFacultyByStudentId() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Slytherin");
        faculty.setColor("Green");

        Mockito.when(service.getFacultyStudent(1L))
                .thenReturn(faculty);

        mockMvc.perform(get("/student/get/faculty")
                        .param("id", "1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Slytherin"))
                .andExpect(jsonPath("$.color").value("Green"));

        Mockito.verify(service).getFacultyStudent(1L);
    }

    @Test
    void shouldUploadAvatarSuccessfully() throws Exception {
        MockMultipartFile file = new MockMultipartFile(
                "file",
                "avatar.png",
                MediaType.IMAGE_PNG_VALUE,
                "test-image".getBytes()
        );

        mockMvc.perform(multipart("/student/{id}/avatar", 1L)
                        .file(file))
                .andExpect(status().isOk());

        Mockito.verify(avatarService)
                .uploadAvatar(eq(1L), any(MultipartFile.class));
    }

    @Test
    void whenGetStudent_thenStatus200() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Harry");

        Mockito.when(service.getStudent(1L))
                .thenReturn(Optional.of(student));

        mockMvc.perform(get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Harry"));
    }

}
