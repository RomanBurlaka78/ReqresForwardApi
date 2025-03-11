package testsUtils.testsData;

public class AuthorizationTestData {

    protected static final String REGISTRATION_EMAIL = "eve.holt@reqres.in";
    protected static final String REGISTRATION_PASSWORD = "pistol";

    protected static final String LOGIN_EMAIL = "eve.holt@reqres.in";
    protected static final String LOGIN_PASSWORD = "cityslicka";

    protected final String registerSuccessfulResponse = """
                {"id":4,"token":"QpwL5tke4Pnpja7X4"}""";

    protected final String registerUnSuccessfulErrorMessageResponse = """
                {"error":"Missing password"}""";

    protected final String loginSuccessfulResponse = """
                {"token":"QpwL5tke4Pnpja7X4"}""";
}
