package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.client.TestRestTemplate;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpEntity;
import org.springframework.http.HttpMethod;
import org.springframework.http.RequestEntity;
import org.springframework.http.ResponseEntity;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;

import static org.assertj.core.api.Assertions.assertThat;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyControllerTest {
    @LocalServerPort
    private int port;
    @Autowired
    private FacultyController facultyController;
    @Autowired
    private TestRestTemplate restTemplate;
    @Autowired
    private FacultyRepository facultyRepository;

    @Test
    void facultyInNotNullTest() throws Exception {
        assertThat(facultyController).isNotNull();
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
        org.junit.jupiter.api.Assertions.assertNotNull(newResponse);


        Faculty newFaculty = newResponse.getBody();
        org.junit.jupiter.api.Assertions.assertEquals(newFaculty.getName(), faculty.getName());
    }

    @Test
    void getFacultyTest() throws Exception {
        Faculty faculty = new Faculty(2l, "russian", "white");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> newResponse = restTemplate.getForEntity("Http://localhost:" +
                port + "/faculty", Faculty.class);
        assertThat(newResponse.getStatusCode().is2xxSuccessful()).isTrue();

        Faculty newFaculty = newResponse.getBody();
        org.junit.jupiter.api.Assertions.assertEquals(newFaculty.getName(), faculty.getName());
        org.junit.jupiter.api.Assertions.assertEquals(newFaculty.getId(), faculty.getId());
        org.junit.jupiter.api.Assertions.assertEquals(newFaculty.getColor(), faculty.getColor());
    }

    @Test
    void deleteFacultyTest() throws Exception {
        Faculty faculty = new Faculty(2l, "russian", "white");
        faculty = facultyRepository.save(faculty);

        ResponseEntity<Faculty> newResponse = restTemplate.postForEntity("http://localhost:" + port + "/faculty", faculty, Faculty.class);
        assertThat(newResponse.getStatusCode().is2xxSuccessful()).isTrue();
        Faculty newFaculty = newResponse.getBody();
        restTemplate.delete("http://localhost:" + port + "/faculty", faculty, Faculty.class);
    }

    @Test
    void findFacultyGetTest() throws Exception {
        ResponseEntity<Faculty> responseFaculty = restTemplate.postForEntity("http://localhost:" +
                port + "/faculty", new Faculty(2l, "russian", "white"), Faculty.class);
        assertThat(responseFaculty.getStatusCode().is2xxSuccessful()).isTrue();
        Faculty newFaculty = responseFaculty.getBody();

        ResponseEntity<Faculty> newResponseFaculty = restTemplate.getForEntity("http://localhost:"
                + port + "/faculty/" + newFaculty.getId(), Faculty.class);

        Faculty faculty = newResponseFaculty.getBody();
        assertThat(faculty.getName()).isEqualTo("russian");
        assertThat(faculty.getColor()).isEqualTo("white");
        assertThat(faculty.getId()).isEqualTo(2L);
    }










}
