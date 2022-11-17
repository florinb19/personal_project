package Tests;

import API.AuthorApi;
import API.BookApi;
import API.UserApi;
import Selenium.SeleniumHelper;
import org.testng.annotations.Test;

import java.util.HashMap;
import java.util.Map;

public class UserAuthTests extends SeleniumHelper {
    UserApi userAPI = new UserApi();
    AuthorApi authorApi = new AuthorApi();
    BookApi bookApi = new BookApi();

    //@Test(priority = 1)
    public void E2ECreateApiLoginSelenium() {
        userAPI.createUser();
        logInApp(userAPI.emailCreated, userAPI.password);
    }

    @Test(priority = 2)
    public void createAuthorTest() {
        authorApi.createAuthorApi(userAPI.emailCreated, userAPI.password, userAPI.idCreated, authorApi.authorId);
    }

    @Test(priority = 3)
    public void createBoookTest() {
        bookApi.createBook(userAPI.emailCreated, userAPI.password, userAPI.idCreated);
    }

    @Test(priority = 4)
    public void lendBookTest() throws InterruptedException {
        canBeLent(bookApi.bookId);
    }

    @Test(priority = 5)
    public void createEmptyBookTest() throws InterruptedException {
        emptyBookCreation(userAPI.emailCreated, userAPI.password);
    }

    @Test(priority = 6)
    public void deleteBook() throws InterruptedException {
        userAPI.createUser();
        deleteBookSelenium(userAPI.generateEmail(), userAPI.password, BookApi.bookId);
    }

    @Test
    public void editBookTest() throws InterruptedException {
        userAPI.createUser();
        editBookSelenium(userAPI.emailCreated, userAPI.password, authorApi.authorId);
        HashMap <String, Object> book = bookApi.createBook(userAPI.emailCreated, userAPI.password, userAPI.idCreated);
        book.put("name", "ceva orice");
        book.put("total", 666);
        book.put("available", "7");
        book.put("authors", "1");
        book = bookApi.editBook("JohhnyUsername"+ userAPI.idCreated, userAPI.password, userAPI.emailCreated, book);
        for (Map.Entry<String, Object> entry : book.entrySet()) {
            System.out.println("\t" + entry.getKey() + " : " + entry.getValue() + " - " + entry.getValue().getClass());
        }
    }

}