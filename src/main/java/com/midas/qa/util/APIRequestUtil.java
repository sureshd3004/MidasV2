package com.midas.qa.util;

import io.restassured.response.Response;
import static io.restassured.RestAssured.*;


public class APIRequestUtil {

    public static Response sendGet(String url, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .get(url)
                .then()
                .extract().response();
    }
    public static Response sendGet(String url) {
        return given()
           //     .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .get(url)
                .then()
                .extract().response();
    }

    public static Response sendPost(String url, String token, String body) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();
    }
    
    public static Response sendPostWithNoToken(String url,String body) {
        return given()
              //  .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .post(url)
                .then()
                .extract().response();
    }

    public static Response sendPut(String url, String token, String body) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .body(body)
                .when()
                .put(url)
                .then()
                .extract().response();
    }

    public static Response sendDelete(String url, String token) {
        return given()
                .header("Authorization", "Bearer " + token)
                .header("Content-Type", "application/json")
                .when()
                .delete(url)
                .then()
                .extract().response();
    }
}
