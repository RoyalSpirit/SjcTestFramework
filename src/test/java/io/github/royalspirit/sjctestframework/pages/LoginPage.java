package io.github.royalspirit.sjctestframework.pages;

import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.openqa.selenium.By;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;

@PageTitle(title = "Page Swag Labs")
public class LoginPage extends AbstractPage {

    @ElementTitle(value = "Swag Labs Page Title")
    private final By swagLabsPageTitle = By.xpath("//div[@class='login_logo' and text()='Swag Labs']");

    @ElementTitle(value = "Username")
    private final By usernameInput = By.xpath("//input[@id='user-name']");

    @ElementTitle(value = "Password")
    private final By passwordInput = By.xpath("//input[@id='password']");

    @ElementTitle(value = "Login")
    private final By loginButton = By.xpath("//input[@id='login-button']");

    @ElementTitle(value = "Login error message")
    private final By loginErrorMessage = By.xpath("//div[contains(@class,'error-message')]/h3");

    @Override
    public void assertIsOpen() {
        $(swagLabsPageTitle).shouldBe(visible);
    }
}
