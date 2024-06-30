package ru.hogwarts.school.controller;


import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.StudentService;

import java.util.List;

@RestController
@RequestMapping("/student")
public class StudentController {

    private StudentService studentService;

    public StudentController(StudentService studentService) {
        this.studentService = studentService;
    }

    @PostMapping
    public Student createStudents(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping
    public List<Student> getAllStudent() {
        return studentService.getAllStudentOnUnivercity();
    }

    @GetMapping("{id}")
    public ResponseEntity<Student> getStudents(@PathVariable Long id) {
        Student newStudent = studentService.findStudent(id);
        if (newStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newStudent);
    }

    @PutMapping("{id}")
    public ResponseEntity<Student> updateStudents(@RequestBody Student student, @PathVariable Long id) {
        Student changeStudent = studentService.editStudent(id, student);
        if (changeStudent == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changeStudent);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Student> deleteStudents(@PathVariable Long id) {
        studentService.deleteStudent(id);
        return ResponseEntity.ok().build();
    }

    @GetMapping("/studentAgeBetween")
    public List<Student> findByAgeBetween(@RequestParam int maxAge, @RequestParam int minAge) {
        return studentService.findByAgeBetween(maxAge, minAge);
    }

    @GetMapping("{id}/faculty")
    public ResponseEntity<Faculty> getStudentsByFaculties(@PathVariable Long id) {
        Student student = studentService.findStudent(id);
        if (student == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(student.getFaculty());
    }

    @GetMapping("/all_students")
    public Integer getAllStudents() {
        return studentService.getAllStudentsIn();
    }

    @GetMapping("/get_average")
    public List<Student> getAverage() {
        return studentService.getAverageStudents();
    }

    @GetMapping("/get_last")
    public List<Student> getLast() {
        return studentService.getLastStudentsOfUniversity();
    }

    @GetMapping("/nameWithStartsWithA")
    public List<Student> getStudentsWithA() {
        return studentService.getAllStudentsWithNameStartsWithA();
    }

    @GetMapping("/getAverageAgeOfStudents")
    public ResponseEntity<Double> getAverageAges() {
        return studentService.getAverageAgeStudents();
    }
}
