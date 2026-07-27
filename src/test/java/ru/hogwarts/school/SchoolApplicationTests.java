package ru.hogwarts.school;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.http.HttpStatusCode;
import org.springframework.http.ResponseEntity;
import org.springframework.web.client.RestTemplate;
import ru.hogwarts.school.controller.StudentController;
import ru.hogwarts.school.model.Student;

@SpringBootTest(webEnvironment = SpringBootTest.WebEnvironment.RANDOM_PORT)
class SchoolApplicationTests {
	@Autowired
	private StudentController studentController;

	@LocalServerPort
	private int port;

	@Autowired
	private RestTemplate restTemplate;


	@Test
	void contextLoads() throws Exception {
		Assertions.assertThat(studentController).isNotNull();
	}

	@Test
	void testGetAllStudent() throws Exception{
		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/students", String.class))
				.isNotNull();
	}

	@Test
	void testCreateStudent() throws Exception{
		Student student = new Student();
		student.setName("Daun");
		student.setAge(22);
		Assertions
				.assertThat(this.restTemplate.postForObject("http://localhost:" + port + "/student", student, String.class))
				.isNotNull();

	}
}
