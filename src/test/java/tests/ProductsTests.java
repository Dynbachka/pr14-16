package tests;

import io.qameta.allure.*;
import org.testng.Assert;
import org.testng.annotations.Test;
import pages.CartPage;
import pages.LoginPage;
import pages.ProductsPage;

@Epic("Saucedemo")
@Feature("Продукты")
@Story("Работа с товарами и сортировкой")
public class ProductsTests extends BaseTest {

    @Test(description = "Позитив: Добавление товара в корзину")
    @Severity(SeverityLevel.CRITICAL)
    @Owner("Иванов И.И.")
    public void addProductToCartTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        productsPage.addToCart();
        productsPage.goToCart();

        CartPage cartPage = new CartPage(driver, wait);
        Assert.assertTrue(cartPage.isProductInCart(), "Товар должен быть в корзине");
    }

    @Test(description = "Позитив: Сортировка товаров по цене")
    @Severity(SeverityLevel.NORMAL)
    @Owner("Иванов И.И.")
    public void sortProductsByPriceTest() {
        LoginPage loginPage = new LoginPage(driver, wait);
        loginPage.open();
        loginPage.login("standard_user", "secret_sauce");

        ProductsPage productsPage = new ProductsPage(driver, wait);
        productsPage.sortByPriceLowToHigh();
        Assert.assertTrue(productsPage.isFirstProductCheapest(), "Первый товар должен быть самым дешевым");
    }
}
