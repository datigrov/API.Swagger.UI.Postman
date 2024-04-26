package ru.hogwarts.school.interfaceUniversity;

import ru.hogwarts.school.model.Student;

public interface StudentInterface {
    Student createStudent(Student student);
    Student getStudent(long id);
    Student updateStudent(long id, Student student);
    Student deleteStudent(long id);
}
