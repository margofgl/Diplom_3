package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;

public class ForgotPasswordPage extends BasePage {

    private final By emailInput = By.xpath("//label[text()='Email']/following-sibling::input");
    private final By recoverButton = By.xpath("//button[text()='Восстановить']");
    private final By loginLink = By.xpath("//a[text()='Войти']");

    public ForgotPasswordPage(WebDriver driver) {
        super(driver);
    }

    @Step("Восстановить пароль для {email}")
    public void recoverPassword(String email) {
        type(emailInput, email);
        click(recoverButton);
    }

    @Step("Перейти на страницу логина")
    public LoginPage goToLogin() {
        click(loginLink);
        return new LoginPage(driver);
    }
}