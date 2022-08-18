package com.test.reqres;

import org.hamcrest.Matchers;
import org.testng.Assert;
import org.testng.annotations.Test;

import com.reqres.pojo.UserCreatedPojo;

import io.restassured.RestAssured;
import io.restassured.response.Response;

public class AutomateReqres {

	@Test
	public void validate_statuCode() {
		String body = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";

		Response response = RestAssured.given().baseUri("https://reqres.in/").body(body).when().post("api/users");
		int statusCode = response.getStatusCode();
		Assert.assertEquals(statusCode, 201);

	}

	@Test
	public void validate_jsonbody() {
		String body = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";

		String response = RestAssured.given().contentType("application/json").baseUri("https://reqres.in/").body(body)
				.when().post("api/users").then().log().all().extract().asString();
		// System.out.println(response.prettyPrint());
		Assert.assertEquals("name", Matchers.equalTo("morpheus"));
		Assert.assertEquals("job", Matchers.equalTo("leader"));
		Assert.assertEquals("id", Matchers.equalTo(275));

	}

	@Test
	public void validate_jsonbody2() {
		String payload = "{\r\n" + "    \"name\": \"morpheus\",\r\n" + "    \"job\": \"leader\"\r\n" + "}";

		Response response = RestAssured.given().log().all().header("Content-Type","application/json").baseUri("https://reqres.in/").body(payload)
				.when().post("api/users");
		
	    System.out.println(response.asPrettyString());
		
		//inline validation
	    response.then().body("name", Matchers.equalTo("morpheus"))
	    .body("job", Matchers.equalTo("leader"));
	    
	    //path validation
	    Assert.assertEquals(response.path("name"), "morpheus");
	    Assert.assertEquals(response.path("job"), "leader");
	    
	    //java object validation
	    UserCreatedPojo uc =response.as(UserCreatedPojo.class);
	    Assert.assertEquals(uc.getName(), "morpheus");
	    Assert.assertEquals(uc.getJob(), "leader");
	    
	    
	}

	
	
}
