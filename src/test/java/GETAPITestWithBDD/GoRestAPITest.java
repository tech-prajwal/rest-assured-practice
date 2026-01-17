package GETAPITestWithBDD;

import static io.restassured.RestAssured.*;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GoRestAPITest {
	
	@Test
	public void getSingleUserTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		Response response= given()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.when()
					.get("/public/v2/users/8333304");
		System.out.println("Status Code: " + response.statusCode());
		System.out.println("Status Line: " + response.statusLine());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));
		
		response.prettyPrint(); //prints in formatted format rather than single line
		
		//fetch the json body
		
		JsonPath js = response.jsonPath();
		int userid = js.getInt("id");
		System.out.println("user id: " + userid);
		Assert.assertEquals(userid, 8333304);
		
		String name = js.getString("name");
		System.out.println("user name: " + name);
		Assert.assertEquals(name, "Bhadra Dwivedi");
		
		String userStatus = js.getString("status");
		System.out.println("user status: " + userStatus);
		Assert.assertEquals(userStatus, "active");
	}

	@Test
	public void getAllUsersTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		Response response= given()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.when()
					.get("/public/v2/users");
		System.out.println("Status Code: " + response.statusCode());
		System.out.println("Status Line: " + response.statusLine());
		
		Assert.assertEquals(response.statusCode(), 200);
		Assert.assertTrue(response.statusLine().contains("OK"));
		
		response.prettyPrint();
		
		JsonPath js = response.jsonPath();
		
		List<Integer> idList = js.getList("id");
		System.out.println(idList);
		
		List<Integer> nameList = js.getList("name");
		System.out.println(nameList);
		
		
	}
}
