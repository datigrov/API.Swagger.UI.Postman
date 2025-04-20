package ru.hogwarts.school;

import com.fasterxml.jackson.databind.ObjectMapper;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.test.web.servlet.MockMvc;
import org.springframework.test.web.servlet.request.MockMvcRequestBuilders;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

import static org.mockito.ArgumentMatchers.*;
import static org.mockito.Mockito.when;
import static org.springframework.test.web.servlet.result.MockMvcResultMatchers.*;

@WebMvcTest(StudentController.class)
public class WebMvcStudentControllerTest {

    @Autowired
    private MockMvc mockMvc;

    @Autowired
    private ObjectMapper objectMapper;

    @MockBean
    private StudentService studentService;

    private Faculty faculty;

    @Test
    public void createStudent_ShouldReturnCreatedStudent() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(20);

        when(studentService.createStudent(any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.post("/student")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("John"))
                .andExpect(jsonPath("$.age").value(20));
    }

    @Test
    public void getAllStudents_ShouldReturnStudentList() throws Exception {
        Student student = new Student();
        student.setName("John");
        student.setAge(20);

        when(studentService.getAllStudentOnUnivercity()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("John"))
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    public void getStudentByIdButReturnStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("John");

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("John"));
    }

    @Test
    public void getStudentByIdButReturnNotFound() throws Exception {
        when(studentService.findStudent(999L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/999"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void updateStudentButReturnUpdatedStudent() throws Exception {
        Student student = new Student();
        student.setId(1L);
        student.setName("Updated");

        when(studentService.editStudent(eq(1L), any(Student.class))).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/1")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.id").value(1))
                .andExpect(jsonPath("$.name").value("Updated"));
    }

    @Test
    public void updateStudentButReturnNotFound() throws Exception {
        Student student = new Student();
        student.setId(999L);

        when(studentService.editStudent(eq(999L), any(Student.class))).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.put("/student/999")
                        .contentType(MediaType.APPLICATION_JSON)
                        .content(objectMapper.writeValueAsString(student)))
                .andExpect(status().isNotFound());
    }

    @Test
    public void deleteStudentButReturnOk() throws Exception {
        mockMvc.perform(MockMvcRequestBuilders.delete("/student/1"))
                .andExpect(status().isOk());
    }

    @Test
    public void findByAgeBetweenButReturnStudents() throws Exception {
        Student student = new Student();
        student.setAge(20);

        when(studentService.findByAgeBetween(anyInt(), anyInt())).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/studentAgeBetween")
                        .param("maxAge", "25")
                        .param("minAge", "18"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].age").value(20));
    }

    @Test
    public void getStudentsFacultyButReturnFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Science");

        Student student = new Student();
        student.setFaculty(faculty);

        when(studentService.findStudent(1L)).thenReturn(student);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/1/faculty"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$.name").value("Science"));
    }

    @Test
    public void getStudentsFacultyButReturnNotFound() throws Exception {
        when(studentService.findStudent(999L)).thenReturn(null);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/999/faculty"))
                .andExpect(status().isNotFound());
    }

    @Test
    public void getAllStudentsCountButReturnNumber() throws Exception {
        when(studentService.getAllStudentsIn()).thenReturn(10);

        mockMvc.perform(MockMvcRequestBuilders.get("/student/all_students"))
                .andExpect(status().isOk())
                .andExpect(content().string("10"));
    }

    @Test
    public void getAverageStudentsButReturnStudents() throws Exception {
        Student student = new Student();
        student.setName("Alex");

        when(studentService.getAverageStudents()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/get_average"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alex"));
    }

    @Test
    public void getLastStudents_ShouldReturnStudents() throws Exception {
        Student student = new Student();
        student.setName("Alex");

        when(studentService.getLastStudentsOfUniversity()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/get_last"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alex"));
    }

    @Test
    public void getStudentsWithNameStartsWithA_ShouldReturnStudents() throws Exception {
        Student student = new Student();
        student.setName("Alex");

        when(studentService.getAllStudentsWithNameStartsWithA()).thenReturn(List.of(student));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/nameWithStartsWithA"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0].name").value("Alex"));
    }

    @Test
    public void getAverageAgeOfStudents_ShouldReturnDouble() throws Exception {
        when(studentService.getAverageAgeStudents()).thenReturn(ResponseEntity.ok(20.5));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/getAverageAgeOfStudents"))
                .andExpect(status().isOk())
                .andExpect(content().string("20.5"));
    }

    @Test
    public void getParallelStudentsNames_ShouldReturnNames() throws Exception {
        when(studentService.parallelStudentsNames()).thenReturn(List.of("Alex", "Fred"));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/print-parallel"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Alex"))
                .andExpect(jsonPath("$[1]").value("Fred"));
    }

    @Test
    public void getSynchronizedStudentsNames_ShouldReturnNames() throws Exception {
        when(studentService.printSynchronized()).thenReturn(List.of("Alex", "Fred"));

        mockMvc.perform(MockMvcRequestBuilders.get("/student/print-synchronized"))
                .andExpect(status().isOk())
                .andExpect(jsonPath("$[0]").value("Alex"))
                .andExpect(jsonPath("$[1]").value("Fred"));
    }





}
