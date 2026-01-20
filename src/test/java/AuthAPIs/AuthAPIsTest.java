package AuthAPIs;

import static org.hamcrest.Matchers.*;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AuthAPIsTest {

	//basic
	//digestive
	//apikey
	//Oauth 1.0
	//Oauth 2.0
	//JWT
	//Bearer
	
	@Test
	public void basicAuthAPITest() {
		RestAssured.baseURI = "https://the-internet.herokuapp.com";
		
		Response response =RestAssured.given().log().all()
						.auth()
						.basic("admin", "admin")
					.when()
						.get("/basic_auth");
		
		response.prettyPrint();
		Assert.assertTrue(response.asString().contains("Congratulations! You must have the proper credentials."));
		Assert.assertEquals(response.statusCode(), 200);
					
	}
	
	@Test
	public void digestAuthAPITest() {
		RestAssured.baseURI = "https://postman-echo.com";
		
		RestAssured.given().log().all()
						.auth()
						.digest("postman", "password")
					.when()
						.get("/digest-auth")
					.then()
						.assertThat()
						.statusCode(200)
						.and()
						.body("authenticated", equalTo(true));
		
					
	}
	
	//depricated, low security, not much used
	/*
	BASIC AUTH vs PREEMPTIVE BASIC AUTH

	Basic Auth:
	- First request WITHOUT credentials
	- Server sends 401 challenge
	- Second request with credentials
	- Slower (2 network calls)

	Example:
	given().auth().basic("user", "pass");

	--------------------------------------------------

	Preemptive Basic Auth:
	- Credentials sent in FIRST request
	- No 401 challenge
	- Faster (1 network call)
	- Preferred for automation

	Example:
	given().auth().preemptive().basic("user", "pass");

	--------------------------------------------------

	Interview one-liner:
	Preemptive auth avoids 401 challenge by sending credentials in first request,
	making it faster than basic auth.
	*/
	@Test
	public void preemptiveAuthAPITest() {
			RestAssured.baseURI = "https://the-internet.herokuapp.com";
		
			RestAssured.given().log().all()
					.auth()
						.preemptive().basic("admin", "admin")
					.when()
						.get("/basic_auth")
							.then().log().all()
								.assertThat().statusCode(200);
		
					
	}
	
	
	
	
}
