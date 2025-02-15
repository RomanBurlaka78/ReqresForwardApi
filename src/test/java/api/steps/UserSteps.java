package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;



public class UserSteps {
    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get list users by : {page}")
    public Response getListUsers(int page) {
        return apiClient.get("/users?page =" + page);
    }

    @Step("Get user by ID: {userId}")
    public Response getUserById(int userId) {
        return apiClient.get("/users/" + userId);
    }

    @Step("Create user with name: {name} and job: {job}")
    public Response createUser(String name, String job) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return apiClient.post("/users", body);
    }

    @Step("Update user with ID: {userId}, name: {name} and job: {job}")
    public Response updateUser(int userId, String name, String job) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return apiClient.put("/users/" + userId, body);
    }

    @Step("Update user with ID: {userId}, name: {name} and job: {job}")
    public Response updatePatchUser(int userId, String name, String job) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return apiClient.patch("/users/" + userId, body);
    }

    @Step("Delete user by ID: {userId}")
    public Response deleteUser(int userId) {
        return apiClient.delete("/users/" + userId);
    }
}
