package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class LoginPage extends BasePage {

    private final By emailInput    = By.xpath("//input[@name='name']");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By loginButton   = By.xpath("//button[text()='Войти']");
    private final By registerLink  = By.xpath("//a[text()='Зарегистрироваться']");
    private final By forgotLink    = By.xpath("//a[text()='Восстановить пароль']");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Step("Авторизация с email {email}")
    public void login(String email, String password) {
        type(emailInput, email);
        type(passwordInput, password);
        click(loginButton);
    }

    @Step("Перейти на страницу регистрации")
    public RegisterPage goToRegister() {
        click(registerLink);
        return new RegisterPage(driver);
    }

    @Step("Перейти на страницу восстановления пароля")
    public ForgotPasswordPage goToForgot() {
        click(forgotLink);
        return new ForgotPasswordPage(driver);
    }
    public boolean isVisibleByButtonText(String text) {
        return super.isVisibleByButtonText(text);
    }

    public boolean isVisibleByText(String text) {
        return super.isVisibleByText(text);
    }
}