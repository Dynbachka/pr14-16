package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Saucedemo")
@Feature("Корзина")
@Story("Работа с корзиной")
public class CartTests extends BaseTest {

    @Test(description = "Позитив: Удаление товара из корзины")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Иванов И.И.")
    public void removeProductFromCartTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        productsPage.addToCart();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver, wait);
        cartPage.removeProduct();
        Assert.assertTrue(cartPage.isCartEmpty(), "Корзина должна быть пустой");
    }

    @Test(description = "Позитив: Переход к оформлению из корзины")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Иванов И.И.")
    public void checkoutFromCartTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        productsPage.addToCart();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver, wait);
        cartPage.clickCheckout();

        Assert.assertTrue(driver.getCurrentUrl().contains("checkout-step-one.html"),
                "Ожидался переход на страницу Checkout");
    }
}
