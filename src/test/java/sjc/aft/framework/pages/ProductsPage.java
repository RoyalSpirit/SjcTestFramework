package sjc.aft.framework.pages;

import org.openqa.selenium.By;
import sjc.aft.framework.core.annotations.ElementTitle;
import sjc.aft.framework.core.annotations.PageTitle;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Products")
public class ProductsPage extends CommonElementsPage {

    @ElementTitle(value = "Products Page Title")
    private final By productsPageTitle = By.xpath("//span[@class='title' and text()='Products']");

    @ElementTitle(value = "Products list")
    private final By productsList = By.xpath("//div[contains(@class,'inventory_item_name')]");

    @Override
    public void assertIsOpen() {
        $(productsPageTitle).shouldBe(visible);
    }
}
