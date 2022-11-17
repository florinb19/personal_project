package API;

import io.restassured.path.json.JsonPath;
import org.testng.annotations.Test;

import java.util.Random;

import static io.restassured.RestAssured.given;

public class UserApi {

    public int idCreated = generateNumber();
    public String emailCreated = generateEmail();
    public String password = "Parola123";
    public void createUser(){
        given()
                .baseUri("http://35.205.170.236/api/v1/users/signup")
                .contentType("application/json")
                .body("{\n" +
                        "    \"username\": \"JohhnyUsername"+idCreated+"\",\n" +
                        "    \"name\": \"JohnnyName"+idCreated+"\",\n" +
                        "    \"email\": \" " + emailCreated + " \",\n" +
                        "    \"password\": \""+password+"\",\n" +
                        "    \"password_confirmation\": \""+password+"\"\n" +
                        "}")
                //when
                .post();
                //.then().statusCode(401);

    }

    public void viewUser() {
        JsonPath jsonPath = given()
                .baseUri("http://35.205.170.236/api/v1/users/view")
                .auth().basic(emailCreated,password)
                .get()
                .jsonPath();
//                .getBody().prettyPrint();
        //System.out.println("Id-ul este: " + jsonPath.get("id"));
        //int userId = jsonPath.get("id");
        //Assert.assertEquals(userId, 6718);
    }
    public String generateEmail() {
        return "Jonny" + idCreated + "@mail.ro";
    }
    public int generateNumber(){
        return new Random().nextInt(99999);
    }

}