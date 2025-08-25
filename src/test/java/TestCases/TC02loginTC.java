package TestCases;

import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;

public class TC02loginTC {

    RequestSpecification req = new RequestSpecBuilder()
            .setBaseUri("https://automationexercise.com/api")
            .build();


    @Test(priority = 1)
    public void loginTC() {
        Response res = given().log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", TC01RegisterTC.eamil)
                .formParam("password", TC01RegisterTC.password)
                .post("/verifyLogin");
        Assert.assertEquals(res.statusCode(), 200);
        Assert.assertEquals(res.body().jsonPath().get("message"), "User exists!");
    }



    @Description("Verify Login without email")
    @Test(priority = 3)
    public void inValidLoginTC() {
        Response res = given()
                .log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("password", TC01RegisterTC.password)
                .post("/verifyLogin");
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 400);
        Assert.assertEquals(res.body().jsonPath().get("message"), "Bad request, email or password parameter is missing in POST request.");

    }


    @Description("Verify Login with invalid details")
    @Test(priority = 4)
    public void inValidLoginTC2() {
        Response res = given()
                .log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", "errorEmail.com")
                .formParam("password", "errorPassword")
                .post("/verifyLogin");
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 404);
        Assert.assertEquals(res.body().jsonPath().get("message"), "User not found!");

    }

    @Description("invalid  http method")
    @Test(priority = 5)
    public void inValidLoginTC3() {

        Response res = given().log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", TC01RegisterTC.eamil)
                .formParam("password", TC01RegisterTC.password)
                .when().delete("/verifyLogin");
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 405);
        Assert.assertEquals(res.body().jsonPath().get("message"), "This request method is not supported.");


    }


}
