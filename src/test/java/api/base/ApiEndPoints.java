package api.base;

public class ApiEndPoints {
    protected final String REGISTER_END_POINT = "/register";
    protected final String LOGIN_SUCCESSFUL = "/login";
    protected final String USER_DELAY = "users?delay";
    protected final String UNKNOWN= "/unknown/";
    protected final String USERS_DEALAY = "users?delay";
    protected final String USERS_PAGE = "/users?page =";
    protected final String USERS_WITH_SLASH = "/users/";
    protected final String USERS = "/users";
    protected final String API_USERS = "/api/users/";


    public String getREGISTER_END_POINT() {
        return REGISTER_END_POINT;
    }

    public String getLOGIN_SUCCESSFUL() {
        return LOGIN_SUCCESSFUL;
    }

    public String getUSER_DELAY() {
        return USER_DELAY;
    }

    public String getUNKNOWN() {
        return UNKNOWN;
    }

    public String getUSERS_DEALAY() {
        return USERS_DEALAY;
    }

    public String getUSERS_PAGE() {
        return USERS_PAGE;
    }

    public String getUSERS() {
        return USERS;
    }

    public String getUSERS_WITH_SLASH() {
        return USERS_WITH_SLASH;
    }

    public String getAPI_USERS() {
        return API_USERS;
    }
}
