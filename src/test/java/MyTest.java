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

    private static final String BASE_URL = System.getProperty("base.url", "https://the-internet.herokuapp.com");
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
    }

    @Test
    @DisplayName("Задание №1")
    public void test1() {
        //Открыть Chrome в headless режиме
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--headless");
        options.addArguments("--disable-gpu");
        startDriver(options);
        //Перейти на ресурс https://the-internet.herokuapp.com/inputs
        driver.get(BASE_URL + "/inputs");
        //В поле ввода ввести 42
        WebElement input = driver.findElement(By.tagName("input"));
        input.sendKeys("42");
        //Проверить, что текст соответствует введенному
        Assertions.assertEquals("42", input.getAttribute("value"));
    }

    @Test
    @DisplayName("Задание №2")
    public void test2() {
        // Открыть Chrome в режиме киоска
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--kiosk");
        startDriver(options);
        // Перейти на ресурс
        driver.get(BASE_URL + "/dialog");
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
    public void test3() {
        // Открыть Chrome в режиме полного экрана
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        startDriver(options);
        driver.manage().window().maximize();
        // Перейти на ресурс
        driver.get(BASE_URL + "/login");
        // В форму ввести имя tomsmith и пароль SuperSecretPassword!, нажать «Отправить»
        driver.findElement(By.id("username")).sendKeys("tomsmith");
        driver.findElement(By.id("password")).sendKeys("SuperSecretPassword!");
        driver.findElement(By.cssSelector("button[type='submit']")).click();
        // Проверить поле Logged in as tomsmith
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(5));
        WebElement parentP = wait.until(
                ExpectedConditions.visibilityOfElementLocated(By.xpath("//span[@id='username']/.."))
        );
        Assertions.assertEquals("Logged in as tomsmith", parentP.getText());
    }
}
