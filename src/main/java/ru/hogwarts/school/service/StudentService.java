package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService {
    private final HashMap<Long, Student> studentServiceInfo = new HashMap<>();
    private Long generateStudentid = 0L;

    public Student createStudent(Student student) {
        studentServiceInfo.put(generateStudentid, student);
        generateStudentid++;
        return student;
    }

    public Student getStudent(Long studentId) {
        return studentServiceInfo.get(studentId);
    }

    public Student updateStudent(Long studentId, Student student) {
        studentServiceInfo.put(generateStudentid, student);
        return student;
    }

    public Student deliteStudent(Long studentId) {
        return studentServiceInfo.remove(studentId);
    }

    public List<Student> ageStudent(Integer age) {
        return studentServiceInfo.values().stream().
                filter(student -> student.getAge() ==age).
                collect(Collectors.toList());
    }


}
