package ru.hogwarts.school.controller;

import lombok.AllArgsConstructor;
import org.springframework.http.MediaType;
import org.springframework.http.ResponseEntity;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;
import ru.hogwarts.school.model.Avatar;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.StudentService;

import java.io.IOException;
import java.util.Collection;
import java.util.stream.Collectors;

@RestController
@RequestMapping("/student")
public class StudentController {
    private final StudentService studentService;
    private final AvatarService avatarService;

    public StudentController(StudentService studentService, AvatarService avatarService) {
        this.studentService = studentService;
        this.avatarService = avatarService;
    }

    @PostMapping
    public Student createStudent(@RequestBody Student student) {
        return studentService.createStudent(student);
    }

    @GetMapping("{id}")
    public Student getStudent(@PathVariable Long id) {
        return studentService.getStudent(id);
    }

    @PostMapping(value = "/{id}/avatar", consumes = MediaType.MULTIPART_FORM_DATA_VALUE)
    public ResponseEntity<String> uploadAvatar(@PathVariable Long id, @RequestParam MultipartFile avatar) throws IOException{
        if(avatar.getSize() >= 1024 * 300){
            return ResponseEntity.badRequest().body("File too big");
        }
        avatarService.uploadAvatar(id, avatar);
        return ResponseEntity.ok().build();
    }
    @PutMapping
    public ResponseEntity<Student> updateStudent(@RequestBody Student student) {
        return ResponseEntity.ok().body(studentService.updateStudent(student));
    }

    @DeleteMapping("{id}")
    public void deleteStudent(@PathVariable Long id) {
        studentService.deleteStudent(id);
    }

    @GetMapping ("/students")
    public Collection<Student> getAllStudents() {
        return studentService.getAllStudent();
    }

    @GetMapping("/age")
    public Collection<Student> getStudentsByAge(@RequestParam int age) {
        return studentService.getStudentsByAge(age);
    }

    @GetMapping("/ageLimit")
    public Collection<Student> getStudentsByAgeLimits(@RequestParam int ageMin, @RequestParam int ageMax) {
        return studentService.getStudentsByAgeLimits(ageMin, ageMax);
    }

    @GetMapping("/{id}/avatar/db")
    public Avatar getAvatarByIdFromDb(@PathVariable Long id){
       return avatarService.findStudentsAvatar(id);
    }

    @GetMapping("{id}/avatar/file")
    public Avatar getAvatarByIdFromFile(@PathVariable Long id) throws IOException{
        return avatarService.getAvatarFromFile(id);
    }
}
