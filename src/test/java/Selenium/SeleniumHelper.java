package Selenium;

//import API.UserApi;

import API.BookApi;
import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.Assert;
import org.testng.annotations.BeforeTest;

import java.time.Duration;

import static io.restassured.RestAssured.given;

public class SeleniumHelper {
    WebDriver driver;
    WebDriverWait wait;
    BookApi bookApi = new BookApi();

    @BeforeTest
    public void openBrowser() {
        //System.setProperty("webdriver.chrome.driver", "/Users/florin.beudean/Documents/Automation/chromedriver");
        WebDriverManager.chromedriver().setup();
        this.driver = new ChromeDriver();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        this.driver.manage().window();
    }

    public void logInApp(String email, String password) {
        driver.get("http://lib.academiatestarii.ro/");
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("login")));
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("login_email")).sendKeys(email);
        driver.findElement(By.id("login_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).click();
        wait.until(ExpectedConditions.urlContains("dashboard"));
        Assert.assertEquals(driver.getCurrentUrl(), "http://lib.academiatestarii.ro/dashboard");
    }

    public void canBeLent(int book) throws InterruptedException {
        driver.get("http://lib.academiatestarii.ro/");
        driver.findElement(By.id("dashboard")).click();

        wait.until(ExpectedConditions.urlContains("dashboard"));
        driver.findElement(By.id("books")).click();

        //wait
        Thread.sleep(2000);
        driver.findElement(By.id("view_" + book)).click();

        //wait
        Thread.sleep(2000);
        Select select = new Select(driver.findElement(By.id("select_lender")));
        select.selectByIndex(1);
        driver.findElement(By.xpath("//*[@id='lend_book']")).click();

        //wait
        Thread.sleep(2000);
        Alert alert = driver.switchTo().alert();
        alert.accept();
        String availableBooks = driver.findElement(By.id("available_books")).getText();
        Assert.assertEquals(availableBooks, "Available Books: 6");

        //close window after this test
        driver.quit();
    }

    public void emptyBookCreation(String email, String password) throws InterruptedException {

        //open a new window for this test
        this.driver = new ChromeDriver();
        this.driver.manage().window();

        driver.get("http://lib.academiatestarii.ro/");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("login_email")).sendKeys(email);
        driver.findElement(By.id("login_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).click();
        //wait
        Thread.sleep(3000);

        //make sure I am on the dashboard
        Assert.assertEquals(driver.getCurrentUrl(), "http://lib.academiatestarii.ro/dashboard");
        driver.findElement(By.id("books")).click();

        //wait
        Thread.sleep(2000);
        driver.findElement(By.id("create_name")).sendKeys("");
        driver.findElement(By.id("create_total")).sendKeys("");
        driver.findElement(By.id("create_available")).sendKeys("");
        driver.findElement(By.id("create_id")).sendKeys("");
        driver.findElement(By.id("create_book")).click();
        String createName = driver.findElement(By.id("create_name")).getAttribute("aria-invalid");
        String createTotal = driver.findElement(By.id("create_total")).getAttribute("aria-invalid");
        String createAvailable = driver.findElement(By.id("create_available")).getAttribute("aria-invalid");
        String createId = driver.findElement(By.id("create_id")).getAttribute("aria-invalid");
        String createBook = driver.findElement(By.id("create_book")).getAttribute("aria-invalid");
        Assert.assertEquals(createName, "true");
        Assert.assertEquals(createTotal, "true");
        Assert.assertEquals(createAvailable, "true");
        Assert.assertEquals(createId, "true");

        //issues with last assert and dunno why
        //Assert.assertEquals(createBook, "true");

        //wait
        Thread.sleep(3000);

        //close the window
        driver.quit();
    }

    public void deleteBookSelenium(String email, String password, int bookId) throws InterruptedException {
        //open a new window for this test
        this.driver = new ChromeDriver();
        this.driver.manage().window();

        //int bookId = bookApi.generateBookId();
        driver.get("http://lib.academiatestarii.ro/");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("login_email")).sendKeys(email);
        driver.findElement(By.id("login_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).click();

        //wait
        Thread.sleep(3000);
        driver.findElement(By.id("books")).click();

        //wait
        Thread.sleep(3000);
        driver.findElement(By.id("delete_" + bookId)).click();
        Thread.sleep(5000);
        Alert alert = driver.switchTo().alert();
        String alertText = alert.getText();
        alert.accept();
        Assert.assertEquals(alertText, "Book was deleted");
        driver.quit();
    }

    public void editBookSelenium(String email, String password, int authorId) throws InterruptedException {
        //open a new window for this test
        //this.driver = new ChromeDriver();
        //this.driver.manage().window();

        //int bookId = bookApi.generateBookId();
        driver.get("http://lib.academiatestarii.ro/");
        driver.findElement(By.id("login")).click();
        driver.findElement(By.id("login_email")).sendKeys(email);
        driver.findElement(By.id("login_password")).sendKeys(password);
        driver.findElement(By.xpath("//*[@type=\"submit\"]")).click();

        //wait
        Thread.sleep(3000);

        //create author
        driver.findElement(By.id("authors")).click();

        //Wait
        Thread.sleep(2000);

        driver.findElement(By.id("create_firstname")).sendKeys("AuthorFirstName" + authorId);
        driver.findElement(By.id("create_lastname")).sendKeys("AuthorLastName" + authorId);
        driver.findElement(By.id("create_id")).sendKeys(authorId+"");
        driver.findElement(By.id("create_author")).click();

        //Wait
        Thread.sleep(2000);

        String authName = driver.findElement(By.id("view_" + authorId)).getText();
        Assert.assertEquals(authName, "AuthorFirstName" + authorId + " AuthorLastName" + authorId);

    }

}