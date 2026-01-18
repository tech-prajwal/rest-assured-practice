package CreateUserWithPOJOAndLombok;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class CreateUserTestWithPOJO {
	
	public String getRandomEmailId() {
		return "apiautomation"+System.currentTimeMillis()+"@gmail.com";
	}

	@Test
	public void addUserTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		User user = new User("Jerry", getRandomEmailId(), "male", "active");
		
		//1- post -create a user using serialization
		//when we pass pojo object to body RestAsssured will automatically convert the object -> Json (serialization ) only if Jackson -databind dependency is added into pom.xml other wise it will throw the illegastate exception
		Integer userId = given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
			.contentType(ContentType.JSON)
			.body(user)
		.when()
			.post("/public/v2/users")
		.then().log().all()
			.statusCode(201)
			.extract()
			.path("id");
		
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
