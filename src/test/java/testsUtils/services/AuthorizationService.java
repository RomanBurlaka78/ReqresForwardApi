package testsUtils.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthorizationService extends BaseService {

    @Step("Create register successful: {email, password}")
    public Response getRegisterSuccessful(String apiEndPoint, Object object) {
        //String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return post(apiEndPoint, object);
    }

    @Step("Create register unsuccessful: {email}")
    public Response getRegisterUnSuccessful(String apiEndPoint, String email) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email,"");
        return post(apiEndPoint, body);
    }

    @Step("Login successful: {email, password}")
    public Response postLoginSuccessful(String apiEndPoint, String email, String password) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return post(apiEndPoint, body);
    }

    @Step("Login successful: {email, password}")
    public Response postLoginUnSuccessful(String apiEndPoint, String email) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email,"");
        return post(apiEndPoint, body);
    }



}
