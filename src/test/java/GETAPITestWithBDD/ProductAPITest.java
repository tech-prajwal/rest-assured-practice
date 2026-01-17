package GETAPITestWithBDD;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class ProductAPITest {

	@Test
	public void getProductsTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		Response response = given()
			.when()
				.get("/products");
					
		System.out.println(response.statusCode());
		System.out.println(response.statusLine());
		
		response.prettyPrint();
		
		JsonPath js = response.jsonPath();
		List<Integer> idList = js.getList("id");
		System.out.println(idList);
		
		List<Double> priceList = js.getList("price");
		System.out.println(priceList);
		
		List<Double> rateList = js.getList("rating.rate");
		System.out.println(rateList);
		
		List<Integer> countList = js.getList("rating.count");
		System.out.println(countList);
		
		for(int i=0;i<idList.size();i++) {
			int id = idList.get(i);
			Object price = priceList.get(i);
			Object rate = rateList.get(i);
			int count = countList.get(i);
			
			System.out.println("id: " + id + " price: " + price + " rate: "+ rate + " count: "+ count);
		}
	}
	
	@Test
	public void getProductCountTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
		given()
			.when()
				.get("/products")
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.and()
									.body("$.size()", equalTo(20));
					
		
	}
}
