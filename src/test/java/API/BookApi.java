package API;

import io.restassured.path.json.JsonPath;
import io.restassured.response.Response;
import org.testng.Assert;

import java.util.HashMap;
import java.util.Random;

import static io.restassured.RestAssured.given;


public class BookApi {

    public static int generateBookId() {
        return new Random().nextInt(0, 999);
    }

    public static int bookId = generateBookId();


    public HashMap<String, Object> createBook(String user, String password, int userId) {
        Response response = given()
                .baseUri("http://35.205.170.236/api/v1/books/JohhnyUsername" + userId)
                .auth().basic(user, password)
                .contentType("application/json")
                //.body("{\"name\":\"BookTest \",\"total\":\"3\",\"available\":\"3\",\"authors\":\"1238\",\"id\":"+generateBookId()+"}")
                .body("{\"name\":\"BookTest\",\"total\":\"3\",\"available\":\"3\",\"authors\":\"1238\",\"id\":\"" + bookId + "\"}")
                .post();
        JsonPath jsonPath = response.getBody().jsonPath();
        jsonPath.prettyPrint();

        Assert.assertEquals(jsonPath.getInt("authors"), 1238);

        HashMap<String, Object> out = jsonPath.get();
        out.put("id", bookId);
        return out;
    }

    public static HashMap<String, Object> editBook(String userName, String password, String email, HashMap<String, Object> book) {
        JsonPath p = given()
                .baseUri(String.format(
                        "http://35.205.170.236/api/v1/books/%s/%d",
                        userName,
                        book.get("id")
                ))
                .contentType("application/json")
                .auth().basic(email, password)
                .body(String.format(
                        "{\"name\":\"%s\",\"total\":%d,\"available\":\"%s\",\"authors\":\"%s\",\"id\":%d}",
                        (String)book.get("name"),
                        book.get("total"),
                        (String)book.get("available"),
                        (String)book.get("authors"),
                        book.get("id")
                ))
                .put()
                .jsonPath();

        HashMap<String, Object> out = p.get();

        return out;
    }

    public void getBook(String user, String password, int userId) {
        JsonPath p = given()
                .baseUri("http://35.205.170.236/api/v1/books/JohhnyUsername" + userId)
                .auth().basic(user, password)
                .contentType("application/json")
                .get()
                .jsonPath();
    }
}
