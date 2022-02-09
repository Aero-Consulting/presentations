package tests;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.Assert;
import org.testng.annotations.Test;

import static org.openqa.selenium.support.locators.RelativeLocator.with;

public class RelativeLocatorsExamplesTest {

    @Test(testName = "New features Relative locators")
    public void relativeLocatorsTest() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.manage().window().maximize();
        driver.get("https://account.jetbrains.com/login");

        WebElement passwordInput = driver.findElement(By.name("password"));
        highlightElement(passwordInput, driver, "red");

        WebElement elementAbove = driver.findElement(with(By.tagName("input")).above(passwordInput));
        highlightElement(elementAbove, driver, "blue");

        WebElement elementBelow = driver.findElement(with(By.tagName("button")).below(passwordInput));
        highlightElement(elementBelow, driver, "green");

        WebElement elementNear = driver.findElement(with(By.tagName("a")).near(elementBelow));
        highlightElement(elementNear, driver, "black");
        String elementNearText = elementNear.getText();
        Assert.assertEquals(elementNearText, "Forgot password?");

        WebElement gitlabLogo = driver.findElement(By.xpath("//a[contains(@href,'gitlab')]"));
        highlightElement(gitlabLogo, driver, "red");

        WebElement elementToLeftOf = driver.findElement(with(By.tagName("a")).toLeftOf(gitlabLogo));
        highlightElement(elementToLeftOf, driver, "blue");

        WebElement elementToRightOf = driver.findElement(with(By.tagName("a")).toRightOf(gitlabLogo));
        highlightElement(elementToRightOf, driver, "green");

        Thread.sleep(10000);
        driver.quit();
    }

    private void highlightElement(WebElement element, WebDriver driver, String color) {
        ((JavascriptExecutor) driver).executeScript(
                "arguments[0].style.border='3px solid ' + arguments[1]",
                element,
                color
        );
    }
}
