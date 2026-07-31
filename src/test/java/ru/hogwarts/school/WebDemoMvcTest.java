package ru.hogwarts.school;


import org.json.JSONObject;
import org.junit.jupiter.api.Test;
import org.mockito.InjectMocks;
import org.mockito.Spy;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.autoconfigure.web.servlet.WebMvcTest;
import org.springframework.boot.test.mock.mockito.MockBean;
import org.springframework.test.web.servlet.MockMvc;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;
import ru.hogwarts.school.repository.AvatarRepository;
import ru.hogwarts.school.repository.FacultyRepository;
import ru.hogwarts.school.repository.StudentRepository;
import ru.hogwarts.school.service.AvatarService;
import ru.hogwarts.school.service.FacultyService;
import ru.hogwarts.school.service.StudentService;

@WebMvcTest
public class WebDemoMvcTest {

    @Autowired
    private MockMvc mockMvc;

    @MockBean
    private FacultyRepository facultyRepository;

    @MockBean
    private StudentRepository studentRepository;

    @MockBean
    private AvatarRepository avatarRepository;

    @Spy
    private StudentService studentService;

    @Spy
    private FacultyService facultyService;

    @Spy
    private AvatarService avatarService;

    @InjectMocks
    private StudentController studentController;

    @InjectMocks
    private FacultyController facultyController;

    @Test
    public void saveStudentTest() throws Exception{
        JSONObject studentObject = new JSONObject();

        Student student = new Student();
        student.setId(1L);
        student.setName("Name");
        student.setAge(11);
        student.
    }
}
