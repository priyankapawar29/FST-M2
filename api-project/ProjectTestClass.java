package test;

import org.testng.annotations.Test;
import org.testng.annotations.BeforeClass;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import static io.restassured.RestAssured.given;

public class ProjectTestClass {

	// Declare request specification
	RequestSpecification requestSpec;
	String ssh_key = "XXXX";
	int ssh_id;

	@BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Authorization", "token XXXX")
				.setBaseUri("https://api.github.com/").build();
	}

	@Test(priority = 1)
	// Test case using a DataProvider
	public void addKeys() {
		String reqBody = "{\"title\": \"TestKey\", \"key\": \"" + ssh_key + "\" }";
		Response response = given().spec(requestSpec) // Use requestSpec
				.body(reqBody) // Send request body
				.when().post("/user/keys"); // Send POST request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);
		ssh_id = response.then().extract().path("id"); // Assertions
		response.then().statusCode(201);
	}

	@Test(priority = 2)
	// Test case using a DataProvider
	public void getKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.when().get("/user/keys"); // Send GET Request
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody); // Assertions
		response.then().statusCode(200);
	}

	
	@Test(priority = 3)
	// Test case using a DataProvider
	public void deleteKeys() {
		Response response = given().spec(requestSpec) // Use requestSpec
				.pathParam("keyId", ssh_key).when().delete("/user/keys/{keyId}"); // Send GET Request		
		String resBody = response.getBody().asPrettyString();
		System.out.println(resBody);		// Assertions
		response.then().statusCode(204);
	}

}
