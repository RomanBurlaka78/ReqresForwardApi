package api.steps;

import io.restassured.response.Response;

public abstract class DataManager {

    public String generateBodyFromEmailAndPassword(String email, String password) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email, password);
        return body;
    }

    public String generateBodyFromNameAndJob(String name, String job) {
        String body = String.format("{\"name\": \"%s\", \"job\": \"%s\"}", name, job);
        return body;
    }

    public String generateBodyFromEmail(String email) {
        String body = String.format("{\"email\": \"%s\", \"password\": \"%s\"}", email,"");
        return body;
    }

    public String generateBodyFromStringAndInt(String str, int i) {
        return str+i;
    }

}
