package tests;


import org.openqa.selenium.By;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.devtools.DevTools;
import org.openqa.selenium.devtools.v94.emulation.Emulation;
import org.openqa.selenium.devtools.v94.fetch.Fetch;
import org.openqa.selenium.devtools.v94.log.Log;
import org.openqa.selenium.devtools.v94.network.Network;
import org.openqa.selenium.devtools.v94.network.model.ConnectionType;
import org.openqa.selenium.devtools.v94.performance.Performance;
import org.openqa.selenium.devtools.v94.performance.model.Metric;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.testng.annotations.Test;

import java.net.MalformedURLException;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.Optional;
import java.util.stream.Collectors;

import static java.util.Base64.getEncoder;

public class DevToolsExamplesTest {

    @Test(testName = "Simulating Device Mode")
    public void simulatingDeviceMode() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        Map deviceMetrics = new HashMap() {{
            put("width", 600);
            put("height", 1000);
            put("mobile", true);
            put("deviceScaleFactor", 50);
        }};
        driver.executeCdpCommand("Emulation.setDeviceMetricsOverride", deviceMetrics);

        driver.get("https://www.google.com");
        Thread.sleep(10000);
        driver.quit();
    }

    @Test(testName = "Simulate Network Speed")
    public void simulateNetworkSpeed() throws InterruptedException, MalformedURLException {
        ChromeDriver driver = new ChromeDriver();
        Thread.sleep(5000);

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.send(Network.emulateNetworkConditions(
                false,
                2,
                10000,
                5000,
                Optional.of(ConnectionType.CELLULAR2G)
        ));

        driver.get("https://www.google.com");
        Thread.sleep(5000);
        driver.quit();
    }

    @Test(testName = "Mocking Geolocation")
    public void mockingGeoLocation() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

//        devTools.send(Emulation.setGeolocationOverride(
//                Optional.of(35.8235),
//                Optional.of(-78.8256),
//                Optional.of(100)));
//        devTools.send(Emulation.setGeolocationOverride(
//                Optional.of(53.0444400),
//                Optional.of(158.6507600),
//                Optional.of(100)));

        driver.get("https://my-location.org/");
        Thread.sleep(20000);
        driver.quit();
    }

    @Test(testName = "Capture HTTP Requests")
    public void captureHttpRequests() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Network.enable(Optional.empty(), Optional.empty(), Optional.empty()));
        devTools.addListener(Network.requestWillBeSent(),
                entry -> {
                    System.out.println("Request URI : " + entry.getRequest().getUrl() + "\n"
                            + " With method : " + entry.getRequest().getMethod() + "\n");
                    entry.getRequest().getMethod();
                });

        driver.get("https://www.google.com");
        driver.quit();
    }

    @Test(testName = "Mocking Responses")
    public void mockingResponses() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Fetch.enable(Optional.empty(), Optional.empty()));
        devTools.addListener(Fetch.requestPaused(),
                request -> {
                    if (request.getRequest().getUrl().contains("/suggest/suggest-ya.cgi")) {
                        String body =
                                getEncoder().encodeToString(
                                        "[\"test\",[[\"\",\"Hello from devtools\",{\"sg_weight\":0,\"hl\":[[0,4]]}]],{\"r\":149,\"pers_options\":1,\"log\":\"sgtype:BWT\"}]".getBytes()
                                );
                        devTools.send(Fetch.fulfillRequest(
                                request.getRequestId(),
                                200,
                                Optional.empty(),
                                Optional.empty(),
                                Optional.of(body),
                                Optional.empty()
                        ));
                    } else {
                        devTools.send(Fetch.continueRequest(
                                request.getRequestId(),
                                Optional.of(request.getRequest().getUrl()),
                                Optional.of(request.getRequest().getMethod()),
                                request.getRequest().getPostData(),
                                Optional.empty(),
                                Optional.empty()
                        ));
                    }
                });

        driver.get("https://www.yandex.ru");
        driver.findElement(By.id("text")).sendKeys("test");
        Thread.sleep(30000);
        driver.quit();
    }

    @Test(testName = "Access Console logs")
    public void accessConsoleLog() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Log.enable());
        devTools.addListener(Log.entryAdded(),
                logEntry -> {
                    System.out.println("log: " + logEntry.getText());
                    System.out.println("level: " + logEntry.getLevel());
                });

        driver.get("http://the-internet.herokuapp.com/broken_images");
        driver.quit();
    }

    @Test(testName = "Capturing Performance Metrics")
    public void capturingPerformanceMetrics() throws InterruptedException {
        ChromeDriver driver = new ChromeDriver();

        DevTools devTools = driver.getDevTools();
        devTools.createSession();

        devTools.send(Performance.enable(Optional.empty()));

        driver.get("https://www.google.org");

        List<Metric> metrics = devTools.send(Performance.getMetrics());
        List<String> metricNames = metrics.stream()
                .map(o -> o.getName())
                .collect(Collectors.toList());

        devTools.send(Performance.disable());

        List<String> metricsToCheck = Arrays.asList(
                "Timestamp", "Documents", "Frames", "JSEventListeners",
                "LayoutObjects", "MediaKeySessions", "Nodes",
                "Resources", "DomContentLoaded", "NavigationStart");

        System.out.println("------------------------------------------------");
        metricsToCheck.forEach(metric -> System.out.println(metric +
                " is : " + metrics.get(metricNames.indexOf(metric)).getValue()));
        System.out.println("------------------------------------------------");
        driver.quit();

    }
}
