package api;

import api.dto.Credentials;
import api.dto.User;
import io.qameta.allure.Step;
import io.restassured.RestAssured;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class UserClient {

    public UserClient(String baseUrl) {
        RestAssured.baseURI = baseUrl.endsWith("/") ? baseUrl + "api" : baseUrl + "/api";
    }

    @Step("API: регистрация пользователя {user.email}")
    public Response register(User user) {
        return given()
                .header("Content-Type", "application/json")
                .body(user)
                .post("/auth/register")
                .andReturn();
    }

    @Step("API: логин пользователя {cred.email}")
    public Response login(Credentials cred) {
        return given()
                .header("Content-Type", "application/json")
                .body(cred)
                .post("/auth/login")
                .andReturn();
    }

    @Step("API: удалить пользователя по accessToken")
    public void delete(String accessToken) {
        given()
                .header("Authorization", accessToken)
                .delete("/auth/user")
                .andReturn();
    }
}