package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthSteps {
    private ApiClient apiClient = ApiClient.getInstance();
    @Step("Create register successful: {email, password}")
    public Response getRegisterSuccessful(String email, String password) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return apiClient.post("/register", body);
    }

    @Step("Create register unsuccessful: {email}")
    public Response getRegisterUnSuccessful(String email) {
        String body = String.format("{\"email\": \"%s\"}", email);
        return apiClient.post("/register", body);
    }

    @Step("Login successful: {email, password}")
    public Response postLoginSuccessful(String email, String password) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return apiClient.post("/login", body);
    }

    @Step("Login successful: {email, password}")
    public Response postLoginUnSuccessful(String email) {
        String body = String.format("{\"email\": \"%s\"}", email);
        return apiClient.post("/login", body);
    }



}
