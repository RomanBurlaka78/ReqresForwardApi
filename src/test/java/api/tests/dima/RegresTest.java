package api.tests.dima;

import api.base.Specifications;
import com.fasterxml.jackson.databind.util.JSONPObject;
import io.restassured.builder.RequestSpecBuilder;
import io.restassured.internal.RequestSpecificationImpl;
import io.restassured.response.Response;
import io.restassured.specification.RequestSpecification;
import org.json.JSONObject;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.List;

import static io.restassured.RestAssured.given;

public class RegresTest {

    protected Specifications requestSpec = new Specifications();
    protected RequestSpecification spec;
    {
        spec = requestSpec.setupRequest();
    }

    @Test
    public void testListOfUsersIdExisted(){

        spec.queryParam("page", 2);
        Response response = given(spec).
                when().
                get("/api/users").
                then().
                statusCode(200).
                extract().
                response();

        List <Integer> listOfUsersId = response.jsonPath().getList("data.id");
        Assert.assertEquals(List.of(7,8,9,10,11,12), listOfUsersId);
    }

    @Test
    public void testVerifyUsersId(){

        int idOfUser = given(spec).
                when().
                get("/api/users/2").jsonPath().getInt("data.id");
        Assert.assertEquals(idOfUser,2);
    }

    @Test(description = "use String class for verification")
    public void testUserIsNotExist(){
        String expectedResult = "{}";
        String response = given(spec).
                when().
                get("/api/users/23").
                then().
                assertThat().
                statusCode(404).
                extract().asString();
        Assert.assertEquals(response, expectedResult);
    }

    @Test(description = "use Rest Assured class JSONObject for verification")
    public void testUserIsNotExistJson() {

        JSONObject jsonpObject = new JSONObject();
        Response response = given(spec).
                when().
                get("/api/users/23").
                then().
                assertThat().
                statusCode(404).
                extract().response();
        Assert.assertEquals(response.body().asString(), jsonpObject.toString());

    }

}
