package com.telran.contacts.tests;

import com.jayway.restassured.RestAssured;
import com.jayway.restassured.response.ValidatableResponse;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ContactDto;
import com.telran.contacts.dto.GetAllContactsDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.expect;
import static com.jayway.restassured.RestAssured.given;
import static org.hamcrest.Matchers.containsString;
import static org.hamcrest.Matchers.equalTo;

public class RestAssuredTests {
    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImlyaW5hKzEyMDhAbWFpbC5ydSJ9.v4AvrflX6WXEzpCAMNOgS2jZLhUFo9ITFqcjlmE_reQ";

    @BeforeMethod
    public void ensurePrecondition() {

        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";
    }

    @Test
    public void loginPositiveTest() {
        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("irina+1208@mail.ru")
                .password("Irina_12345")
                .build();
        LoginRegResponseDto responseDto = given()
                .contentType("application/json")
                .body(requestDto)
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .extract().response().as(LoginRegResponseDto.class);
        System.out.println(responseDto.getToken());

        // String token ="eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImlyaW5hKzEyMDhAbWFpbC5ydSJ9.v4AvrflX6WXEzpCAMNOgS2jZLhUFo9ITFqcjlmE_reQ";
        String token2 = String.valueOf(given().contentType("application/json")
                .body(requestDto)
                .when()
                .post("login")
                .then()
                .assertThat().statusCode(200)
                .body(containsString("token"))
                .body("token", equalTo(token)));
        System.out.println(token2);


    }

    /* @Test
     public void loginNegativeTestWithInvalidPassword(){
         AuthRequestDto requestDto = AuthRequestDto.builder()
                 .email("irina+1208@mail.ru")
                 .password("Irina12345")
                 .build();

         String message = given().contentType("aplication/json")
                 .body(requestDto)
                 .post("login")
                 .then()
                 .assertThat().statusCode(400)
                 .extract().path("message");
         System.out.println(message);

     }

     */
    @Test
    public void addNewContactPositiveTest() {
        int i = (int) ((System.currentTimeMillis() / 1000) % 3600);
        // String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImlyaW5hKzEyMDhAbWFpbC5ydSJ9.v4AvrflX6WXEzpCAMNOgS2jZLhUFo9ITFqcjlmE_reQ";
        ContactDto contactDto = ContactDto.builder()
                .address("Leer")
                .description("Forward")
                .email("Arina" + i + "@mail.ru")
                .lastName("Lagerfeld")
                .name("Arina")
                .phone("123456789" + i)
                .build();

        int id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");
        System.out.println(id);
    }


    @Test
    public void getAllContactsTest() {
        GetAllContactsDto responseDto = given()
                .header("Authorization", token)
                .get("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().body().as(GetAllContactsDto.class);

        for (ContactDto contactDto : responseDto.getContacts()) {
            System.out.println(contactDto.getId() + "*****" + contactDto.getLastName() + "*****");
            System.out.println("--------------------------------------");
        }
    }

        @Test
        public void deleteContact () {
            String status = given().header("Authorization", token)
                    .delete("/contact/5826")
                    .then()
                    .assertThat().statusCode(200)
                    .extract().path("status");
            System.out.println(status);
        }

    }

//5816*****Lagerfeld*****
//--------------------------------------
//5823*****Lagerfeld*****
//--------------------------------------
//5826*****Lagerfeld*****