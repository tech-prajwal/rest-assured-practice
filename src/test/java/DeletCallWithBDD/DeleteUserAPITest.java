package DeletCallWithBDD;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.annotations.Test;

import CreateUserWithPOJOAndLombok.User;
import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class DeleteUserAPITest {
	public String getRandomEmailId() {
		return "apiautomation" + System.currentTimeMillis() + "@opencart.com";
	}

	@Test
	public void updateUserTest() {

		// 1. post

		RestAssured.baseURI = "https://gorest.co.in";

		User user = new User("Ravi", getRandomEmailId(), "male", "inactive");

		// 1. post -- create a user using pojo to json (serialization) using
		// jackson(databind) lib
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
		System.out.println("user id : " + userId);

		System.out.println("-----------------");


		// 2. get
		given().log().all()
				.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.when()
				.get("/public/v2/users/" + userId)
				.then().log().all()
				.assertThat()
				.statusCode(200).and()
				.body("id", equalTo(userId));
		
		System.out.println("-----------------");

		
		//3. delete:
		given().log().all()
		.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
		.when()
			.delete("/public/v2/users/"+userId)
			.then().log().all()
			.assertThat()
			.statusCode(204);
			
		
		System.out.println("-----------------");

		
		// 4. get
				given().log().all()
						.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
						.when()
						.get("/public/v2/users/" + userId)
						.then().log().all()
						.assertThat()
						.statusCode(404)
						.and()
						.body("message", equalTo("Resource not found"));

	}

}
