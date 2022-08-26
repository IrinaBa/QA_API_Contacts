package com.telran.ilCarro.tests;

import com.jayway.restassured.RestAssured;
import com.telran.ilCarro.dto.AuthorizationAssuredDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class RestAssurTests {
    String token = "a2FybCs4QG1haWwucnU6S2FybF8xMjM0NTY=";

    @BeforeMethod
    public void ensurePrecondition() {
        RestAssured.baseURI = "https://java-3-ilcarro-team-b.herokuapp.com";


    }

    @Test
    public void registrationPositiveTest() {

        AuthorizationAssuredDto authorizationRequestDto = AuthorizationAssuredDto.builder()
                .first_name("Karl")
                .second_name("Lagerfeld")
                .build();


        String date = given().header("Authorization", token)
                .contentType("application/json")
                .body(authorizationRequestDto)
                .post("registration")
                .then()
                .assertThat().statusCode(200)
                .extract().path("registration_date");
        System.out.println(date);


    }
}