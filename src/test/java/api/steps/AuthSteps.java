package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

import static io.restassured.RestAssured.given;

public class AuthSteps extends DataManager{

    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Create register successful: {email, password}")
    public Response getRegisterSuccessful(String apiEndPoint, String email, String password) {
        return apiClient.post(apiEndPoint, generateBodyFromEmailAndPassword(email,password));
    }

    @Step("Create register unsuccessful: {email}")
    public Response getRegisterUnSuccessful(String apiEndPoint, String email) {
        return apiClient.post(apiEndPoint, generateBodyFromEmail(email));
    }

    @Step("Login successful: {email, password}")
    public Response postLoginSuccessful(String apiEndPoint, String email, String password) {
        return apiClient.post(apiEndPoint, generateBodyFromEmailAndPassword(email,password));
    }

    @Step("Login successful: {email, password}")
    public Response postLoginUnSuccessful(String apiEndPoint, String email) {
        return apiClient.post(apiEndPoint, generateBodyFromEmail(email));
    }



}
