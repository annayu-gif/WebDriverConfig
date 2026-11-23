import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.*;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MyTest {
    WebDriver driver;

    @BeforeAll
    public static void install() {
        WebDriverManager.chromedriver().setup();
    }

    @AfterEach
    public void close() {
        if (driver != null)
            driver.close();

    }

    private void startDriver(ChromeOptions options) {
        driver = new ChromeDriver(options);
        driver.manage().timeouts().scriptTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(30));
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(30));
    }

    @Test
    @DisplayName("Задание №1")
    public void Test1() {
        //Открыть Chrome в headless режиме
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        startDriver(options);
        //Перейти на ресурс https://the-internet.herokuapp.com/inputs
        driver.get("https://the-internet.herokuapp.com/inputs");
        //В поле ввода ввести 42
        WebElement input = driver.findElement(By.tagName("input"));
        input.sendKeys("42");
        //Проверить, что текст соответствует введенному
        Assertions.assertEquals("42", input.getAttribute("value"));
    }

    @Test
    @DisplayName("Задание №2")
    public void Test2() {
        // Открыть Chrome в режиме киоска
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        startDriver(options);
        // Перейти на ресурс
        driver.get("https://the-internet.herokuapp.com/dialog");
        // Нажать на «Open modal dialog»
        driver.findElement(By.xpath("//button[text()='Open modal dialog']")).click();
        // Проверить, что открылось модальное окно
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(10));
        WebElement modal = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.tagName("dialog")));
        Assertions.assertTrue(modal.isDisplayed());
    }


    @Test
    @DisplayName("Задание №3")
    public void Test3() {
        // Открыть Chrome в режиме полного экрана
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        startDriver(options);
        driver.manage().window().maximize();
        // Перейти на ресурс
        driver.get("https://the-internet.herokuapp.com/login");
        // В форму ввести имя tomsmith и пароль SuperSecretPassword!, нажать «Отправить»
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Проверить поле Logged in as tomsmith
        WebElement username = driver.findElement(
                By.xpath("//p[contains(text(), 'Logged in as')]/span"));
        Assertions.assertEquals("tomsmith", username.getText());
    }
}
