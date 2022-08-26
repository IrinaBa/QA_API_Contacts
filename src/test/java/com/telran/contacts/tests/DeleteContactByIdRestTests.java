package com.telran.contacts.tests;

import com.jayway.restassured.RestAssured;
import com.telran.contacts.dto.ContactDto;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;

import static com.jayway.restassured.RestAssured.given;

public class DeleteContactByIdRestTests {

    String token = "eyJ0eXAiOiJKV1QiLCJhbGciOiJIUzI1NiJ9.eyJlbWFpbCI6ImlyaW5hKzEyMDhAbWFpbC5ydSJ9.v4AvrflX6WXEzpCAMNOgS2jZLhUFo9ITFqcjlmE_reQ";

    int id;

    @BeforeMethod
    public void ensurePrecondition() {

        RestAssured.baseURI = "https://contacts-telran.herokuapp.com";
        RestAssured.basePath = "api";

        int i = (int) ((System.currentTimeMillis() / 1000) % 3600);

        ContactDto contactDto = ContactDto.builder()
                .address("Leer")
                .description("Forward")
                .email("Arina" + i + "@mail.ru")
                .lastName("Lagerfeld")
                .name("Arina")
                .phone("123456789" + i)
                .build();

        id = given().header("Authorization", token)
                .contentType("application/json")
                .body(contactDto)
                .post("contact")
                .then()
                .assertThat().statusCode(200)
                .extract().path("id");

    }

    @Test
    public void deleteByIdTest() {
        String status = given().header("Authorization", token)
                .delete("/contact/" + id)
                .then()
                .assertThat().statusCode(200)
                .extract().path("status");
        System.out.println(status);
    }


}
