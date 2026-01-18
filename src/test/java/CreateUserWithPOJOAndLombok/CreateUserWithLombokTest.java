package CreateUserWithPOJOAndLombok;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class CreateUserWithLombokTest {

	public String getRandomEmailId() {
		return "virat"+System.currentTimeMillis()+"@gmail.com";
	}

	@Test
	public void addUserLombokTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		UserLombok user = new UserLombok("prajwal", getRandomEmailId(), "male", "active");
		
		System.out.println(user);
		
		//1- post -create a user using serialization
		//here UserLombok pojo class is created with lombok pattern
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
	
	@Test
	public void addUserLombokBuilderTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		//builder patter using lombok
		
		UserLombok user = new UserLombok.UserLombokBuilder()
						.name("Sunita")
						.email(getRandomEmailId())
						.gender("female")
						.status("active")
						.build();
		
		System.out.println(user);
		
		//1- post -create a user using serialization
		//here UserLombok pojo class is created with lombok pattern
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
	
	@Test
	public void addUserLombokBuilderWithGetterTest() {
		RestAssured.baseURI = "https://gorest.co.in";
		
		//builder patter using lombok
		
		UserLombok user = new UserLombok.UserLombokBuilder()
						.name("Sunita")
						.email(getRandomEmailId())
						.gender("female")
						.status("active")
						.build();
		
		System.out.println(user);
		
		//1- post -create a user using serialization
		//here UserLombok pojo class is created with lombok pattern
				Response response = given().log().all()
					.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
					.contentType(ContentType.JSON)
					.body(user)
				.when()
					.post("/public/v2/users");
				
				JsonPath js = response.jsonPath();
				
				Assert.assertEquals(js.getString("name"), user.getName());
				Assert.assertEquals(js.getString("gender"), user.getGender());
				Assert.assertEquals(js.getString("status"), user.getStatus());
				
	}
	
	//Practice
	@Test
	public void addContactTestLombok() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		
		ContactsLombok contact = new ContactsLombok("Virat", "Kolhi", "1986-12-12", getRandomEmailId(), "094689297398", "Bull Road", "Bear Road", "Bombay", "43267");
		
		Object id= given().log().all()
			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2OTZhZmVhYzhlMDZmNTAwMTUyNGVkZWEiLCJpYXQiOjE3Njg3MDczNDV9.h-be_JpVLh6mg2JV3ITl-CIcHko73hg0LUFG44XMOhY")
			.contentType(ContentType.JSON)
			.body(contact)
		.when()
			.post("/contacts")
		.then().log().all()
			.assertThat()
				.statusCode(201)
				.and()
				.extract()
				.path("_id");
			
		System.out.println("id is : " + id);
		
		given().log().all()
			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2OTZhZmVhYzhlMDZmNTAwMTUyNGVkZWEiLCJpYXQiOjE3Njg3MDczNDV9.h-be_JpVLh6mg2JV3ITl-CIcHko73hg0LUFG44XMOhY")
		.when()
			.get("/contacts/"+id)
		.then().log().all()
			.assertThat()
			.statusCode(200)
			.and()
			.body("_id", equalTo(id));
			
		
	}
}
