import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.annotations.AfterTest;
import org.testng.annotations.BeforeTest;
import org.testng.annotations.Test;

import java.time.Duration;
import java.util.List;

import static junit.framework.TestCase.*;

public abstract class TestsClass {
    protected final String url = "https://demoqa.com/books";
    protected final String urlLogin = "https://demoqa.com/login";
    protected final String urlProfile = "https://demoqa.com/profile";
    protected WebDriver driver;
    protected final String username = "Dima";
    protected final String password = "0_!aDima";
    protected WebDriverWait wait;

    @BeforeTest
    public void setUpUi() {
        driver = Driver.getInstance().setWebDriver().getWebDriver();
        driver.manage().window().maximize();

        //implicit wait
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(10));
        //explicit wait
        wait = new WebDriverWait(driver, Duration.ofSeconds(2));
    }

    @AfterTest
    public void tearDown() {
        Driver.getInstance().deleteWebDriver();
    }

    @Test(description = "login with valid data")
    public void loginUserWithValidData() {
        driver.get(urlLogin);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        elements.get(0).sendKeys(username);
        elements.get(1).sendKeys(password);
        WebElement loginButton = driver.findElement(By.cssSelector("#login"));
        loginButton.click();
        WebElement nameUser = driver.findElement(By.id("userName-value"));
        assertTrue(nameUser.isDisplayed());
    }


    @Test(description = "login with invalid data")
    public void loginWithInvalidData() {
        driver.get(urlLogin);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        elements.get(0).sendKeys(username);
        elements.get(1).sendKeys(password + "invalid");
        WebElement loginButton = driver.findElement(By.cssSelector("#login"));
        loginButton.click();
        WebElement invalidMessage = driver.findElement(By.xpath("//*[contains(text(),'Invalid username or password!')]"));
        assertTrue(invalidMessage.isDisplayed());
    }


    @Test(description = "add book to collection")
    public void addBookToCollection() {
        driver.get(urlLogin);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        elements.get(0).sendKeys(username);
        elements.get(1).sendKeys(password);
        WebElement loginButton = driver.findElement(By.cssSelector("#login"));
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userName-value")));

        driver.get(url);
        WebElement linkBook = driver.findElement(By.linkText("Git Pocket Guide"));
        linkBook.click();

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add To Your Collection']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        addButton.click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Book added to your collection.", confirmAlert.getText());
        confirmAlert.accept();

        driver.get(urlProfile);
        WebElement bookLink = driver.findElement(By.partialLinkText("Git Pocket Guide"));
        assertTrue(bookLink.isDisplayed());
    }

    @Test(description = "add exists book to collection")
    public void addExistsBookToCollection() {
        driver.get(urlLogin);
        List<WebElement> elements = driver.findElements(By.tagName("input"));
        elements.get(0).sendKeys(username);
        elements.get(1).sendKeys(password);
        WebElement loginButton = driver.findElement(By.cssSelector("#login"));
        loginButton.click();
        wait.until(ExpectedConditions.presenceOfElementLocated(By.id("userName-value")));

        driver.get(url);
        WebElement linkBook = driver.findElement(By.linkText("Git Pocket Guide"));
        linkBook.click();

        WebElement addButton = driver.findElement(By.xpath("//button[text()='Add To Your Collection']"));
        JavascriptExecutor js = (JavascriptExecutor) driver;
        js.executeScript("window.scrollBy(0,document.body.scrollHeight)");
        addButton.click();
        Alert confirmAlert = wait.until(ExpectedConditions.alertIsPresent());
        assertEquals("Book already present in the your collection!", confirmAlert.getText());
        confirmAlert.accept();
    }

}

