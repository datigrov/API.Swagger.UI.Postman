package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;

    Faculty faculty;

    @Test
    void studentIsNotNull() throws Exception {
        assertThat(studentController).isNotNull();
    }

    /*@Test
    void testGetStudent()throws Exception {
        assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student", String.class))
                .isNotEmpty();
    }*/

    @Test
    void testPostStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setAge(15);
        student.setName("John");
        assertThat(
                this.restTemplate.postForObject("http://localhost:" + port + "/student",
                        student, String.class)).isNotEmpty();
    }

    /*@Test
    void testDeleteStudent() throws Exception {
        Student student = new Student(5L,"Bob",18);
        ResponseEntity<Student> newResponseEntity =
                restTemplate.postForEntity("http://localhost:" + port + "/student", student, Student.class);
        Assertions.assertThat(newResponseEntity.getStatusCode().equals(HttpStatus.OK)).isTrue();
        Student student1 = newResponseEntity.getBody();
        restTemplate.delete("http://localhost:" + port + "/student", student.getId(), Student.class);
    }

    @Test
    void findByAgeBetweenTest() throws Exception {
    }*/

    @Test
    void createTest() throws Exception {
        Student student = new Student(5L, "John", 15);
        student.setFaculty(faculty);
        ResponseEntity<Student> studentResponseEntity = restTemplate.postForEntity("http://localhost:"+ port + "/student/", student, Student.class);
        //assertThat(studentResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        Student responseBody = studentResponseEntity.getBody();
        assertThat(responseBody.getId()).isEqualTo(5L);
        assertThat(responseBody.getName()).isEqualTo("John");
        assertThat(responseBody.getAge()).isEqualTo(15);


        studentResponseEntity = restTemplate.postForEntity("http://localhost:"+ port + "/student/", responseBody.getId(), Student.class);
        responseBody = studentResponseEntity.getBody();
        assertThat(responseBody.getId()).isEqualTo(5L);
        assertThat(responseBody.getName()).isEqualTo("John");
        assertThat(responseBody.getAge()).isEqualTo(15);

        responseBody.setName("Robert");
        HttpEntity<Student> httpEntity = new RequestEntity<>(responseBody, HttpMethod.PUT, null);
        studentResponseEntity = restTemplate.exchange("/student/" + responseBody.getId(), HttpMethod.PUT, httpEntity, Student.class);
        //assertThat(studentResponseEntity.getStatusCode().is2xxSuccessful()).isTrue();

        studentResponseEntity = restTemplate.getForEntity("/student/" + responseBody.getId(), Student.class);
        responseBody = studentResponseEntity.getBody();
        assertThat(responseBody.getId()).isEqualTo(5L);
        assertThat(responseBody.getName()).isEqualTo("Robert");
        assertThat(responseBody.getAge()).isEqualTo(15);


        restTemplate.delete("/student/" + responseBody.getId());
        studentResponseEntity = restTemplate.getForEntity("/student/" + responseBody.getId(), Student.class);
        assertThat(studentResponseEntity.getStatusCode()).isEqualTo(HttpStatus.BAD_REQUEST);


    }
}
