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
import ruhogwarts.school.model.Student;
import ruhogwarts.school.repository.StudentRepository;
import ruhogwarts.school.service.StudentService;

import static org.hamcrest.MatcherAssert.assertThat;
import static org.hamcrest.Matchers.is;
import static org.hamcrest.Matchers.notNullValue;




@ExtendWith(SpringExtension.class)
@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControlleRestTest {

    @Autowired
    private TestRestTemplate restTemplate;

    @Autowired
    private StudentRepository repository;

    @AfterEach
    public void resetDB() {
        repository.deleteAll();
    }

    // Тест POST запроса
    @Test
    void whenAddStudent_thenStatus201() {
        Student std = new Student();
        std.setName("test");
        std.setAge(12);

        ResponseEntity<Student> responseEntity = restTemplate.postForEntity("/student", std, Student.class);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.CREATED));
        Assertions.assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().getId(), notNullValue());
        assertThat(responseEntity.getBody().getName(), is("test"));
        }

    // тест PUT запроса
    @Test
    void whenGetStudent_thenStatus200() {

        long id = createTestStd("Joe").getId();

        Student student = restTemplate.getForObject("/student/{id}", Student.class, id);
        assertThat(student.getName(), is("Joe"));
    }

    // Метод DELETE запроса
    @Test
    public void givenStudent_whenDeleteStudent_thenStatus200() {
        long id = createTestStd("Мария").getId();
        ResponseEntity<Void> responseEntity = restTemplate.exchange("/student/{id}", HttpMethod.DELETE, null, Void.class, id);

        assertThat(responseEntity.getStatusCode(), is(HttpStatus.NO_CONTENT));
    }

    @Test
    void whenUpdateStudent_thenStatus200() {
        long id = createTestStd("Joe").getId();
        Student std1 = new Student();
        std1.setName("Nick");

        HttpEntity<Student> entity = new HttpEntity<>(std1);

        ResponseEntity<Student> responseEntity = restTemplate.exchange("/student/{id}", HttpMethod.PUT, entity,  Student.class, id);
        assertThat(responseEntity.getStatusCode(), is(HttpStatus.OK));
        Assertions.assertNotNull(responseEntity.getBody());
        assertThat(responseEntity.getBody().getId(), notNullValue());
        assertThat(responseEntity.getBody().getName(), is("Nick"));
    }

    // метод создание сущности Student
    private Student createTestStd(String name) {
        Student std = new Student();
        std.setName(name);
        return repository.save(std);

    }

    @Test
    void whenGetAllStudents_thenStatus200() {
        createTestStd("Alex");
        createTestStd("Bob");

        ResponseEntity<Student[]> response =
                restTemplate.getForEntity("/student", Student[].class);

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().length > 0, is(true));
    }

    @Test
    void whenGetStudentsBetweenAge_thenStatus200() {
        Student s1 = new Student();
        s1.setName("Tom");
        s1.setAge(10);
        repository.save(s1);

        Student s2 = new Student();
        s2.setName("Jerry");
        s2.setAge(15);
        repository.save(s2);

        ResponseEntity<Student[]> response = restTemplate.getForEntity(
                "/student/between/age?min=9&max=12",
                Student[].class
        );

        assertThat(response.getStatusCode(), is(HttpStatus.OK));
        Assertions.assertNotNull(response.getBody());
        assertThat(response.getBody().length, is(1));
        assertThat(response.getBody()[0].getName(), is("Tom"));
    }












}
