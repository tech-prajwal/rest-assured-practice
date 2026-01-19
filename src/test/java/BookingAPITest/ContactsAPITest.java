package BookingAPITest;

import static io.restassured.RestAssured.given;
import static org.hamcrest.Matchers.equalTo;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;

public class ContactsAPITest {

	String tokenID;
	String newUserId;
	
	@BeforeTest
	public void getToken() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		
		 tokenID = given().log().all()
			.contentType(ContentType.JSON)
			.body("{\r\n"
					+ "    \"email\": \"testprajwal@gmail.com\",\r\n"
					+ "    \"password\": \"password\"\r\n"
					+ "}")
			.when()
			.post("/users/login")
			.then().log().all()
			.assertThat().statusCode(200)
				.extract().path("token");
		System.out.println("token id: "+ tokenID);
	}
	
	@BeforeMethod
	public void setup() {
		newUserId = addUser();
	}
	
	@Test
	public void getUserTest() {		
		given().log().all()
			.header("Authorization", "Bearer "+ tokenID)
			.when()
				.get("users/me")
				.then().log().all()
					.assertThat()
					.statusCode(200)
					.and()
					.body("firstname", equalTo("Prajwal"))
					.and()
					.body("lastname", equalTo("Test"));

	}
	
	@Test
	public void createBookingTest() {
		Assert.assertNotNull(newUserId);
	}
	
	@Test
	public void updateUserTest() {
		
		Users updatedCreds = new Users.UsersBuilder()
				.firstName("Test")
				.lastName("Tester")
				.email(getRandomEmailId())
				.password("forgotpassword")
				.build();
		
		given().log().all()
			.header("Authorization", "Bearer "+ tokenID)
			.body(updatedCreds)
			.when()
				.put("/users/me")
				.then().log().all()
				.assertThat()
					.statusCode(200)
						.and()
						.body("firstname", equalTo("Test"))
						.and()
						.body("lastName", equalTo("Tester"));
	}
	
	
	@Test
	public void deleteUserTest() {
		given().log().all()
			.header("Authorization", "Bearer "+ tokenID)
			.when()
				.delete("users/me")
				.then().log().all()
				.assertThat()
					.statusCode(201);
						
	}
	
	public String getRandomEmailId() {
		return "virat"+System.currentTimeMillis()+"@gmail.com";
	}
	
	public String addUser() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		
		Users creds = new Users.UsersBuilder()
				.firstName("Test")
				.lastName("Tester")
				.email(getRandomEmailId())
				.password("forgotpassword")
				.build();
		
		String userId = given().log().all()
			.contentType(ContentType.JSON)
			.header("Authorization", "Bearer "+tokenID)
			.body(creds)
			.when()
				.post("/users")
				.then().log().all()
				.assertThat().statusCode(201)
				.extract().path("user._id");
		System.out.println("new user id: "+ userId);
		return userId;
	}
}
