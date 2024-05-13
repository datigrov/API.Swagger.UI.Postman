package ru.hogwarts.school.service;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import ru.hogwarts.school.interfaceUniversity.FacultyInterface;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.repository.FacultyRepository;


import java.util.HashMap;
import java.util.List;
import java.util.Objects;
import java.util.stream.Collectors;

@Service
public class FacultyService{

    @Autowired
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

    public List<Faculty> findColor(String color) {
        return facultyRepository.findAll().stream().
                filter(faculty -> Objects.equals(faculty.getColor(), color)).
                collect(Collectors.toList());

    }

    public Faculty findByNameIgnore(String name) {
        return facultyRepository.findByNameIgnoreCase(name);
    }

    public Faculty findByColorIgnoreCase(String color) {
        return facultyRepository.findByColorIgnoreCase(color);
    }

}
