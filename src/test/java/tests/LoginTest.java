package tests;

import api.UserClient;
import api.dto.Credentials;
import api.dto.User;
import config.TestBase;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.ForgotPasswordPage;
import pages.LoginPage;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class LoginTest extends TestBase {

    private String email;
    private final String password = "qwerty1";
    private String accessToken;
    private UserClient api;

    @Before
    public void createUser() {
        email = "ui_" + System.currentTimeMillis() + "@mail.test";
        api = new UserClient(baseUrl);

        api.register(new User(email, password, "UI QA"))
                .then().statusCode(200);
    }

    @After
    public void deleteUser() {
        if (accessToken != null) {
            api.delete(accessToken);
        }
    }

    private void assertLoggedIn() {
        assertTrue("Кнопка 'Оформить заказ' не найдена после логина",
                new MainPage(driver).isVisibleByButtonText("Оформить заказ"));
    }

    private void loginAndSaveToken() {
        accessToken = api.login(new Credentials(email, password))
                .then().statusCode(200)
                .extract().path("accessToken");
    }

    @Test
    @Description("Вход по кнопке 'Войти в аккаунт' на главной")
    public void loginViaMainButton() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);

        main.clickLoginMain()
                .login(email, password);

        loginAndSaveToken();
        assertLoggedIn();
    }

    @Test
    @Description("Вход через кнопку 'Личный Кабинет'")
    public void loginViaAccountButton() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);

        main.goToAccount();
        new LoginPage(driver).login(email, password);

        loginAndSaveToken();
        assertLoggedIn();
    }

    @Test
    @Description("Вход через кнопку в форме регистрации")
    public void loginFromRegisterForm() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);

        main.clickLoginMain()
                .goToRegister()
                .goToLogin()
                .login(email, password);

        loginAndSaveToken();
        assertLoggedIn();
    }

    @Test
    @Description("Вход через кнопку в форме восстановления пароля")
    public void loginFromForgotPassword() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);

        main.clickLoginMain()
                .goToForgot()
                .goToLogin()
                .login(email, password);

        loginAndSaveToken();
        assertLoggedIn();
    }
}