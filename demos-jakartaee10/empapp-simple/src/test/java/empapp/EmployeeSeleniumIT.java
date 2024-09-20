package empapp;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.time.temporal.ChronoUnit;
import java.util.List;

import static org.junit.jupiter.api.Assertions.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

class EmployeeSeleniumIT {

    WebDriver webDriver;

    @BeforeEach
    void init() {
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--disable-search-engine-choice-screen");
        webDriver = new ChromeDriver(options);
    }

    @AfterEach
    void destroy() {
        webDriver.close();
    }

    @Test
    void saveThenList() {
        webDriver.get("http://localhost:8080/empapp/employees");
        webDriver.findElement(By.id("name-input")).sendKeys("John Smith2");
        webDriver.findElement(By.id("employee-form")).submit();

        WebDriverWait wait = new WebDriverWait(webDriver, Duration.of(30, ChronoUnit.SECONDS));
        wait.until(presenceOfElementLocated(
                By.xpath("//td[text() = 'John Smith2']")));

        List<WebElement> tds = webDriver
                .findElements(By.xpath("//td"));
        assertEquals("John Smith", tds.get(tds.size() - 1).getText());
    }
}
