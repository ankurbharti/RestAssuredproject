package restassuredproject;

import org.testng.annotations.Test;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;

import  static io.restassured.RestAssured.given;

public class demo {
	public String accessToken;
    public String id;
   
    
	 public static String baseurl = "https://ecommerceservice.herokuapp.com";
	

	
	@Test(priority = 0)
	public void login()
	{
        RestAssured.baseURI = baseurl; 
		
		String requestbody = "{\n"
				+ "	\"email\": \"ankur4567@gmail.com\",\n"
				+ "	\"password\": \"ankur@123\"\n"
				+ "}";
		
		//this time i want to know what my response is in my console
		Response response = given()
		.header("content-Type","application/json")
		.body(requestbody)
		
		.when()
		.post("/user/login")
		
		.then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON)
		.extract().response();
		//is to print the response
		//basically the output format by default will be string cannot read
		//i have to convert it to json
		String jsonresponse = response.asString();
		//if i want to convert from normal string to json format
		JsonPath responsebody =new JsonPath(jsonresponse);
	    accessToken = responsebody.get("accessToken");
	    System.out.println(responsebody.get("accessToken"));
	}
	@Test(priority = 1)
	public void get()
	 {
		RestAssured.baseURI = baseurl; 
		
		
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " +accessToken)
		
		.when()
		.get("/products")
		
		.then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON)
		.extract().response();
		
		String jsonresponse = response.asString();
		JsonPath responsebody =new JsonPath(jsonresponse);
		System.out.println(responsebody.get());
	 }
	@Test(priority = 2)
	public void create()
	{
        RestAssured.baseURI = baseurl; 
		
		String requestbody = "{\n"
				+ "	\"name\": \"iphone\",\n"
				+ "	\"price\": 5000\n"
				+ "}";
		
		//this time i want to know what my response is in my console
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " +accessToken)
		.body(requestbody)
		
		.when()
		.post("/products")
		
		.then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON)
		.extract().response();
		
		String jsonresponse = response.asString();
		
		JsonPath responsebody =new JsonPath(jsonresponse);
		System.out.println(responsebody.get("product._id"));
		id = responsebody.get("product._id");
		System.out.println(responsebody.get("message"));
	}
	@Test(priority = 3)
	public void productid()
	{
        RestAssured.baseURI = baseurl; 
		
		
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " +accessToken)
		
		.when()
		.get("/products/"+id)
		
		.then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON)
		.extract().response();
		
		String jsonresponse = response.asString();
		JsonPath responsebody =new JsonPath(jsonresponse);
		System.out.println(responsebody.get());
		
		
	}
	@Test(priority = 4)
	public void updateid()
	{
        RestAssured.baseURI = baseurl; 
		
		String requestbody = "{\n"
				+ "	\"name\": \"iphone\",\n"
				+ "	\"price\": 3000\n"
				+ "}";
		
		
		Response response = given()
		.header("content-Type","application/json")
		.header("Authorization","bearer " +accessToken)
		.body(requestbody)
		
		.when()
		.patch("/products/"+id)
		
		.then()
		.assertThat().statusCode(200).and().contentType(ContentType.JSON)
		.extract().response();
		
		String jsonresponse = response.asString();
		
		JsonPath responsebody =new JsonPath(jsonresponse);
		System.out.println(responsebody.get());
		
	}
	
}
