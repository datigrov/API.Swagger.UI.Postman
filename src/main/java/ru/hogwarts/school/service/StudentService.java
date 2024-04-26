package ru.hogwarts.school.service;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.StudentInterface;
import ru.hogwarts.school.model.Student;
import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class StudentService implements StudentInterface {
    private final HashMap<Long, Student> studentServiceInfo = new HashMap<>();
    private long nextId = 0L;

    @Override
    public Student createStudent(Student student) {
        student.setId(nextId++);
        studentServiceInfo.put(student.getId(), student);
        return student;
    }

    @Override
    public Student getStudent(long id) {
        return studentServiceInfo.get(id);
    }

    @Override
    public Student updateStudent(long id, Student student) {
        if (!studentServiceInfo.containsKey(id)) {
            return null;
        }
        studentServiceInfo.put(id, student);
        return student;
    }

    @Override
    public Student deleteStudent(long id) {
        return studentServiceInfo.remove(id);
    }

    public List<Student> ageStudent(int age) {
        return studentServiceInfo.values().stream().
                filter(student -> student.getAge() ==age).
                collect(Collectors.toList());
    }


}
