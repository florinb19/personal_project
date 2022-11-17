package Tests;

import API.AuthorApi;
import API.BookApi;
import API.UserApi;
import Selenium.SeleniumHelper;
import org.testng.annotations.Test;

public class DeleteBookTest extends SeleniumHelper {

    UserApi userAPI = new UserApi();
    AuthorApi authorApi = new AuthorApi();
    BookApi bookApi = new BookApi();

    @Test
    public void deleteBookTest() throws InterruptedException {


        userAPI.createUser();
        //logInApp(userAPI.emailCreated, userAPI.password);
        bookApi.createBook(userAPI.emailCreated, userAPI.password, userAPI.idCreated);
        deleteBookSelenium(userAPI.emailCreated, userAPI.password, BookApi.bookId);
    }
}
