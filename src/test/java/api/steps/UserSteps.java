package api.steps;

import api.utils.ApiClient;
import io.qameta.allure.Step;
import io.restassured.http.ContentType;
import io.restassured.response.Response;



public class UserSteps extends DataManager{

    private ApiClient apiClient = ApiClient.getInstance();

    @Step("Get list users by : {page}")
    public Response getListUsers(String apiEndPoint, int page) {
        return apiClient.get(generateBodyFromStringAndInt(apiEndPoint, page));
    }

    @Step("Get user by ID: {userId}")
    public Response getUserById(String apiEndPoint, int userId) {
        return apiClient.get(generateBodyFromStringAndInt(apiEndPoint, userId));
    }

    @Step("Create user with name: {name} and job: {job}")
    public Response createUser(String apiEndPoint, String name, String job) {
        return apiClient.post(apiEndPoint, generateBodyFromNameAndJob( name, job));
    }

    @Step("Update user with ID: {userId}, name: {name} and job: {job}")
        public Response updateUser(String apiEndPoint, int userId, String name, String job) {
            String generated_endpoint = generateBodyFromStringAndInt(apiEndPoint, userId);
            return apiClient.put(generated_endpoint, generateBodyFromNameAndJob(name, job));
        }

    @Step("Delete user by ID: {userId}")
    public Response deleteUser(String apiEndPoint, int userId) {
        return apiClient.delete(generateBodyFromStringAndInt(apiEndPoint, userId));
    }
}
