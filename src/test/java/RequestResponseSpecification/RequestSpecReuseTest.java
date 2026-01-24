package RequestResponseSpecification;

import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestSpecReuseTest {
	
	RequestSpecification requestSpec;

	@BeforeMethod
	public void setup() {
		
	requestSpec = RestAssured.given().log().all()
			.baseUri("https://gorest.co.in")
			.header("Content-Type", "application/json")
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea");
	}
	
	@Test
	public void getUserTest() {
		requestSpec.when()
					.get("/public/v2/users")
					.then().log().all()
					.assertThat()
					.statusCode(200);
	}
	
	
	@Test
	public void getSingleUserTest() {
		requestSpec.when()
					.get("/public/v2/users/8339374")
					.then().log().all()
					.assertThat()
					.statusCode(200);
	}
	
	@Test
	public void getInvalidUserTest() {
		requestSpec.when()
					.get("/public/v2/users/8989")
					.then().log().all()
					.assertThat()
					.statusCode(404);
	}
	
	
	
}
