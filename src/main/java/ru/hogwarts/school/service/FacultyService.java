package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.FacultyInterface;
import ru.hogwarts.school.model.Faculty;


import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService implements FacultyInterface {
    private final HashMap<Long, Faculty> facultyServiceInfo = new HashMap<>();
    private Long count = 0L;

    @Override
    public Faculty createFaculty(Faculty faculty) {
        facultyServiceInfo.put(faculty.getId(), faculty);
        faculty.setId(count++);
        return faculty;
    }

    @Override
    public Faculty getFaculty(long id) {
        return facultyServiceInfo.get(id);
    }

    @Override
    public Faculty updateFaculty(long id, Faculty faculty) {
        facultyServiceInfo.put(count, faculty);
        return faculty;
    }

    @Override
    public Faculty deleteFaculty(long id) {
        return facultyServiceInfo.remove(id);
    }

    public List<Faculty> facultyCollor(String collor) {
        return facultyServiceInfo.values().
                stream().filter(faculty -> faculty.getColor().equals(collor)).
                collect(Collectors.toList());

    }

}
