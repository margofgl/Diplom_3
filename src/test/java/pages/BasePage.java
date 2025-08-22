package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;

public class BasePage<T extends BasePage<T>> {
    protected final WebDriver driver;
    protected final WebDriverWait wait;
    protected final WebDriverWait longWait;

    public BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(20));
        this.longWait = new WebDriverWait(driver, Duration.ofSeconds(30));
    }

    @Step("Открыть URL: {url}")
    @SuppressWarnings("unchecked")
    public T open(String url) {
        driver.get(url);
        return (T) this;
    }

    protected WebElement el(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    protected void click(By locator) {
        WebElement element = longWait.until(ExpectedConditions.elementToBeClickable(locator));
        ((JavascriptExecutor) driver).executeScript("arguments[0].scrollIntoView(true);", element);
        try { Thread.sleep(150); } catch (InterruptedException ignored) {}
        element.click();
    }

    protected void type(By locator, String text) {
        WebElement element = el(locator);
        element.clear();
        element.sendKeys(text);
    }

    public boolean isVisible(By locator) {
        try {
            return el(locator).isDisplayed();
        } catch (TimeoutException e) {
            return false;
        }
    }

    public boolean isVisibleByButtonText(String text) {
        try {
            By locator = By.xpath("//button[text()='" + text + "']");
            return isVisible(locator);
        } catch (Exception e) {
            return false;
        }
    }

    public boolean isVisibleByText(String text) {
        try {
            By locator = By.xpath("//*[text()='" + text + "']");
            return isVisible(locator);
        } catch (Exception e) {
            return false;
        }
    }

    public String getText(By locator) {
        return el(locator).getText();
    }
}