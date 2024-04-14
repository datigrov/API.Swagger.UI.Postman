package ru.hogwarts.school.service;

import org.springframework.stereotype.Service;
import ru.hogwarts.school.model.Faculty;


import java.util.HashMap;
import java.util.List;
import java.util.stream.Collectors;

@Service
public class FacultyService {
    private final HashMap<Long, Faculty> facultyServiceInfo = new HashMap<>();
    private Long generateFacultyId = 0L;

    public Faculty createFaculty(Faculty faculty) {
        facultyServiceInfo.put(generateFacultyId, faculty);
        generateFacultyId++;
        return faculty;
    }

    public Faculty getFaculty(Long facultyId) {
        return facultyServiceInfo.get(facultyId);
    }

    public Faculty updateFaculty(Long facultyId, Faculty faculty) {
        facultyServiceInfo.put(generateFacultyId, faculty);
        return faculty;
    }

    public Faculty deliteFaculty(Long facultytId) {
        return facultyServiceInfo.remove(facultytId);
    }

    public List<Faculty> facultyCollor(String collor) {
        return facultyServiceInfo.values().
                stream().filter(faculty -> faculty.getColor().equals(collor)).
                collect(Collectors.toList());

    }

}
