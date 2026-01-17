package POSTAPITests;

import static io.restassured.RestAssured.*;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserAPITest {

	public String getRandomEmailId() {
		return "apiautomation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	@Test
	public void createUserWithJSONStringTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		String emailId = getRandomEmailId();
		given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
			.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "\"name\": \"ABC\",\r\n"
					+ "\"gender\": \"male\",\r\n"
					+ "\"email\": \""+emailId+"\",\r\n"
					+ "\"status\":\"active\"\r\n"
					+ "}")
			.when()
				.post("/public/v2/users")
			.then().log().all()
				.assertThat()
				.statusCode(201);
	}
	
	@Test
	public void createUserWithJSONFileTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
			.contentType(ContentType.JSON)
			.body(new File("./src/test/resources/jsons/user.json"))
			.when()
				.post("/public/v2/users")
			.then().log().all()
				.assertThat()
				.statusCode(201);
	}
	
	//same as above test with different url
//	@Test
//	public void createContactWithJSONFileTest() {
//		RestAssured.baseURI ="https://thinking-tester-contact-list.herokuapp.com";
//		
//		given().log().all()
//			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2OTZhZmVhYzhlMDZmNTAwMTUyNGVkZWEiLCJpYXQiOjE3Njg2MTk3MTB9.CAy8GSIrwNce_AJx4I5BeZXdDQ5pZRkElacsudehO5w")
//			.contentType(ContentType.JSON)
//			.body(new File("./src/test/resources/jsons/contacts.json"))
//			.when()
//				.post("/contacts")
//			.then().log().all()
//				.assertThat()
//				.statusCode(201);
//	}
	
	@Test
	public void createUserWithJSONFileWithStringReplacementTest() throws IOException {
		RestAssured.baseURI ="https://gorest.co.in";
		String emailId = getRandomEmailId();
		
		//convert json file content to string
		String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		String updatedJson = rawJson.replace("{{email}}", emailId);
	
		Integer userId = given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
			.contentType(ContentType.JSON)
			.body(updatedJson)
			.when()
				.post("/public/v2/users")
			.then().log().all()
				.assertThat()
				.statusCode(201)
					.extract()
						.path("id");
		System.out.println("user id: "+ userId);
	}
	
}
