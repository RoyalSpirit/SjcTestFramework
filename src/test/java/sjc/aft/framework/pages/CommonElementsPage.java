package sjc.aft.framework.pages;

import org.openqa.selenium.By;
import sjc.aft.framework.core.annotations.ElementTitle;
import sjc.aft.framework.core.annotations.PageTitle;

@PageTitle(title = "Common Elements For All Pages (like menu and etc)")
public class CommonElementsPage extends AbstractPage {

    @ElementTitle(value = "Menu")
    private final By openMenuButton = By.xpath("//button[text()='Open Menu']");

    @ElementTitle(value = "All Items")
    private final By allItemsButton = By.xpath("//a[text()='All Items']");

    @ElementTitle(value = "About")
    private final By aboutButton = By.xpath("//a[text()='About']");

    @ElementTitle(value = "Logout")
    private final By logoutButton = By.xpath("//a[text()='Logout']");

    @ElementTitle(value = "Reset App State")
    private final By resetAppStateButton = By.xpath("//a[text()='Reset App State']");

    @Override
    public void assertIsOpen() {
    }
}
