package TestCases;

import io.restassured.RestAssured;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import org.testng.Assert;
import org.testng.annotations.Test;
import java.time.Instant;

import static io.restassured.RestAssured.given;

public class TC01RegisterTC {

public static String eamil = "test@example.com"+ Instant.now().toEpochMilli();
public static String password = "123456";

    @Test
    public void registerTC() {

        RestAssured.baseURI= "https://automationexercise.com/api";

        Response res = given()
                .log().all()
                .contentType(ContentType.URLENC)
                .formParam("name", "mohamed")
                .formParam("email", eamil)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "12")
                .formParam("birth_month", "May")
                .formParam("birth_year", "1998")
                .formParam("firstname", "mohamed")
                .formParam("lastname", "mohamed2")
                .formParam("company", "xyz.com")
                .formParam("address1", "Egypt2")
                .formParam("address2", "Egypt")
                .formParam("country", "Egypt")
                .formParam("zipcode", "123654")
                .formParam("state", "alex")
                .formParam("city", "elramal")
                .formParam("mobile_number", "0122222222")
                .when()
                .post("/createAccount");

        res.prettyPrint();
        Assert.assertEquals((Integer) (res.body().jsonPath().get("responseCode")), 201);
        Assert.assertEquals(res.body().jsonPath().get("message"), "User created!");
    }


}
