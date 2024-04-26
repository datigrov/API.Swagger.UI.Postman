package ru.hogwarts.school.controller;

import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.service.FacultyService;

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
        Faculty newFaculty = facultyService.getFaculty(id);
        if (newFaculty == null) {
            return ResponseEntity.notFound().build();
        }
        return ResponseEntity.ok(newFaculty);
    }

    @PutMapping("{id}")
    public ResponseEntity<Faculty> updateFacultyes(@RequestBody Faculty faculty, @PathVariable Long id) {
        Faculty changeFaculty = facultyService.updateFaculty(id, faculty);
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

    @RequestMapping("/collor")
    public List<Faculty> collorsOfFaculty(@RequestParam String collor) {
        return facultyService.facultyCollor(collor);
    }
}
