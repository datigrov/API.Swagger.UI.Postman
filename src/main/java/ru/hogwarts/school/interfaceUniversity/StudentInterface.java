package ru.hogwarts.school.interfaceUniversity;

import ru.hogwarts.school.model.Student;

public interface StudentInterface {
    Student createStudent(Student student);
    Student findStudent(long id);
    Student editStudent(long id, Student student);
    Student deleteStudent(long id);
}
