package ru.hogwarts.school;


import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.FacultyService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.request.MockMvcRequestBuilders.*;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(FacultyController.class)
public class WebMvcFacultyControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private FacultyService facultyService;

    @Test
    void listByColorButReturnFaculties() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setColor("RED");

        when(facultyService.listByColor(anyString())).thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/color?color=RED"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].color").value("RED"));
    }

    @Test
    void createFacultyButReturnCreatedFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Engineering");
        faculty.setColor("BLUE");

        when(facultyService.createFaculty(any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(post("/faculty")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Engineering"))
                .andExpect(jsonPath("$.color").value("BLUE"));
    }

    @Test
    void getFacultyByIdButReturnNotFound() throws Exception {
        when(facultyService.findFaculty(123L)).thenReturn(null);

        mockMvc.perform(get("/faculty/123"))
                .andExpect(status().isNotFound());
    }
    @Test
    void getFacultyByIdButReturnFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(123L);
        faculty.setName("Gryffindor");

        when(facultyService.findFaculty(123L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/123"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }
    @Test
    void updateFacultyButReturnNotFound() throws Exception {
        when(facultyService.editFaculty(eq(123L), any(Faculty.class))).thenReturn(null);

        mockMvc.perform(put("/faculty/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(new Faculty())))
                .andExpect(status().isNotFound());
    }

    @Test
    void deleteFacultyButReturnOk() throws Exception {
        mockMvc.perform(delete("/faculty/123"))
                .andExpect(status().isOk());
    }

    @Test
    void updateFacultyButReturnUpdatedFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setId(123L);
        faculty.setName("Gryffindor");

        when(facultyService.editFaculty(eq(123L), any(Faculty.class))).thenReturn(faculty);

        mockMvc.perform(put("/faculty/123")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(faculty)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(123))
                .andExpect(jsonPath("$.name").value("Gryffindor"));
    }

    @Test
    void findByColorOrNameButReturnFaculties() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Gryffindor");
        faculty.setColor("Red");

        when(facultyService.findByNameOrFindByColor(anyString(), anyString()))
                .thenReturn(List.of(faculty));

        mockMvc.perform(get("/faculty/nameOrColor?name=Gryffindor&color=Red"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Gryffindor"))
                .andExpect(jsonPath("$[0].color").value("Red"));
    }

    @Test
    void getStudentsByFacultyButReturnStudents() throws Exception {
        Student student = new Student();
        student.setName("Garry Potter");

        Faculty faculty = new Faculty();
        faculty.setStudents(List.of(student));

        when(facultyService.findFaculty(123L)).thenReturn(faculty);

        mockMvc.perform(get("/faculty/123/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Garry Potter"));
    }

    @Test
    void getStudentsByFacultyButReturnNotFound() throws Exception {
        when(facultyService.findFaculty(123L)).thenReturn(null);

        mockMvc.perform(get("/faculty/123/student"))
                .andExpect(status().isNotFound());
    }

    @Test
    void getLongerNameButReturnString() throws Exception {
        when(facultyService.getLongerNameOfFaculty()).thenReturn("TheLongestFacultyName");

        mockMvc.perform(get("/faculty/getLongerName"))
                .andExpect(status().isOk())
                .andExpect(content().string("TheLongestFacultyName"));
    }


}


