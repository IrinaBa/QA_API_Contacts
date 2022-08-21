package com.telran.contacts.tests;

import com.google.gson.Gson;
import com.telran.contacts.dto.AuthRequestDto;
import com.telran.contacts.dto.ErrorDto;
import com.telran.contacts.dto.LoginRegResponseDto;
import okhttp3.*;
import org.testng.annotations.Test;

import java.io.IOException;


public class ContactRegistrationOkHttpTests {

    public static final MediaType JSON = MediaType.get("application/json; charset=utf-8");


    @Test
    public void registrationUserAlreadyExist() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("irina+1208@mail.ru")
                .password("Irina_12345").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responseJSON = response.body().string();

        ErrorDto errorDto = gson.fromJson(responseJSON, ErrorDto.class);
        System.out.println(errorDto.getCode());
        System.out.println(errorDto.getMessage());

    }

    @Test
    public void registrationInvalidPassword() throws IOException {
        Gson gson = new Gson();
        OkHttpClient client = new OkHttpClient();

        AuthRequestDto requestDto = AuthRequestDto.builder()
                .email("irina+1208@mail.ru")
                .password("irina123").build();

        RequestBody requestBody = RequestBody.create(gson.toJson(requestDto), JSON);

        Request request = new Request.Builder()
                .url("https://contacts-telran.herokuapp.com/api/registration")
                .post(requestBody)
                .build();

        Response response = client.newCall(request).execute();

        String responseJSON = response.body().string();

        if (response.isSuccessful()) {
            LoginRegResponseDto responseDto = gson.fromJson(responseJSON, LoginRegResponseDto.class);
            System.out.println(responseDto.getToken());
            System.out.println(responseDto.getCode());
        } else {
            ErrorDto errorDto = gson.fromJson(responseJSON, ErrorDto.class);
            System.out.println("******************************");
            System.out.println(errorDto.getCode());
            System.out.println("*******************************");
            System.out.println(errorDto.getMessage());
        }
    }
}