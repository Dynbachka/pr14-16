package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class LoginPage extends BasePage {
    private final By usernameField = By.id("user-name");
    private final By passwordField = By.id("password");
    private final By loginButton = By.id("login-button");
    private final By errorMessage = By.cssSelector("[data-test='error']");

    public LoginPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Открыть страницу входа")
    public void open() {
        driver.get("https://www.saucedemo.com/");
    }

    @Step("Выполнить вход с логином: {username} и паролем: {password}")
    public void login(String username, String password) {
        type(usernameField, username);
        type(passwordField, password);
        click(loginButton);
    }

    @Step("Проверить успешный вход")
    public boolean isLoggedIn() {
        return driver.getCurrentUrl().contains("/inventory.html");
    }

    @Step("Проверить сообщение об ошибке")
    public String getErrorMessage() {
        return getText(errorMessage);
    }

    @Step("Проверить отображение ошибки")
    public boolean isErrorDisplayed() {
        try {
            return isDisplayed(errorMessage);
        } catch (Exception e) {
            return false;
        }
    }
}
