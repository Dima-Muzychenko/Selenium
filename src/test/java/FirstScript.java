import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;

import java.time.Duration;

import static junit.framework.TestCase.assertEquals;


public class FirstScript {
    public static void main(String[] args) {
        WebDriver driver = new ChromeDriver();
        driver.get("https://www.selenium.dev/selenium/web/web-form.html");
    }

    @Test
    public void eightComponents(){
        WebDriver driver = new ChromeDriver();

        driver.get("https://www.selenium.dev/selenium/web/web-form.html");

        String title = driver.getTitle();
        assertEquals("Web form", title);

        driver.manage().timeouts().implicitlyWait(Duration.ofMillis(5000));

        //отримаємо ссилку на поле вводу даних
        WebElement textBox = driver.findElement(By.name("my-text"));
        //отримаємо ссилку на кнопку підтвердження
        WebElement submitButton = driver.findElement(By.cssSelector("button"));

        //в поле вводимо дані та підтверджуємо (після цього нас перекине на іншу сторінку та виведе повідомлення)
        textBox.sendKeys("Selenium");
        submitButton.click();

        //Зчитуємо повідомлення та перевіряєм, чи справді це те повідомлення
        WebElement message = driver.findElement(By.id("message"));
        String value = message.getText();
        assertEquals("Received!", value);

        driver.quit();
    }
}