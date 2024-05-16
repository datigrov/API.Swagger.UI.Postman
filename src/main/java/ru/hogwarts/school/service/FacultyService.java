package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.FacultyInterface;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.Collection;
import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    private final FacultyRepository facultyRepository;

    public FacultyService(FacultyRepository facultyRepository) {
        this.facultyRepository = facultyRepository;
    }

    public Faculty createFaculty(Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public Faculty findFaculty(long id) {
        return facultyRepository.getById(id);
    }

    public Faculty editFaculty(long id, Faculty faculty) {
        return facultyRepository.save(faculty);
    }

    public void deleteFaculty(long id) {
        facultyRepository.deleteById(id);
    }

    public List<Faculty> listByColor(String color) {
        return facultyRepository.findAllByColor(color);
    }

    public List<Faculty> findByNameOrFindByColor(String name, String color) {
        return facultyRepository.findAllByNameIgnoreCaseOrColorIgnoreCase(name, color);
    }

}
