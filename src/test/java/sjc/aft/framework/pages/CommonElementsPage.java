package sjc.aft.framework.pages;

import org.openqa.selenium.By;
import sjc.aft.framework.core.annotations.ElementTitle;
import sjc.aft.framework.core.annotations.PageTitle;

@PageTitle(title = "Common Elements For All Pages (like menu and etc)")
public class CommonElementsPage extends AbstractPage {

    @ElementTitle(value = "Menu")
    protected final By openMenuButton = By.xpath("//button[text()='Open Menu']");

    @ElementTitle(value = "Cart")
    protected final By cartButton = By.xpath("//div[contains(@class,'shopping_cart')]");

    @Override
    public void assertIsOpen() {
    }
}
