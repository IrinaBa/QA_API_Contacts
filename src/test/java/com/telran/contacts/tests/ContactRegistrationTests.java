package com.telran.contacts.tests;

import com.google.gson.Gson;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ErrorDto;
import org.apache.http.HttpResponse;
import org.apache.http.client.fluent.Request;
import org.apache.http.client.fluent.Response;
import org.apache.http.entity.ContentType;
import org.testng.annotations.Test;

import java.io.BufferedReader;
import java.io.IOException;
import java.io.InputStream;
import java.io.InputStreamReader;

public class ContactRegistrationTests {


    @Test
    public void registrationHttpTestWithInvalidEmail() throws IOException {

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("irina+1208@mail")
                .password("Irina_12345").build();

        Gson gson = new Gson();

        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/registration")
                .bodyString(gson.toJson(requestDto), ContentType.APPLICATION_JSON)
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        System.out.println(httpResponse.getStatusLine().getStatusCode());

        InputStream is = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(is));

        errorMassage(gson, reader);

    }

    @Test
    public void registrationUserAlreadyExist() throws IOException {

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("irina+1208@mail.ru")
                .password("Irina_12345").build();

        Gson gson = new Gson();

        Response response = Request.Post("https://contacts-telran.herokuapp.com/api/registration")
                .bodyString(gson.toJson(requestDto), ContentType.APPLICATION_JSON)
                .execute();

        HttpResponse httpResponse = response.returnResponse();
        System.out.println(httpResponse.getStatusLine().getStatusCode());


        InputStream inputStream = httpResponse.getEntity().getContent();
        BufferedReader reader = new BufferedReader(new InputStreamReader(inputStream));

        errorMassage(gson, reader);

    }

    public void errorMassage(Gson gson, BufferedReader reader) throws IOException {
        String line;
        StringBuilder sb = new StringBuilder();
        while ((line = reader.readLine()) != null) {
            sb.append(line);
        }

        ErrorDto error = gson.fromJson(sb.toString(), ErrorDto.class);
        System.out.println(error.getDetails());
        System.out.println(error.getMessage());
        System.out.println(error.getCode());
    }

}