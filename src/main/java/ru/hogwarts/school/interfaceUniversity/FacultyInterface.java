package ru.hogwarts.school.interfaceUniversity;

import ru.hogwarts.school.model.Faculty;

public interface FacultyInterface {
    Faculty createFaculty(Faculty faculty);
    Faculty getFaculty(long id);
    Faculty updateFaculty(long id, Faculty faculty);
    Faculty deleteFaculty(long id);
}
