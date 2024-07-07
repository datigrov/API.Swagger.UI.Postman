package ru.hogwarts.school;

import org.mockito.InjectMocks;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.repository.StudentRepository;

@WebMvcTest
public class WebMvcStudentControllerTest {
    @MockBean
    private MockMvc mockMvc;
    @InjectMocks
    private StudentController studentController;
    @MockBean
    private StudentRepository studentRepository;




}
