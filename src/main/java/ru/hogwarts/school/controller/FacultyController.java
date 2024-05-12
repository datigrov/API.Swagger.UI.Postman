package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

import javax.swing.*;
import java.util.List;

@RestController
@RequestMapping("/faculty")
public class FacultyController {

    private FacultyService facultyService;

    public FacultyController(FacultyService facultyService) {
        this.facultyService = facultyService;
    }

    @PostMapping
    public Faculty createFacultyes(@RequestBody Faculty faculty) {
        return facultyService.createFaculty(faculty);
    }

    @GetMapping("{id}")
    public ResponseEntity<Faculty> getFacultyes(@PathVariable Long id) {
        Faculty newFaculty = facultyService.findFaculty(id);
        if (newFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newFaculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFacultyes(@RequestBody Faculty faculty, @PathVariable Long id) {
        Faculty changeFaculty = facultyService.editFaculty(id, faculty);
        if (changeFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(changeFaculty);
    }

    @DeleteMapping("{id}")
    public ResponseEntity<Faculty> deleteFacultyes(@PathVariable Long id) {
        facultyService.deleteFaculty(id);
        return ResponseEntity.ok().build();
    }

    @RequestMapping
    public List<Faculty> colorsOfFaculty(@RequestParam String color) {
        return facultyService.facultyColor(color);
    }

    @GetMapping("/nameAndColor")
    public ResponseEntity<Faculty> findByNameIgnoreCaseAndFindCollorIgnoreCase(@RequestParam String name,
            @RequestParam (required = false) String color) {
        if (name != null && !name.isBlank() && color != null && !color.isBlank()) {
            return ResponseEntity.ok(facultyService.findByNameIgnoreCaseAndFindCollorIgnoreCase(name, color));
        }
        return ResponseEntity.ok().build();
    }

    @GetMapping("{id}/student")
    public ResponseEntity getFacultiesByStudents(@PathVariable Long id) {
        Faculty faculty = facultyService.findFaculty(id);
        if (faculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(facultyService.findFaculty(id).getStudents());
    }
}
