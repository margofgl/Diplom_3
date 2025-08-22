package tests;

import api.UserClient;
import api.dto.Credentials;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Test;
import org.openqa.selenium.By;
import pages.LoginPage;
import pages.MainPage;
import pages.RegisterPage;
import config.TestBase;

import static org.junit.Assert.assertTrue;

public class RegistrationTest extends TestBase {

    private String accessToken;

    @After
    public void clean() {
        if (accessToken != null) {
            new UserClient(baseUrl).delete(accessToken);
        }
    }

    private static String uniqEmail() {
        return "ui_" + System.currentTimeMillis() + "@mail.test";
    }

    @Test
    @Description("Успешная регистрация с валидным паролем (>=6), редирект на страницу логина")
    public void successfulRegistration() {
        MainPage main = new MainPage(driver);
        main.open();

        LoginPage login = main.clickLoginMain();
        RegisterPage reg = login.goToRegister();

        String email = uniqEmail();
        String pass = "qwerty1";
        String name = "UI QA";

        reg.register(name, email, pass);

        LoginPage loginPage = new LoginPage(driver);
        assertTrue("Не отобразилась форма логина после регистрации",
                loginPage.isVisible(By.xpath("//button[text()='Войти']")));

        UserClient api = new UserClient(baseUrl);
        accessToken = api.login(new Credentials(email, pass))
                .then().statusCode(200)
                .extract().path("accessToken");
    }

    @Test
    @Description("Ошибка при коротком пароле (<6)")
    public void registrationInvalidPassword() {
        MainPage main = new MainPage(driver);
        main.open();

        LoginPage login = main.clickLoginMain();
        RegisterPage reg = login.goToRegister();

        String email = uniqEmail();
        reg.register("UI QA", email, "12345");

        assertTrue("Не появилась ошибка под полем пароля", reg.hasPasswordError());
    }
}