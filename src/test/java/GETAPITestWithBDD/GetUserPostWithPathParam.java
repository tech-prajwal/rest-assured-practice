package GETAPITestWithBDD;

import static io.restassured.RestAssured.given;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import static org.hamcrest.Matchers.*;

import java.util.HashMap;
import java.util.Map;

import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;

public class GetUserPostWithPathParam {

	@DataProvider
	public Object[][] getUserData() {
		return new Object[][] {
			{8333304, "qwe"},
			{8333169,"SampleTitle1"},
			{8333163,"TestTester Guy"}
		};
	}
	
	
	@Test(dataProvider = "getUserData")
	public void getUserPostWithPathParamTest(int userID, String title) {
		RestAssured.baseURI ="https://gorest.co.in";
		
		given().log().all()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.pathParam("userId", userID)
		.when()
			.get("/public/v2/users/{userId}/posts")
		.then().log().all()
			.assertThat()
				.statusCode(200)
				.and()
				.body("title", hasItem(title));
	}
	
	@Test()
	public void getUserWithPathParamUsingHashMapTest() {
		
		RestAssured.baseURI = "https://reqres.in";
		
		Map<String, String> pathParamMap = new HashMap<String, String>();
		pathParamMap.put("firstpath", "api");
		pathParamMap.put("secondpath", "users");

		given().log().all()
			.pathParams(pathParamMap)
			.queryParam("page", 2)
		.when()
			.get("/{firstpath}/{secondpath}")
		.then().log().all()
			.assertThat()
				.statusCode(200);
	}
}
