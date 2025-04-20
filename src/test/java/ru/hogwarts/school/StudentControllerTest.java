package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class StudentControllerTest {
    @LocalServerPort
    private int port;

    @Autowired
    private StudentController studentController;

    @Autowired
    private TestRestTemplate restTemplate;
    private Student student;

    @BeforeEach
    void info() {
        student = new Student();
        student.setName("Jeremy");
        student.setAge(19);
        student = restTemplate.postForObject("/student", student, Student.class);
    }

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

    @Test
    void testCreateStudent() throws Exception {
        Student newStudent = new Student();
        newStudent.setName("New Student");
        newStudent.setAge(22);

        ResponseEntity<Student> response = restTemplate.postForEntity(
                "/student",
                newStudent,
                Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertNotNull(response.getBody().getId());
        assertEquals("New Student", response.getBody().getName());
    }

    @Test
    public void testGetAllStudents() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/student",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void testUpdateStudent() {
        student.setName("Fred");
        student.setAge(12);

        ResponseEntity<Student> response = restTemplate.exchange(
                "/student/" + student.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(student),
                Student.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Fred", response.getBody().getName());
        assertEquals(12, response.getBody().getAge());
    }

    @Test
    public void testGetStudentsFaculty() {
        ResponseEntity<Faculty> response = restTemplate.getForEntity(
                "/student/" + student.getId() + "/faculty",
                Faculty.class);
        assertTrue(response.getStatusCode() == HttpStatus.OK ||
                response.getStatusCode() == HttpStatus.NOT_FOUND);
    }

    @Test
    public void testGetAllStudentsCount() {
        ResponseEntity<Integer> response = restTemplate.getForEntity(
                "/student/all_students",
                Integer.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() >= 0);
    }

    @Test
    public void testGetLastStudents() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/student/get_last",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetStudentsWithNameStartsWithA() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/student/nameWithStartsWithA",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetAverageAgeOfStudents() {
        ResponseEntity<Double> response = restTemplate.getForEntity(
                "/student/getAverageAgeOfStudents",
                Double.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertTrue(response.getBody() > 0);
    }

    @Test
    public void testGetParallelStudentsNames() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/student/print-parallel",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }

    @Test
    public void testGetSynchronizedStudentsNames() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/student/print-synchronized",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
    }
}
