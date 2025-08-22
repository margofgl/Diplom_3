package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class AccountPage extends BasePage<AccountPage> {

    private final By logoutBtn = By.xpath("//button[text()='Выход']");

    public AccountPage(WebDriver driver) {
        super(driver);
    }

    @Step("Выйти из аккаунта")
    public void logout() {
        clickWithScroll(logoutBtn, 15);
    }

    @Step("Переход в 'Конструктор' через хедер")
    public void goToConstructorFromHeader() {
        clickWithScroll(By.xpath("//p[text()='Конструктор']"), 10);
    }

    @Step("Переход в 'Конструктор' кликом по логотипу")
    public void goToConstructorByLogo() {
        clickWithScroll(By.xpath("//div[contains(@class,'AppHeader_header__logo')]"), 10);
    }

    public boolean isVisibleByButtonText(String text) {
        return super.isVisibleByButtonText(text);
    }

    public boolean isVisibleByText(String text) {
        return super.isVisibleByText(text);
    }

    private void clickWithScroll(By locator, int timeoutSeconds) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(timeoutSeconds));
        WebElement element = wait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        element.click();
    }
}