package POSTAPITests;

import static io.restassured.RestAssured.given;

import java.io.File;
import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Paths;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

public class CreateUserTest {

	public String getRandomEmailId() {
		return "apiautomation"+System.currentTimeMillis()+"@gmail.com";
	}
	
	@Test
	public void createUserWithJSONFileWithStringReplacementTest() throws IOException {
		RestAssured.baseURI ="https://gorest.co.in";
		String emailId = getRandomEmailId();
		
		//convert json file content to string
		String rawJson = new String(Files.readAllBytes(Paths.get("./src/test/resources/jsons/user.json")));
		String updatedJson = rawJson.replace("{{email}}", emailId);
	
		//1- post -create a user
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
		
		//2 - get a user --GET
		
		given().log().all()
			.header("Authorization","Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.when()
					.get("/public/v2/users/"+userId)
						.then().log().all()
							.assertThat()
							.statusCode(200)
								.and()
									.body("id", equalTo(userId));
		
	}
	
}
