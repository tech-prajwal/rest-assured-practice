package schemavalidation;

import static io.restassured.module.jsv.JsonSchemaValidator.matchesJsonSchemaInClasspath;

import java.io.File;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class SchemaValidationTest {
	
	
	@Test
	public void getUsersAPISchemaTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		RestAssured.given()
						.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
					.when()
						.get("/public/v2/users")
					.then()
						.assertThat()
							.statusCode(200)
							.and()
							.body(matchesJsonSchemaInClasspath("getuserschema.json"));
	}
	
	
	@Test
	public void createUserAPISchemaTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		RestAssured.given()
						.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
						.body(new File("./src/test/resources/jsons/createuser.json"))
						.contentType(ContentType.JSON)
					.when()
						.post("/public/v2/users")
					.then()
						.assertThat()
							.statusCode(201)
							.and()
							.body(matchesJsonSchemaInClasspath("createuserschema.json"));
	}
	
	

}
