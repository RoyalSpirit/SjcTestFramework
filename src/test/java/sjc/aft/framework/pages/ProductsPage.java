package sjc.aft.framework.pages;

import org.openqa.selenium.By;
import sjc.aft.framework.core.annotations.ElementTitle;
import sjc.aft.framework.core.annotations.PageTitle;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Products")
public class ProductsPage extends LoginPage {

    @ElementTitle(value = "Products Page Title")
    private final By productsPageTitle = By.xpath("//span[@class='title' and text()='Products']");

//    @ElementTitle(value = "Username")
//    private final By usernameInput = By.xpath("//input[@id='user-name']");
//
//    @ElementTitle(value = "Password")
//    private final By passwordInput = By.xpath("//input[@id='password']");
//
//    @ElementTitle(value = "Login")
//    private final By loginButton = By.xpath("//input[@id='login-button']");

    @Override
    public void assertIsOpen() {
        $(productsPageTitle).shouldBe(visible);
    }
}
