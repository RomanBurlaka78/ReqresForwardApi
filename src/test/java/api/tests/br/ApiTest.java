package api.tests.br;

import api.base.Specifications;
import api.pojo.DataListUsers;
//import io.qameta.allure.restassured.AllureRestAssured;
import org.testng.Assert;
import org.testng.annotations.Ignore;
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
                //.filter(new AllureRestAssured())
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


    @Test
    public void testGetListUserPojo() {
        specifications.installSpec();
        List<DataListUsers> itemList = given()
                //.filter(new AllureRestAssured())
                .when()
                .get("/api/users?page=2")
                .then()
                .statusCode(200)
                .extract().body().jsonPath().getList("data", DataListUsers.class);

        Assert.assertEquals(itemList.get(0).id, 7);

        itemList.stream().forEach(System.out::println);

    }

}
