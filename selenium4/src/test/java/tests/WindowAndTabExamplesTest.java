package tests;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WindowType;
import org.openqa.selenium.chrome.ChromeDriver;
import org.testng.annotations.Test;

public class WindowAndTabExamplesTest {

    @Test(testName = "Open new window")
    public void newWindow() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://google.com");
        Thread.sleep(5000);

        driver.switchTo().newWindow(WindowType.WINDOW);
        driver.get("https://yandex.ru");
        Thread.sleep(5000);

        driver.quit();
    }

    @Test(testName = "Open new tab")
    public void newTab() throws InterruptedException {
        WebDriver driver = new ChromeDriver();

        driver.get("https://google.com");
        Thread.sleep(5000);

        driver.switchTo().newWindow(WindowType.TAB);
        driver.get("https://yandex.ru");
        Thread.sleep(5000);

        driver.quit();
    }
}
