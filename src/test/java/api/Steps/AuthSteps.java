package api.Steps;


import api.Utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;

public class AuthSteps {
    private ApiClient apiClient = ApiClient.getInstance();
    @Step("Create register successful: {email, password}")
    public Response getRegisterSuccessful(String email, String password) {
        String jsonBody = """
        {
            "email": "%s",
            "password": "%s"
        }
        """.formatted(email, password);
        return apiClient.post("/register", jsonBody);
    }

    @Step("Create register unsuccessful: {email}")
    public Response getRegisterUnSuccessful(String email) {
        String jsonBody = """
        {
            "email": "%s"
        }
        """.formatted(email);
        return apiClient.post("/register", jsonBody);
    }

    @Step("Create login successful: {email, password}")
    public Response getLoginSuccessful(String email, String password) {
        String jsonBody = """
        {
            "email": "%s",
            "password": "%s"
        }
        """.formatted(email, password);
        return apiClient.post("/login", jsonBody);
    }

    @Step("Create login unsuccessful: {email}")
    public Response getLoginUnSuccessful(String email) {
        String jsonBody = """
        {
            "email": "%s"
        }
        """.formatted(email);
        return apiClient.post("/login", jsonBody);
    }
}
