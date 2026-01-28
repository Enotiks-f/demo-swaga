package ruhogwarts.school;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.Test;
import org.junit.jupiter.api.extension.ExtendWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.test.context.junit.jupiter.SpringExtension;
import ruhogwarts.school.model.Faculty;
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.FacultyRepository;


import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;


@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerRestTest {

    @Autowired
    FacultyRepository repository;

    @Autowired
    TestRestTemplate restTemplate;

//    @AfterEach
//    void resetDB() {
//        repository.deleteAll();
//    }

    @Test
    void whenAddFaculty_thenStatus201() {
        Faculty faculty = new Faculty();
        faculty.setName("test");

        ResponseEntity<Faculty> response = restTemplate.postForEntity("/faculty", faculty, Faculty.class);

        assertThat(response.getStatusCode(), is(HttpStatus.CREATED));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getId(), is(notNullValue()));
        assertThat(response.getBody().getName(), is("test"));
    }

    @Test
    void whenGetFaculty_thenStatus200() {
        long id = creatFacultyTest("Slytherin", "Green").getId();

        Faculty f = restTemplate.getForObject("/faculty/{id}", Faculty.class, id);

        assertThat(f.getName(), is("Slytherin"));
    }

    @Test
    void whenGetAllFaculty_thenStatus200() {
        creatFacultyTest("Slytherin", "Green");
        creatFacultyTest("IT", "BLUE");

        ResponseEntity<Faculty[]> response =
                restTemplate.getForEntity("/faculty", Faculty[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().length > 0, is(true));
    }

    @Test
    void whenDeleteFaculty_thenStatus200() {
        long id = creatFacultyTest("Slytherin", "Green").getId();

        ResponseEntity<Void> response = restTemplate.exchange("/faculty/{id}", HttpMethod.DELETE, null, Void.class, id);
        assertThat(response.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void whenUpdateFaculty_thenStatus200() {
        long id = creatFacultyTest("Slytherin", "Green").getId();
        Faculty f = new Faculty();
        f.setName("IT");

        HttpEntity<Faculty> entity = new HttpEntity<>(f);

        ResponseEntity<Faculty> response = restTemplate.exchange("/faculty/{id}", HttpMethod.PUT, entity, Faculty.class, id);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().getId(), is(id));
        assertThat(response.getBody().getName(), is("IT"));
    }

    private Faculty creatFacultyTest(String name, String color) {
        Faculty faculty = new Faculty();
        faculty.setName(name);
        faculty.setColor(color);
        return repository.save(faculty);
    }
}
