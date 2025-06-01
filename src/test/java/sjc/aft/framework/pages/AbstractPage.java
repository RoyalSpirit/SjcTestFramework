package sjc.aft.framework.pages;

import sjc.aft.framework.core.FrameworkPage;
import sjc.aft.framework.core.annotations.ActionTitle;
import sjc.aft.framework.core.annotations.ActionsTitle;
import sjc.aft.framework.core.annotations.PageTitle;

import java.time.Duration;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static sjc.aft.framework.core.ElementsObjectRegistry.getElementByTitle;

@PageTitle(title = "Abstract class with common elements and methods / Абстрактный класс с общими методами и элементами")
public abstract class AbstractPage extends FrameworkPage {

    public final String fgRed = "\u001B[31m";
    public final String fgGreen = "\u001B[32m";
    public final String fgBlue = "\u001B[34m";
    public final String fgYellow = "\u001B[33m";
    public final String fgReset = "\u001B[0m";

    @ActionsTitle({
            @ActionTitle(value = "fills field"),
            @ActionTitle(value = "заполняет поле")})
    public void fillField(String elementTitle, String expectedValue) throws Exception {
        $(getElementByTitle(elementTitle)).shouldBe(visible).setValue(expectedValue);
        logger.info("Filled the: " + fgBlue + elementTitle + fgReset + " field with the value: " + fgGreen + expectedValue + fgReset);
    }

    @ActionsTitle({
            @ActionTitle(value = "press button"),
            @ActionTitle(value = "нажимает кнопку")})
    public void clickButton(String elementTitle) throws Exception {
        $(getElementByTitle(elementTitle)).shouldBe(visible).click();
    }

    @ActionTitle(value = "ожидает")
    public void waitForSomeSeconds(String seconds) throws Exception {
        Thread.sleep(Duration.ofSeconds(Long.parseLong(seconds + 000)));
        }

}
