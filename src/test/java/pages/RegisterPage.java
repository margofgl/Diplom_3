package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class RegisterPage extends BasePage {

    private final By nameInput = By.xpath("//label[text()='Имя']/following-sibling::input");
    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By passwordInput = By.xpath("//input[@type='password']");
    private final By registerButton = By.xpath("//button[text()='Зарегистрироваться']");
    private final By loginLink = By.xpath("//a[text()='Войти']");
    private final By passwordError = By.xpath("//p[contains(text(),'пароль') and contains(@class,'input__error')]");

    public RegisterPage(WebDriver driver) {
        super(driver);
    }

    @Step("Заполнить форму регистрации: {name}, {email}, {password}")
    public void register(String name, String email, String password) {
        type(nameInput, name);
        type(emailInput, email);
        type(passwordInput, password);
        click(registerButton);
    }

    @Step("Перейти на страницу логина")
    public LoginPage goToLogin() {
        click(loginLink);
        return new LoginPage(driver);
    }

    @Step("Проверить наличие ошибки пароля")
    public boolean hasPasswordError() {
        return isVisible(passwordError);
    }

    @Step("Получить текст ошибки")
    public String getErrorMessage() {
        return getText(passwordError);
    }
}