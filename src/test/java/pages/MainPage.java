package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class MainPage extends BasePage<MainPage> {

    private final By bunsTab      = By.xpath("//span[text()='Булки']/..");
    private final By saucesTab    = By.xpath("//span[text()='Соусы']/..");
    private final By fillingsTab  = By.xpath("//span[text()='Начинки']/..");
    private final By loginMainBtn = By.xpath("//button[text()='Войти в аккаунт']");
    private final By accountBtn   = By.xpath("//a[@href='/account']");

    public MainPage(WebDriver driver) {
        super(driver);
    }

    @Step("Открыть главную страницу")
    public MainPage open() {
        driver.get("https://stellarburgers.nomoreparties.site/");
        return this;
    }

    @Step("Клик по вкладке 'Булки'")
    public void openBuns() {
        clickTab(bunsTab);
    }

    @Step("Клик по вкладке 'Соусы'")
    public void openSauces() {
        clickTab(saucesTab);
    }

    @Step("Клик по вкладке 'Начинки'")
    public void openFillings() {
        clickTab(fillingsTab);
    }

    private void clickTab(By tabLocator) {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement tab = wait.until(ExpectedConditions.elementToBeClickable(tabLocator));
        new Actions(driver).moveToElement(tab).perform(); // Прокрутка
        tab.click();
    }

    @Step("Проверить активную вкладку: {expected}")
    public boolean isActiveTab(String expected) {
        By tabLocator = By.xpath("//span[text()='" + expected + "']/..");
        try {
            WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
            WebElement tab = wait.until(ExpectedConditions.visibilityOfElementLocated(tabLocator));
            String selected = tab.getAttribute("aria-selected");
            return selected != null && selected.equals("true");
        } catch (Exception e) {
            System.out.println("Вкладка '" + expected + "' не найдена или не активна.");
            return false;
        }
    }

    @Step("Клик по кнопке 'Войти в аккаунт' на главной")
    public LoginPage clickLoginMain() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(loginMainBtn));
        btn.click();
        return new LoginPage(driver);
    }

    @Step("Перейти в Личный Кабинет")
    public void goToAccount() {
        WebDriverWait wait = new WebDriverWait(driver, Duration.ofSeconds(15));
        WebElement btn = wait.until(ExpectedConditions.elementToBeClickable(accountBtn));
        btn.click();
    }
}