package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

public class CartPage extends BasePage {
    private final By checkoutButton = By.id("checkout");
    private final By itemName = By.cssSelector(".inventory_item_name");
    private final By removeButton = By.id("remove-sauce-labs-backpack");

    public CartPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Нажать кнопку Checkout")
    public void clickCheckout() {
        click(checkoutButton);
    }

    @Step("Проверить наличие товара в корзине")
    public boolean isProductInCart() {
        return isDisplayed(itemName);
    }

    @Step("Удалить товар из корзины")
    public void removeProduct() {
        click(removeButton);
    }

    @Step("Проверить, что корзина пуста")
    public boolean isCartEmpty() {
        try {
            return !driver.findElements(itemName).stream().findFirst().isPresent();
        } catch (Exception e) {
            return true;
        }
    }
}
