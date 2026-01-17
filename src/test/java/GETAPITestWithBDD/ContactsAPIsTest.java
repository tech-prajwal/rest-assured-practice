package GETAPITestWithBDD;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType; 

public class ContactsAPIsTest {

	@BeforeMethod
	public void setupd() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
	}
	
	@Test
	public void getContactsAPITest() {
		given().log().all()
			.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2OTZhZmVhYzhlMDZmNTAwMTUyNGVkZWEiLCJpYXQiOjE3Njg2MTk3MTB9.CAy8GSIrwNce_AJx4I5BeZXdDQ5pZRkElacsudehO5w")
					.when()
						.get("/contacts")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.contentType(ContentType.JSON);
	}
	
	@Test
	public void getContactsAPIAuthErrorTest() {
		given().log().all()
			.header("Authorization", "Bearer TestInvalid")
					.when()
						.get("/contacts")
							.then().log().all()
								.assertThat()
									.statusCode(401);
	}
	
	@Test
	public void getContactsAPIInvalidTokenTest() {
		String errorMessage =given().log().all()
								.header("Authorization", "Bearer --testInvalid")
									.when()
										.get("/contacts")
											.then().log().all()
												.extract().path("error");
		System.out.println(errorMessage);
		Assert.assertEquals(errorMessage, "Please authenticate.");
	}
	
	
	
	
	
	
}
