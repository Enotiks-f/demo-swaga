package ruhogwarts.school;

import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.mockito.Mockito;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.http.MediaType;
import org.springframework.test.context.bean.override.mockito.MockitoBean;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import org.springframework.test.web.servlet.MockMvc;
import ruhogwarts.school.controller.FacultyController;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.service.FacultyService;

import static org.mockito.ArgumentMatchers.any;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.post;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.get;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
@ExtendWith(SpringExtension.class)
public class FacultyControllerMockTest {

    @Autowired
    private MockMvc mockMvc;

    @MockitoBean
    FacultyService facultyService;

    @Test
    void whenGetFaculty_thenStatus200() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Hufflepuff");

        Mockito.when(facultyService.getFaculty(1L))
                .thenReturn(faculty);

        mockMvc.perform(get("/faculty/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Hufflepuff"));
    }

    @Test
    void whenAddFaculty_thenStatus201() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(1L);
        faculty.setName("Hufflepuff");

        Mockito.when(facultyService.addFaculty(any()))
                .thenReturn(faculty);

        mockMvc.perform(
                        post("/faculty")
                                .contentType(MediaType.APPLICATION_JSON)
                                .content("""
                        {
                          "name": "Hufflepuff"
                        }
                    """)
                )
                .andExpect(status().isCreated())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Hufflepuff"));
    }
}
