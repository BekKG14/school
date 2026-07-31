package ru.hogwarts.school;

import com.fasterxml.jackson.annotation.JsonTypeInfo;
import org.assertj.core.api.Assertions;
import org.junit.jupiter.api.Test;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.boot.test.web.server.LocalServerPort;
import org.springframework.core.io.ByteArrayResource;
import org.springframework.http.*;
import org.springframework.util.LinkedMultiValueMap;
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
	void testGetAllStudent() throws Exception {
		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/students", String.class))
				.isNotNull();
	}

	@Test
	void testCreateStudent() throws Exception {
		Student student = new Student();
		student.setName("Lexa");
		student.setAge(22);
		Student newStudent = restTemplate.postForObject("http://localhost:" + port + "/student", student, Student.class);
		System.out.println(newStudent.getId());
		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class))
				.isNotNull();
		Assertions
				.assertThat(newStudent).isNotNull();
		Assertions.assertThat(newStudent.getId()).isNotNull();

		restTemplate.delete("http://localhost:" + port + "/student/" + newStudent.getId());

		Assertions
				.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + newStudent.getId(), Student.class))
				.isNull();
	}

	@Test
	void testUpdateStudent() throws Exception{
		Student student = new Student();
		student.setAge(22);
		student.setName("Dima");

		Student firstStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student, Student.class);

		firstStudent.setName("Alex");
		firstStudent.setAge(32);
		restTemplate.put("http://localhost:" + port + "/student" , firstStudent);
		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/" + firstStudent.getId(), Student.class))
				.isEqualTo(firstStudent);
	}

	@Test
	void testGetStudentByAge() throws Exception {
		Student student = new Student();
		student.setAge(22);
		student.setName("Dima");

		Student student2 = new Student();
		student2.setAge(32);
		student2.setName("Alex");

		Student student3 = new Student();
		student3.setAge(32);
		student3.setName("Vova");




		Student firstStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student, Student.class);
		Student secondStudent = restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
		Student thirdStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student3, Student.class);

		Assertions.assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/age?age=32", Student[].class))
				.hasSize(2);
	}

	@Test
	void testGetStudentByAgeLimits() throws Exception{
		Student student = new Student();
		student.setAge(22);
		student.setName("Dima");

		Student student2 = new Student();
		student2.setAge(32);
		student2.setName("Alex");

		Student student3 = new Student();
		student3.setAge(32);
		student3.setName("Vova");

		Student student4 = new Student();
		student4.setAge(31);
		student4.setName("Igor");

		Student student5 = new Student();
		student5.setAge(28);
		student5.setName("Lera");

		Student student6 = new Student();
		student6.setAge(18);
		student6.setName("Kola");

		Student firstStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student, Student.class);
		Student secondStudent = restTemplate.postForObject("http://localhost:" + port + "/student", student2, Student.class);
		Student thirdStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student3, Student.class);
		Student fourthStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student4, Student.class);
		Student fifthStudent = restTemplate.postForObject("http://localhost:" + port + "/student", student5, Student.class);
		Student sixthStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student6, Student.class);

		Assertions
		 .assertThat(this.restTemplate.getForObject("http://localhost:" + port + "/student/ageLimit?ageMin=19&ageMax=30", Student[].class))
				.hasSize(2);
	}

	@Test
	void testUploadAvatar() throws Exception{
		byte[] bytes = new byte[100 * 1024];
		Student student = new Student();
		student.setAge(22);
		student.setName("Dima");

		Student firstStudent = restTemplate.postForObject("http://localhost:" + port + "/student" , student, Student.class);

		ByteArrayResource fileResource = new ByteArrayResource(bytes){

		@Override
		public String getFilename(){
			return "avatar.jpg";
		}};
		LinkedMultiValueMap<String, Object> body = new LinkedMultiValueMap<>();
		body.add("avatar", fileResource);

		HttpHeaders headers = new HttpHeaders();
		headers.setContentType(MediaType.MULTIPART_FORM_DATA);
		HttpEntity<LinkedMultiValueMap<String, Object>> requestEntity = new HttpEntity<>(body, headers);
		ResponseEntity<String> response = restTemplate.exchange(
				"http://localhost:" + port + "/student/" + firstStudent.getId() + "/avatar",
				HttpMethod.POST,
				requestEntity,
				String.class);
	}
}