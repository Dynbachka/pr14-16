package pages;

import io.qameta.allure.Step;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.List;

public class ProductsPage extends BasePage {
    private final By addToCartButtons = By.cssSelector("[data-test='add-to-cart-sauce-labs-backpack']");
    private final By shoppingCartLink = By.className("shopping_cart_link");
    private final By productSort = By.cssSelector("[data-test='product-sort-container']");
    private final By productTitle = By.cssSelector(".inventory_item_name");
    private final By productPrice = By.cssSelector(".inventory_item_price");

    public ProductsPage(WebDriver driver, WebDriverWait wait) {
        super(driver, wait);
    }

    @Step("Добавить товар в корзину")
    public void addToCart() {
        click(addToCartButtons);
    }

    @Step("Перейти в корзину")
    public void goToCart() {
        click(shoppingCartLink);
    }

    @Step("Сортировать товары по цене (low to high)")
    public void sortByPriceLowToHigh() {
        el(productSort).sendKeys("Price (low to high)");
    }

    @Step("Получить список названий товаров")
    public List<String> getProductTitles() {
        return driver.findElements(productTitle).stream()
                .map(WebElement::getText)
                .toList();
    }

    @Step("Проверить, что первый товар самый дешевый")
    public boolean isFirstProductCheapest() {
        List<WebElement> prices = driver.findElements(productPrice);
        double firstPrice = Double.parseDouble(prices.get(0).getText().replace("$", ""));
        for (int i = 1; i < prices.size(); i++) {
            double currentPrice = Double.parseDouble(prices.get(i).getText().replace("$", ""));
            if (firstPrice > currentPrice) {
                return false;
            }
        }
        return true;
    }
}
