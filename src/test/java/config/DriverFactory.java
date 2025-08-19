package config;

import io.github.bonigarcia.wdm.WebDriverManager;
import io.qameta.allure.Step;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;

import java.time.Duration;

public class DriverFactory {

    @Step("Старт драйвера для браузера: {browser}")
    public static WebDriver create(Browser browser) {
        switch (browser) {
            case yandex:
                return createYandex();
            case chrome:
            default:
                return createChrome();
        }
    }

    private static WebDriver createChrome() {
        WebDriverManager.chromedriver().setup();
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--start-maximized");
        options.addArguments("--remote-allow-origins=*");
        return new ChromeDriver(options);
    }

    private static WebDriver createYandex() {
        String yandexDriver = System.getProperty("yandex.driver");
        String yandexBinary = System.getProperty("yandex.binary");
        if (yandexDriver == null || yandexBinary == null) {
            throw new IllegalStateException("Укажи -Dyandex.driver и -Dyandex.binary (см. pom.xml profile yandex)");
        }
        System.setProperty("webdriver.chrome.driver", yandexDriver);
        ChromeOptions options = new ChromeOptions();
        options.setBinary(yandexBinary);
        options.addArguments("--start-maximized");
        return new ChromeDriver(options);
    }

    public static void tune(WebDriver driver) {
        driver.manage().timeouts().implicitlyWait(Duration.ofSeconds(3));
        driver.manage().timeouts().pageLoadTimeout(Duration.ofSeconds(20));
    }
}