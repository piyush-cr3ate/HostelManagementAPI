package com.kiet.hostelmanagementapi.controller;

import com.kiet.hostelmanagementapi.entity.HostelStudent;
import com.kiet.hostelmanagementapi.repository.HostelStudentRepository;

import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.http.HttpStatus;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Optional;

@RestController
@RequestMapping("/students")
public class HostelStudentController {

    @Autowired
    private HostelStudentRepository repository;

    // (c) Get all students
    @GetMapping
    public List<HostelStudent> getAllStudents() {
        return repository.findAll();
    }

    // (b) Add a student
    @PostMapping
    public ResponseEntity<HostelStudent> addStudent(@RequestBody HostelStudent student) {
        return new ResponseEntity<>(repository.save(student), HttpStatus.CREATED);
    }

    // (c) Get student by ID
    @GetMapping("/{id}")
    public ResponseEntity<?> getStudentById(@PathVariable Long id) {
        Optional<HostelStudent> student = repository.findById(id);
        if (student.isPresent()) {
            return ResponseEntity.ok(student.get());
        }
        return ResponseEntity.status(HttpStatus.NOT_FOUND)
                .body("Student not found with id " + id);
    }

    // (d) Update hostel name, room number and fee
    @PutMapping("/{id}")
    public ResponseEntity<?> updateStudent(@PathVariable Long id,
                                           @RequestBody HostelStudent updated) {
        Optional<HostelStudent> existing = repository.findById(id);
        if (existing.isEmpty()) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with id " + id);
        }
        HostelStudent student = existing.get();
        student.setHostelName(updated.getHostelName());
        student.setRoomNumber(updated.getRoomNumber());
        student.setFee(updated.getFee());
        return ResponseEntity.ok(repository.save(student));
    }

    // (e) Delete student
    @DeleteMapping("/{id}")
    public ResponseEntity<String> deleteStudent(@PathVariable Long id) {
        if (!repository.existsById(id)) {
            return ResponseEntity.status(HttpStatus.NOT_FOUND)
                    .body("Student not found with id " + id);
        }
        repository.deleteById(id);
        return ResponseEntity.ok("Student deleted successfully");
    }
}