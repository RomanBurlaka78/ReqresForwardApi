package api.Steps;

import api.Utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.response.Response;


public class UserSteps {
    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get list users by : {page}")
    public Response getListUsers(int page) {
        return apiClient.get("/users?page =" + page);
    }

    @Step("Get user by ID: {userId}")
    public Response getUserId(int userId) {
        return apiClient.get("/users/" + userId);
    }

    @Step("Create user with name: {name} and job: {job}")
    public Response createUser(String name, String job) {
        String jsonBody = """
        {
            "name": "%s",
            "job": "%s"
        }
        """.formatted(name, job);
        return apiClient.post("/users", jsonBody);
    }

    @Step("\"Update user with ID: {userId}, name: {name} and job: {job}")
    public Response updateUser(int userId, String name, String job) {
        String jsonBody = """
        {
            "name": "%s",
            "job": "%s"
        }
        """.formatted(name, job);
        return apiClient.put("/users/" + userId, jsonBody);
    }

    @Step("\"Update user with ID: {userId}, name: {name} and job: {job}")
    public Response updatePatchUser(int userId, String name, String job) {
        String jsonBody = """
        {
            "name": "%s",
            "job": "%s"
        }
        """.formatted(name, job);
        return apiClient.patch("/users/" + userId, jsonBody);
    }

    @Step("Delete user by ID: {userId}")
    public Response deleteUser(int userId) {
        return apiClient.delete("/users/" + userId);
    }

}
