package tests;

import api.UserClient;
import api.dto.Credentials;
import api.dto.User;
import config.TestBase;
import io.qameta.allure.Description;
import io.qameta.allure.Step;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import pages.AccountPage;
import pages.LoginPage;
import pages.MainPage;

import java.time.Duration;

import static org.junit.Assert.assertTrue;

public class LogoutTest extends TestBase {

    private String email;
    private final String password = "qwerty1";
    private String accessToken;
    private UserClient api;

    @Before
    @Step("Создание тестового пользователя и логин через UI")
    public void createUserAndLogin() {
        email = "ui_" + System.currentTimeMillis() + "@mail.test";
        api = new UserClient(baseUrl);

        api.register(new User(email, password, "UI QA")).then().statusCode(200);
        accessToken = api.login(new Credentials(email, password))
                .then().statusCode(200)
                .extract().path("accessToken");

        MainPage main = new MainPage(driver);
        main.open();
        main.goToAccount();
        new LoginPage(driver).login(email, password);

        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(ExpectedConditions.visibilityOfElementLocated(By.xpath("//button[text()='Выход']")));
    }

    @After
    @Step("Удаление тестового пользователя")
    public void cleanup() {
        if (accessToken != null) {
            api.delete(accessToken);
        }
    }

    @Test
    @Description("Выход из аккаунта")
    public void logoutTest() {
        AccountPage account = new AccountPage(driver);
        account.logout();

        LoginPage loginPage = new LoginPage(driver);
        new WebDriverWait(driver, Duration.ofSeconds(10))
                .until(driver -> loginPage.isVisibleByButtonText("Войти"));

        assertTrue("Не отобразилась кнопка 'Войти' после логаута",
                loginPage.isVisibleByButtonText("Войти"));
    }
}