package api.tests.br;

import api.base.Specifications;
import org.testng.Assert;
import org.testng.annotations.Test;

import static io.restassured.RestAssured.given;
import static io.restassured.RestAssured.when;

import java.util.HashMap;
import java.util.LinkedHashMap;
import java.util.List;
import java.util.Map;


public class ApiTest {
    Specifications specifications = new Specifications();

    @Test
    public void testGetListOfUsers() {
        specifications.installSpec();

        List<Object> itemList = given()
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().jsonPath().getList("data");

        LinkedHashMap<Object, Object> listHashMapUsers = new LinkedHashMap<>((Map<?, ?>) itemList.get(0));

        Assert.assertEquals(listHashMapUsers.get("id"), 7);
        Assert.assertEquals(listHashMapUsers.get("email"), "michael.lawson@reqres.in");
        Assert.assertEquals(listHashMapUsers.get("first_name"), "Michael");
        Assert.assertEquals(listHashMapUsers.get("last_name"), "Lawson");
        Assert.assertEquals(listHashMapUsers.get("avatar"), "https://reqres.in/img/faces/7-image.jpg");


    }


}
