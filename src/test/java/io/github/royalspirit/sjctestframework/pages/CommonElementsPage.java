package io.github.royalspirit.sjctestframework.pages;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

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
