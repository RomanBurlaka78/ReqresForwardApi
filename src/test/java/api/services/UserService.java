package api.services;

import io.qameta.allure.Step;
import io.restassured.response.Response;



public class UserService extends BaseService {

    @Step("Get list users by : {page}")
    public Response getListUsers(String apiEndPoint, int page) {
        return get(apiEndPoint + page);
    }

    @Step("Get user by ID: {userId}")
    public Response getUserById(String apiEndPoint, int userId) {
        return get(apiEndPoint + userId);
    }

    @Step("Create user with name: {name} and job: {job}")
    public Response createUser(String apiEndPoint, String name, String job) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return post(apiEndPoint, body);
    }

    @Step("Update user with ID: {userId}, name: {name} and job: {job}")
    public Response updateUser(String apiEndPoint, int userId, String name, String job) {
        String generated_endpoint = apiEndPoint + userId;
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return put(generated_endpoint, body);
    }

    @Step("Delete user by ID: {userId}")
    public Response deleteUser(String apiEndPoint, int userId) {
        return delete(apiEndPoint + userId);
    }
}
