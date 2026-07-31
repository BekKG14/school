package ru.hogwarts.school;

import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.FacultyController;
import ru.hogwarts.school.model.Faculty;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
public class FacultyTest {
    @Autowired
    private FacultyController facultyController;

    @LocalServerPort
    private int port;

    @Autowired
    private RestTemplate restTemplate;

    @Test
    void contextLoads() throws Exception {
        Assertions.assertThat(facultyController).isNotNull();
    }

    @Test
    void testGetAllStudent() throws Exception {
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/faculties", String.class))
                .isNotNull();
    }

    @Test
    void testCreateFaculty() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Grifenpifen");
        faculty.setColor("Yellow");
        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty, Faculty.class);
        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/" + newFaculty.getId(), Faculty.class))
                .isNotNull();
        Assertions
                .assertThat(newFaculty).isNotNull();
        Assertions.assertThat(newFaculty.getId()).isNotNull();

        restTemplate.delete("http://localhost:" + port + "/faculties/" + newFaculty.getId());

        Assertions
                .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/faculties/" + newFaculty.getId(), Faculty.class))
                .isNull();
    }


    @Test
    void testUpdateStudent() throws Exception{
            Faculty faculty = new Faculty();
            faculty.setName("Grifenpifen");
            faculty.setColor("Yellow");
            Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty, Faculty.class);

        newFaculty.setName("Pufinhafin");
        newFaculty.setColor("Green");
        restTemplate.put("http://localhost:" + port + "/faculties", newFaculty, Faculty.class);
        Faculty newFaculty1 = restTemplate.getForObject("http://localhost:" + port + "/faculties/" + newFaculty.getId(), Faculty.class);
        Assertions
                .assertThat(newFaculty1.getName()).isEqualTo("Pufinhafin");
        Assertions.assertThat(newFaculty1.getColor()).isEqualTo("Green");
    }

    @Test
    void testGetFacultiesByColor() throws Exception {
        Faculty faculty = new Faculty();
        faculty.setName("Grifenpifen");
        faculty.setColor("Yellow");

        Faculty faculty2 = new Faculty();
        faculty2.setName("Grifenpifen");
        faculty2.setColor("Yellow");

        Faculty faculty3 = new Faculty();
        faculty3.setName("NoGrifen");
        faculty3.setColor("Red");

        Faculty newFaculty = restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty, Faculty.class);
        Faculty newFaculty2 = restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty2, Faculty.class);
        Faculty newFaculty3 = restTemplate.postForObject("http://localhost:" + port + "/faculties", faculty3, Faculty.class);

        Assertions
                .assertThat(restTemplate.getForObject("http://localhost:" + port + "/faculties/color/yellow", Faculty[].class))
                .hasSize(2);
    }


}
