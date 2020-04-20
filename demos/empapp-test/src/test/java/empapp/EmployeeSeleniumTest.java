package empapp;

import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

import static org.junit.Assert.assertEquals;
import static org.openqa.selenium.support.ui.ExpectedConditions.presenceOfElementLocated;

public class EmployeeSeleniumTest {

    @Test
    public void testSaveThenList() {
        System.setProperty("webdriver.gecko.driver",
                "C:\\Java\\geckodriver-v0.23.0-win64\\geckodriver.exe");

        FirefoxDriver driver = new FirefoxDriver();
        driver.get("http://localhost:8080/empapp/employees");
        driver.findElementById("name-input").sendKeys("John Smith2");
        driver.findElementById("employee-form").submit();

        WebDriverWait wait = new WebDriverWait(driver, 30);
        wait.until(presenceOfElementLocated(
                By.xpath("//td[text() = 'John Smith2']")));

        List<WebElement> tds = driver
                .findElements(By.xpath("//td"));
        assertEquals("John Smith", tds.get(tds.size() - 1).getText());
        driver.close();
    }
}
