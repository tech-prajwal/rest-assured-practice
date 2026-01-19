package DeserializationWithJSONArrayResponse;
import static io.restassured.RestAssured.given;

import org.testng.annotations.Test;

import com.fasterxml.jackson.core.JsonProcessingException;
import com.fasterxml.jackson.databind.JsonMappingException;
import com.fasterxml.jackson.databind.ObjectMapper;

import io.restassured.RestAssured;
import io.restassured.response.Response;


public class GetAllUsersTest {

	
	@Test
	public void getAllUsersTest() {
		
		RestAssured.baseURI = "https://gorest.co.in";
		
		Response response = given()
		.header("Authorization", "Bearer a77b03df858ed8ed5c1f1593ac495c974603adc5df88fe84fd4250b7361a3cea")
		.when()
			.get("/public/v2/users");
		
		response.prettyPrint();
		
		//Deserilization: json to pojo
		
		ObjectMapper mapper =  new ObjectMapper();
		try {
			//json arrays as response ---> User[].class
			User[] userRes = mapper.readValue(response.getBody().asString(), User[].class);
			
			for(User user : userRes) {
				System.out.println("id: "+ user.getId());
				System.out.println("name: "+ user.getName());
				System.out.println("email: "+ user.getEmail());
				System.out.println("gender: "+ user.getGender());
				System.out.println("status: "+ user.getStatus());
			}
			
			
			
		} catch (JsonMappingException e) {
			e.printStackTrace();
		} catch (JsonProcessingException e) {
			e.printStackTrace();
		}
		
		
	}
}
