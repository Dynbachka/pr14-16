package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.LoginPage;

@Epic("Saucedemo")
@Feature("Авторизация")
@Story("Позитивные и негативные сценарии входа")
public class LoginTests extends BaseTest {

    @Test(description = "Позитив: Успешный вход стандартным пользователем")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Иванов И.И.")
    public void positiveLoginTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");
        Assert.assertTrue(loginPage.isLoggedIn(), "Ожидался переход на страницу продуктов");
    }

    @Test(description = "Негатив: Вход с неверным паролем")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Иванов И.И.")
    public void negativeLoginWrongPasswordTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "wrong_password");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Ожидалось сообщение об ошибке");
    }

    @Test(description = "Негатив: Вход заблокированным пользователем")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Иванов И.И.")
    public void lockedOutUserLoginTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("locked_out_user", "secret_sauce");
        Assert.assertTrue(loginPage.isErrorDisplayed(), "Ожидалось сообщение о блокировке");
    }
}
