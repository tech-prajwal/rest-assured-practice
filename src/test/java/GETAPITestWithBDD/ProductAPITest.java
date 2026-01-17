package GETAPITestWithBDD;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import static io.restassured.RestAssured.*;
import static org.hamcrest.Matchers.*;

import java.util.List;

public class ProductAPITest {

	/*
	 * TECHNICAL NOTE: Parsing Nested JSON & List Extraction
	 * -----------------------------------------------------
	 * 1. JsonPath Initialization:
	 * `response.jsonPath()` converts the raw JSON string into a queryable object, allowing
	 * us to extract specific values using GPath expressions.
	 *
	 * 2. Deep Traversal (Dot Notation):
	 * - `js.getList("id")`: Extracts 'id' from every object in the root array.
	 * - `js.getList("rating.rate")`: Uses dot notation to traverse deeper. It goes into 
	 * the 'rating' object of *each* product to fetch the 'rate' value.
	 *
	 * 3. Data Correlation:
	 * Since the response is an ordered array, all extracted Lists (IDs, prices, rates) 
	 * maintain the same index alignment (Index 0 in 'idList' corresponds to Index 0 in 'rateList').
	 * The loop relies on this alignment to safely print the data row-by-row.
	 */
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
	
	/*
	 * TECHNICAL NOTE: Understanding $.size()
	 * --------------------------------------
	 * This assertion uses a Groovy GPath (JSONPath) expression:
	 * 1. "$" represents the root of the JSON response.
	 * 2. ".size()" is a built-in function that counts items in the root Array (List).
	 * * Logic: It calculates the total count of elements in the JSON array 
	 * and asserts that it matches the expected value (20).
	 */
	@Test
	public void getProductCountTest() {
		RestAssured.baseURI = "https://fakestoreapi.com";
		
			get("/products")
					.then().log().all()
						.assertThat()
							.statusCode(200)
								.and()
									.body("$.size()", equalTo(20));  //
					
		
	}
}
