package ruhogwarts.school;

import com.fasterxml.jackson.core.type.TypeReference;
import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.Mock;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.MvcResult;
import org.springframework.test.web.servlet.ResultMatcher;
import ruhogwarts.school.controller.StudentController;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.StudentRepository;
import ruhogwarts.school.service.StudentService;

import static org.assertj.core.api.Assertions.assertThat;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

import static net.bytebuddy.matcher.ElementMatchers.is;
import static org.hamcrest.collection.IsCollectionWithSize.hasSize;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.client.match.MockRestRequestMatchers.jsonPath;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.status;


@WebMvcTest(StudentController.class)
public class StudentControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @Mock
    private StudentRepository repository;

    @Mock
    private StudentService service;

    @Test
    void getAllStudents() throws Exception {
        // Arrange
        Student student1 = new Student();
        student1.setId(1L);
        student1.setName("student1");
        student1.setAge(11);

        Student student2 = new Student();
        student2.setId(2L);
        student2.setName("student2");
        student2.setAge(21);

        Collection<Student> students = Arrays.asList(student1, student2);

        when(service.getAllStudents()).thenReturn(students);

        // Act
        MvcResult result = mockMvc.perform(get("/student")
                        .accept(MediaType.APPLICATION_JSON))
                .andExpect(status().isOk())
                .andReturn();

        // Assert
        String jsonResponse = result.getResponse().getContentAsString();
        List<Student> responseStudents = objectMapper.readValue(
                jsonResponse,
                new TypeReference<List<Student>>() {}
        );

        assertThat(responseStudents).hasSize(2);
        assertThat(responseStudents.get(0).getName()).isEqualTo("student1");
        assertThat(responseStudents.get(1).getName()).isEqualTo("student2");
    }

}
