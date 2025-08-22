package tests;

import api.UserClient;
import api.dto.Credentials;
import api.dto.User;
import config.TestBase;
import io.qameta.allure.Description;
import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import pages.AccountPage;
import pages.LoginPage;
import pages.MainPage;

import static org.junit.Assert.assertTrue;

public class AccountNavigationTest extends TestBase {
    private String email;
    private final String password = "qwerty1";
    private String accessToken;

    @Before
    public void prepare() {
        email = "ui_" + System.currentTimeMillis() + "@mail.test";
        UserClient api = new UserClient(baseUrl);
        api.register(new User(email, password, "UI QA")).then().statusCode(200);
        accessToken = api.login(new Credentials(email, password)).then().statusCode(200).extract().path("accessToken");
    }

    @After
    public void cleanup() {
        if (accessToken != null) new UserClient(baseUrl).delete(accessToken);
    }

    @Test
    @Description("Переход в Личный кабинет по клику на 'Личный Кабинет'")
    public void goToAccount() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);
        main.goToAccount();

        LoginPage loginPage = new LoginPage(driver);
        AccountPage accountPage = new AccountPage(driver);

        assertTrue("Не открылась страница логина/аккаунта",
                loginPage.isVisibleByButtonText("Войти") ||
                        accountPage.isVisibleByButtonText("Выход")
        );
    }

    @Test
    @Description("Из личного кабинета переход в Конструктор через ссылку 'Конструктор' и по логотипу")
    public void fromAccountToConstructor() {
        MainPage main = new MainPage(driver);
        main.open(baseUrl);
        main.goToAccount();

        new LoginPage(driver).login(email, password);

        AccountPage acc = new AccountPage(driver);
        acc.goToConstructorFromHeader();
        assertTrue(new MainPage(driver).isVisibleByText("Соберите бургер"));

        main.goToAccount();
        acc.goToConstructorByLogo();
        assertTrue(new MainPage(driver).isVisibleByText("Соберите бургер"));
    }
}