package config;

import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.openqa.selenium.WebDriver;
import config.DriverFactory;

public class TestBase {
    protected WebDriver driver;
    protected String baseUrl;

    @Before
    @Step("Инициализация теста")
    public void setUp() {
        String browserProp = System.getProperty("browser", "chrome").toLowerCase();
        Browser browser;
        try {
            browser = Browser.valueOf(browserProp);
        } catch (IllegalArgumentException e) {
            System.out.println("Неизвестный браузер: " + browserProp + ". Будет использован Chrome по умолчанию.");
            browser = Browser.chrome;
        }

        driver = DriverFactory.create(browser);
        DriverFactory.tune(driver);

        baseUrl = System.getProperty("baseUrl", "https://stellarburgers.nomoreparties.site/");
    }

    @After
    @Step("Завершение теста")
    public void tearDown() {
        if (driver != null) {
            driver.quit();
        }
    }
}
