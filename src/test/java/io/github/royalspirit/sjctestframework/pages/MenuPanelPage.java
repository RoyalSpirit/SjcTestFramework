package io.github.royalspirit.sjctestframework.pages;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Menu Panel")
public class MenuPanelPage extends AbstractPage {

    @ElementTitle(value = "Menu Panel")
    private final By menuPanel = By.xpath("//div[@class='bm-menu']");

    @ElementTitle(value = "All Items")
    private final By allItemsButton = By.xpath("//a[text()='All Items']");

    @ElementTitle(value = "About")
    private final By aboutButton = By.xpath("//a[text()='About']");

    @ElementTitle(value = "Logout")
    private final By logoutButton = By.xpath("//a[text()='Logout']");

    @ElementTitle(value = "Reset App State")
    private final By resetAppStateButton = By.xpath("//a[text()='Reset App State']");

    @ElementTitle(value = "Close Menu")
    private final By closeMenuButton = By.xpath("//img[@data-test='close-menu']/preceding-sibling::button");

    @Override
    public void assertIsOpen() {
        $(menuPanel).shouldBe(visible);
    }
}
