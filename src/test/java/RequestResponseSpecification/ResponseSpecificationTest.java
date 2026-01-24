package RequestResponseSpecification;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.specification.ResponseSpecification;

import static io.restassured.RestAssured.expect;
import static io.restassured.RestAssured.given;

public class ResponseSpecificationTest {

	@Test
	public void responseSpecTest() {
		ResponseSpecification resSpec = expect()
		.statusCode(200)
		.header("Content-Type", "application/json; charset=utf-8")
		.header("Server", "cloudflare");
		
		given().log().all()
			.baseUri("https://gorest.co.in")
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
		.when()
			.get("/public/v2/users")
		.then().log().all()
			.spec(resSpec);
				
	}
	
	
	
	
	
}
