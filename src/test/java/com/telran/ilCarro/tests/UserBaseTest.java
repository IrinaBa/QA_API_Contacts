package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.telran.ilCarro.dto.UserBaseAssuredDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class UserBaseTest {
    String token = "a2FybCs4QG1haWwucnU6S2FybF8xMjM0NTY=";

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";

    }

    @Test
    public void registrationPositiveTest() {

        UserBaseAssuredDto userBaseAssuredDto = UserBaseAssuredDto.builder()
                .first_name("Karl")
                .second_name("Lagerfeld")
                .build();


        String name = given().header("Authorization", token)
                .contentType("application/json")
                .body(userBaseAssuredDto)
                .put("user")
                .then()
                .assertThat().statusCode(200)
                .extract().path("first_name");
        System.out.println(name);



    }
}