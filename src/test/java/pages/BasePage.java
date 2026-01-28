package pages;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;
import io.qameta.allure.Step;
import java.time.Duration;

public abstract class BasePage {
    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver, WebDriverWait wait) {
        this.driver = driver;
        this.wait = wait;
    }

    protected WebElement el(By locator) {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(locator));
    }

    @Step("Кликнуть на элемент")
    protected void click(By locator) {
        wait.until(ExpectedConditions.elementToBeClickable(locator)).click();
    }

    @Step("Ввести текст '{text}' в поле")
    protected void type(By locator, String text) {
        WebElement element = el(locator);
        element.clear();
        element.sendKeys(text);
    }

    @Step("Проверить видимость элемента")
    protected boolean isDisplayed(By locator) {
        return el(locator).isDisplayed();
    }

    @Step("Получить текст элемента")
    protected String getText(By locator) {
        return el(locator).getText();
    }
}
