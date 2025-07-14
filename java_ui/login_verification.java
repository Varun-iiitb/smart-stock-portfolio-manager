package com.example.tradesight;

import java.net.URI;
import java.net.http.HttpClient;
import java.net.http.HttpRequest;
import java.net.http.HttpResponse;

public class login_verification {
    public static String loginUser(String username, String password) {
        try{
            String jsonbody = String.format("{\"username\":\"%s\",\"password\":\"%s\"}", username, password);

            HttpRequest request = HttpRequest.newBuilder().uri(URI.create("http://localhost:5000/login")).header("Content-Type", "application/json").POST(HttpRequest.BodyPublishers.ofString(jsonbody)).build();
            HttpClient httpClient = HttpClient.newHttpClient();
            HttpResponse<String> response = httpClient.send(request,HttpResponse.BodyHandlers.ofString());

            String responseBody = response.body();

            if (response.statusCode() == 200 && responseBody.contains("Login successful")) {
                return "Login successful";
            }

            else if (response.statusCode() == 401 && responseBody.contains("Invalid credentials")) {
                return "Invalid credentials";
            }
            else{
                return "Something went wrong";
            }
        } catch (Exception e) {
            return "Error while connecting to the server";
        }
    }
}
