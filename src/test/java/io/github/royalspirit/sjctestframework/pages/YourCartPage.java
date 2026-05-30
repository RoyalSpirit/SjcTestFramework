package io.github.royalspirit.sjctestframework.pages;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Your Cart")
public class YourCartPage extends CommonElementsPage {

    @ElementTitle(value = "Your Cart Page Title")
    private final By cartPageTitle = By.xpath("//span[@class='title' and text()='Your Cart']");

    @ElementTitle(value = "Products list in cart")
    private final By productsListInCart = By.xpath("//div[contains(@class,'inventory_item_name')]");

    @ElementTitle(value = "Prices list in cart")
    private final By pricesListInCart = By.xpath("//div[contains(@class,'inventory_item_price')]");


    // Buttons
    @ElementTitle(value = "Continue Shopping")
    private final By continueShoppingButton = By.xpath("//button[@id='continue-shopping']");

    @ElementTitle(value = "Checkout")
    private final By checkoutButton = By.xpath("//button[@id='checkout']");

    @Override
    public void assertIsOpen() {
        $(cartPageTitle).shouldBe(visible);
    }
}
