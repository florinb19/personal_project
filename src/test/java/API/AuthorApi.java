package API;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.openqa.selenium.By;
import org.testng.Assert;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class AuthorApi {

    public static Integer generateRandomId(){
        return new Random().nextInt(0,9999);
    }

    public static int authorId = generateRandomId();

    public static String authorNameRandom () {return "AutorLuniLast4%d".formatted(generateRandomId());}


    public void createAuthorApi(String email, String password, int userId, int authorId) {
        Response response = given()
                .baseUri("http://35.205.170.236/api/v1/authors/JohhnyUsername" + userId)
                .contentType("application/json")
                .auth().basic(email, password)
                .body("{\"firstName\":\"AutorLuni4\",\"lastName\":\"AutorLuniLast4\",\"id\":\""+authorId+"\"}")
                .post();
        JsonPath jsonPath = response.getBody().jsonPath();
        jsonPath.prettyPrint();
        Assert.assertEquals(response.getStatusCode(), 200);
    }
}
