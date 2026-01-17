package GETAPITestWithBDD;

import static io.restassured.RestAssured.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;

public class GetUserWithQueryParam {

	@Test
	public void getUsersTestWithQueryParamTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.queryParam("name", "Bhadra")
				.queryParam("status", "active")
					.when()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON);
	}
	
	@Test
	public void getUsersTestWithQueryParamWithHashMapTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		Map<String, String> userQueryMap = new HashMap<String, String>();
		userQueryMap.put("name", "bhadra");
		userQueryMap.put("status", "inactive");
		userQueryMap.put("gender", "male");
		given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.queryParams(userQueryMap)
					.when()
						.get("/public/v2/users")
							.then().log().all()
								.assertThat()
									.statusCode(200)
										.and()
											.contentType(ContentType.JSON);
	}
}
