package io.github.royalspirit.sjctestframework.pages;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Detailed Product Information")
public class DetailedProductInformationPage extends CommonElementsPage {

    @ElementTitle(value = "Detailed Product Information Page Title")
    private final By detailedProductInformationPageTitle = By.xpath("//div[@class='inventory_details_desc_container']");

    @ElementTitle(value = "Back to products")
    private final By backToProductsButton = By.xpath("//button[@id='back-to-products']");

    @ElementTitle(value = "Product title")
    private final By productTitle = By.xpath("//div[@data-test='inventory-item-name']");

    @ElementTitle(value = "Product description")
    private final By productDescription = By.xpath("//div[@data-test='inventory-item-desc']");

    @ElementTitle(value = "Product price")
    private final By productPrice = By.xpath("//div[@data-test='inventory-item-price']");

    @ElementTitle(value = "Add to cart")
    private final By addToCartButton = By.xpath("//button[@id='add-to-cart']");

    @ElementTitle(value = "Remove")
    private final By removeFromCartButton = By.xpath("//button[@id='remove']");

    @Override
    public void assertIsOpen() {
        $(detailedProductInformationPageTitle).shouldBe(visible);
    }
}
