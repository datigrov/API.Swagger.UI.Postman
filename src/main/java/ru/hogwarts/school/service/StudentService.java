package ru.hogwarts.school.service;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService{

    private final StudentRepository studentRepository;

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        return studentRepository.getById(id);
    }

    public Student editStudent(Long id, Student student) {
       return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int maxAge, int minAge) {
        return studentRepository.findByAgeBetween(maxAge, minAge);
    }

    public List<Student> getAllStudentOnUnivercity() {
        return studentRepository.findAll();
    }

}
