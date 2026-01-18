package GetCallWithDeserialization;

import static io.restassured.RestAssured.given;

import org.testng.Assert;
import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

public class GetAUserTest {

	
	@Test
	public void getSingleUserTest() {
		RestAssured.baseURI ="https://gorest.co.in";
		
		Response response= given()
			.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
				.when()
					.get("/public/v2/users/8335400");
		
		//deserialization
		response.prettyPrint();
		String responseBody = response.getBody().asString();
		ObjectMapper mapper = new ObjectMapper();
		
		try {
			//using pojo class
//			User userRes = mapper.readValue(responseBody, User.class);
			
			//using lombok pojo class
			UserLombok userRes = mapper.readValue(responseBody, UserLombok.class);
			System.out.println(userRes);
			
			Assert.assertEquals(userRes.getName(), "prajwal");
			Assert.assertEquals(userRes.getEmail(), "apiautomation1768707247820@gmail.com");
			Assert.assertEquals(userRes.getStatus(), "active");
			Assert.assertEquals(userRes.getId(), 8335400);
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}

		
		
		
	}
	
	
	
	
	
	
	
	
	
	
}
