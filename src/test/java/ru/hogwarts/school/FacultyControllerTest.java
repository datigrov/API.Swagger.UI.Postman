package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.*;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import java.util.List;

import static org.assertj.core.api.Assertions.assertThat;
import static org.junit.jupiter.api.Assertions.*;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    private Faculty faculty;

    @BeforeEach
    void info() {
        faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");
        faculty = restTemplate.postForObject("/faculty", faculty, Faculty.class);
    }

    @Test
    void facultyIsNotEmptyTest() throws Exception {
        assertThat(restTemplate.getForObject("Http://localhost:" +
                port + "/faculty", String.class)).isNotEmpty();
    }

    @Test
    void createFacultyTest()throws Exception {
        Faculty faculty = new Faculty(1l, "literature", "red");
        ResponseEntity<Faculty> newResponse = restTemplate.postForEntity("Http://localhost:" +
                port + "/faculty", faculty, Faculty.class);
        assertThat(newResponse.getStatusCode().is2xxSuccessful()).isTrue();
        assertNotNull(newResponse);


        Faculty newFaculty = newResponse.getBody();
        assertEquals(newFaculty.getName(), faculty.getName());
    }

    @Test
    public void listByFacultyColors() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/faculty/color?color=Red",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void updateFaculty_ShouldReturnUpdatedFaculty() {
        faculty.setName("Gryffindor");
        faculty.setColor("RED");

        ResponseEntity<Faculty> response = restTemplate.exchange(
                "/faculty/" + faculty.getId(),
                HttpMethod.PUT,
                new HttpEntity<>(faculty),
                Faculty.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertEquals("Gryffindor", response.getBody().getName());
        assertEquals("RED", response.getBody().getColor());
    }

    @Test
    public void findByColorOrName() {
        ResponseEntity<List> response = restTemplate.getForEntity(
                "/faculty/nameOrColor?name=Gryffindor&color=Red",
                List.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }

    @Test
    public void getLongerFacultyName() {
        ResponseEntity<String> response = restTemplate.getForEntity(
                "/faculty/getLongerName",
                String.class);

        assertEquals(HttpStatus.OK, response.getStatusCode());
        assertNotNull(response.getBody());
        assertFalse(response.getBody().isEmpty());
    }
}
