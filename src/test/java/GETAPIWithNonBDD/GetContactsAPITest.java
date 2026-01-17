package GETAPIWithNonBDD;

import java.util.List;

import org.testng.Assert;
import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.Header;
import io.restassured.http.Headers;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;

public class GetContactsAPITest {

	@Test
	public void getContactsTest() {
		RestAssured.baseURI = "https://thinking-tester-contact-list.herokuapp.com";
		RequestSpecification request = RestAssured.given();
		
		request.header("Authorization", "Bearer eyJhbGciOiJIUzI1NiIsInR5cCI6IkpXVCJ9.eyJfaWQiOiI2OTZhZmVhYzhlMDZmNTAwMTUyNGVkZWEiLCJpYXQiOjE3Njg2MTk3MTB9.CAy8GSIrwNce_AJx4I5BeZXdDQ5pZRkElacsudehO5w");
		Response response = request.get("/contacts");
		
		System.out.println(response.statusCode());
		System.out.println(response.statusLine());
		response.prettyPrint();
		
		Assert.assertEquals(response.statusCode(), 200);
		
		String contentType = response.header("content-type");
		System.out.println(contentType);
		
		Headers headers = response.headers();
		List<Header> headersList = headers.asList();
		System.out.println(headersList.size());
		
		for(Header e : headersList) {
			String name = e.getName();
			String value = e.getValue();
			System.out.println("Header Name: " +name +" Header Value: "+value);
		}
	}
}
