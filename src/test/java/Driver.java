//import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

public class Driver {
    private WebDriver driver;
    private static Driver instance;

    private Driver() {
    }

    public static Driver getInstance() {
        if (instance == null)
            instance = new Driver();
        return instance;
    }

    public Driver setWebDriver() {
//        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver();
        return this;
    }

    public WebDriver getWebDriver() {
        return driver;
    }

    public void deleteWebDriver() {
        if (driver != null) {
            driver.quit();
        }
        driver = null;
    }
}

