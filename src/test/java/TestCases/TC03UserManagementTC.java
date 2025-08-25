package TestCases;

import io.qameta.allure.Description;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.http.ContentType;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.testng.Assert;
import org.testng.annotations.Test;

import static TestCases.TC01RegisterTC.eamil;
import static TestCases.TC01RegisterTC.password;
import static io.restassured.RestAssured.given;

public class TC03UserManagementTC {
    RequestSpecification req = new RequestSpecBuilder()
            .setContentType(ContentType.URLENC)
            .setBaseUri("https://automationexercise.com/api")
            .build();


    // update data
    @Description("update data")
    @Test
    public void updateUserAccountData() {
        Response res = given()
                .spec(req)
                .log().all()
                .formParam("name", "mohamed")
                .formParam("email", eamil)
                .formParam("password", password)
                .formParam("title", "Mr")
                .formParam("birth_date", "20")
                .formParam("birth_month", "May")
                .formParam("birth_year", "1998")
                .formParam("firstname", "mohamed")
                .formParam("lastname", "hamrawi")
                .formParam("company", "smartEgypt.com")
                .formParam("address1", "Egypt2")
                .formParam("address2", "Alexandria")
                .formParam("country", "Cairo")
                .formParam("zipcode", "123654")
                .formParam("state", "elram")
                .formParam("city", "sekina")
                .formParam("mobile_number", "01000000000")
                .when().put("/updateAccount");

        res.prettyPrint();

        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 200);
        Assert.assertEquals(res.body().jsonPath().get("message"), "User updated!");
        System.out.println("email: " + eamil);

    }

    @Description("get user details by email")
    @Test(dependsOnMethods = "updateUserAccountData")
    public void getUserEmailTC() {
        Response res = given()
                .log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", TC01RegisterTC.eamil)
                .when().get("/getUserDetailByEmail");

        res.prettyPrint();
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 200);
        Assert.assertEquals(res.body().jsonPath().get("user.name"), "mohamed");
        Assert.assertEquals(res.body().jsonPath().get("user.country"), "Cairo");
        Assert.assertEquals(res.body().jsonPath().get("user.company"), "smartEgypt.com");

    }

    //delete account
    @Description("delete account")
    @Test(dependsOnMethods = "getUserEmailTC")
    public void deleteAccount() {

        Response res = given()
                .log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", TC01RegisterTC.eamil)
                .formParam("password", TC01RegisterTC.password)
                .delete("/deleteAccount");
        res.prettyPrint();
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 200);
        Assert.assertEquals(res.body().jsonPath().get("message"), "Account deleted!");


    }


    @Description("get user details by email")
    @Test(dependsOnMethods = "deleteAccount")
    public void getUserEmailAfterDeleteTC() {
        Response res = given()
                .log().all()
                .spec(req)
                .contentType(ContentType.URLENC)
                .formParam("email", TC01RegisterTC.eamil)
                .when().get("/getUserDetailByEmail");

        res.prettyPrint();
        Assert.assertEquals((Integer) res.body().jsonPath().get("responseCode"), 404);
        Assert.assertEquals(res.body().jsonPath().get("message"), "Account not found with this email, try another email!");

    }


}
