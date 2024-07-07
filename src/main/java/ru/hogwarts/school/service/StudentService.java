package ru.hogwarts.school.service;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.http.ResponseEntity;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import java.util.List;
import java.util.stream.Collectors;


@Service
public class StudentService {

    private final StudentRepository studentRepository;
    Logger logger = LoggerFactory.getLogger(StudentRepository.class);

    public StudentService(StudentRepository studentRepository) {
        this.studentRepository = studentRepository;
    }

    public Student createStudent(Student student) {
        logger.info("Was invoked method for create student");
        logger.debug("debug of create student");
        return studentRepository.save(student);
    }

    public Student findStudent(Long id) {
        logger.info("Was invoked method for find student");
        return studentRepository.getById(id);
    }

    public Student editStudent(Long id, Student student) {
        logger.info("Was invoked method for edit student");
        return studentRepository.save(student);
    }

    public void deleteStudent(Long id) {
        logger.info("Was invoked method for delete student");
        logger.debug("debug of delete student");
        studentRepository.deleteById(id);
    }

    public List<Student> findByAgeBetween(int maxAge, int minAge) {
        logger.info("Was invoked method for find by age between student");
        return studentRepository.findByAgeBetween(maxAge, minAge);
    }

    public List<Student> getAllStudentOnUnivercity() {
        logger.info("Was invoked method for get all students");
        return studentRepository.findAll();
    }

    public Integer getAllStudentsIn() {
        return studentRepository.getAllStudentsFromUniversity();
    }

    public List<Student> getAverageStudents() {
        logger.info("Was invoked method for get average student");
        return studentRepository.getAverageAgeOfStudent();
    }

    public List<Student> getLastStudentsOfUniversity() {
        logger.info("Was invoked method for get last student");
        return studentRepository.getLastStudents();
    }

    public List<Student> getAllStudentsWithNameStartsWithA() {
        return studentRepository.findAll().stream().filter(s -> s.getName().toUpperCase().
                startsWith("A")).sorted().toList();
    }

    public ResponseEntity<Double> getAverageAgeStudents() {
        //return studentRepository.findAll().stream().mapToDouble(Student::getAge).average().orElseThrow();
        double listStudents = studentRepository.findAll().
                stream().mapToDouble(Student::getAge).average().orElseThrow();
        return ResponseEntity.ok(listStudents);
    }
    public List<String> parallelStudentsNames() {
        List<String> parallelStudents = studentRepository.findAll()
                .stream().parallel().limit(6).map(Student::getName)
                .toList();
        System.out.println(parallelStudents.get(0) + " " + parallelStudents.get(1));
        new Thread(() -> System.out.println(parallelStudents.get(2) + " " + parallelStudents.get(3)));
        new Thread(() -> System.out.println(parallelStudents.get(4) + " " + parallelStudents.get(5)));
        return parallelStudents;
    }

    public static void synchronuzedStudent(String name) {
        synchronized (Student.class) {
            System.out.println("Студент " +  name);
        }
    }

    public List<String> printSynchronized() {
        List<String> synchronuzedNames = studentRepository.findAll().stream().parallel()
                .limit(6).map(Student::getName).toList();
        synchronuzedStudent(synchronuzedNames.get(0));
        synchronuzedStudent(synchronuzedNames.get(1));
        new Thread(() ->
                synchronuzedStudent(synchronuzedNames.get(2)));
        synchronuzedStudent(synchronuzedNames.get(3));
        new Thread(() ->
                synchronuzedStudent(synchronuzedNames.get(4)));
        synchronuzedStudent(synchronuzedNames.get(5));
        return synchronuzedNames;
    }


}

