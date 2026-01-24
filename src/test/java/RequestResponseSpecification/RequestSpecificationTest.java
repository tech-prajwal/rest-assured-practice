package RequestResponseSpecification;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.specification.RequestSpecification;

public class RequestSpecificationTest {

	@Test
	public void reqSpecTest() {
		RequestSpecification requestSpec = RestAssured.given().log().all()
						.baseUri("https://jsonplaceholder.typicode.com")
						.header("Content-Type", "application/json");
						
		
			requestSpec.get("/posts")
							.then().log().all()
							.statusCode(200);
						
			requestSpec.get("/comments")
			.then().log().all()
			.statusCode(200);
			
			requestSpec.body("{\r\n"
					+ "    title: 'foo',\r\n"
					+ "    body: 'bar',\r\n"
					+ "    userId: 1,\r\n"
					+ "  }")
						.post("/posts")
						.then().log().all()
						.statusCode(200);
	}
	
	
	@Test
	public void reqSpecGoRestTest() {
		RequestSpecification requestSpec = RestAssured.given().log().all()
						.baseUri("https://gorest.co.in")
						.header("Content-Type", "application/json")
						.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea");
		
		requestSpec.when()
						.get("/public/v2/users")
						.then().log().all()
						.statusCode(200);
		
	}
	
	@Test
	public void reqSpecGoRestAPIWithQueryParamTest() {
		RequestSpecification requestSpec = RestAssured.given().log().all()
						.baseUri("https://gorest.co.in")
						.header("Content-Type", "application/json")
						.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
						.queryParam("status", "inactive")
						.queryParam("name", "prajwal");
		
		requestSpec.when()
						.get("/public/v2/users")
						.then().log().all()
						.statusCode(200);
		
	}
	
	
	
	
}
