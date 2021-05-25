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
	String ssh_key = "ssh-rsa AAAAB3NzaC1yc2EAAAADAQABAAABgQCuEj94g7d3JNFc6VYHKVvJg0ric2Ub9cvhQ7yoHYo2P+og+PuBJqqLxDvur07TByRbWC/BCAYjAC2wQf6fD92ZF1ZBHUw7NV5aAKcEu2C90ow0JuQDiNLyvt0d+0CCCnyVBQkj1IwY5V9BwUC9Jl7tZ0UlTwmiu4u6QZC8ZC3OhTbP0uR5dYSzcHDt20oUZ0Fo+7XaxW/u6X193gp8s8pJpr3O5rVulnx5E3lqvQdDbvT6HNqQ0ZFVNcaXesKVTkRMF7KcjgFjAC/hZjC7328+URqQsmSqmaw6H0mrhcrkpn3lt+8/tkObv8SHKyJDxx1c+F2kDWp0wjbRxUJyZsZ92xkNaovkCNQOXxz4z/a3tz/U3xJvMCzhFKs1ybSuFhZCj3GIPaN384DgWf/zqMY2wuKpG67zyygkNq32DX93vbH4Ts9igsD6+GB8Svt7kSlpZd5rWth9i7lqbUehjQo0uWop6KQZmP3G7PidakHLpg+IBF1ffD4xpe13x3/XY+k=";
	int ssh_id;

	@BeforeClass
	public void setUp() {
		// Create request specification
		requestSpec = new RequestSpecBuilder().setContentType(ContentType.JSON)
				.addHeader("Authorization", "token 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")
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
