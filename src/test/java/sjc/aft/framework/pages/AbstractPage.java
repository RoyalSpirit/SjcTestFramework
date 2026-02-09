package sjc.aft.framework.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.datatable.DataTable;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;
import sjc.aft.framework.core.FrameworkPage;
import sjc.aft.framework.core.annotations.ActionTitle;
import sjc.aft.framework.core.annotations.ActionsTitle;
import sjc.aft.framework.core.annotations.PageTitle;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static sjc.aft.framework.core.ElementsObjectRegistry.getElementByTitle;

@PageTitle(title = "Abstract class with common elements and methods / Абстрактный класс с общими методами и элементами")
public abstract class AbstractPage extends FrameworkPage {

    public static final String ANSI_RED = "\u001B[31m";
    public static final String ANSI_GREEN = "\u001B[32m";
    public static final String ANSI_BLUE = "\u001B[34m";
    public static final String ANSI_YELLOW = "\u001B[33m";
    public static final String ANSI_PURPLE = "\u001B[35m";
    public static final String ANSI_CYAN = "\u001B[36m";
    public static final String ANSI_RESET = "\u001B[0m";

    /**
     * Fills the specified field with the given value.
     * @param elementTitle the title of the field to fill
     * @param expectedValue the value to set
     * @throws Exception if the element cannot be found or interacted with
     */
    @ActionsTitle({
            @ActionTitle(value = "fills field"),
            @ActionTitle(value = "заполняет поле")})
    public void fillField(String elementTitle, String expectedValue) throws Exception {
        $(getElementByTitle(elementTitle)).shouldBe(visible).setValue(expectedValue);
        logger.info("Filled the: " + ANSI_BLUE + elementTitle + ANSI_RESET + " field with the value: " + ANSI_GREEN + expectedValue + ANSI_RESET);
    }

    /**
     * Clears the specified field.
     * @param elementTitle the title of the field to clear
     * @throws Exception if the element cannot be found or interacted with
     */
    @ActionsTitle({
            @ActionTitle(value = "clears field"),
            @ActionTitle(value = "очищает поле")})
    public void clearField(String elementTitle) throws Exception {
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        SelenideElement element = $(getElementByTitle(elementTitle));
        element.sendKeys(cmdCtrl, "a");
        element.sendKeys(Keys.DELETE);
        logger.info("Cleared field with title '" + ANSI_BLUE + elementTitle + ANSI_RESET + "'.");
    }

    @ActionsTitle({
            @ActionTitle(value = "press button"),
            @ActionTitle(value = "click on element"),
            @ActionTitle(value = "нажимает кнопку")})
    public void clickButton(String elementTitle) throws Exception {
        $(getElementByTitle(elementTitle)).shouldBe(visible).click();
        logger.info("Pressed the button: " + ANSI_RED + elementTitle + ANSI_RESET);
    }

    @ActionsTitle({
            @ActionTitle(value = "selects element from list"),
            @ActionTitle(value = "выбирает элемент из списка")})
    public void selectElementFromListByName(String elementsListTitle, String elementName) {
        ElementsCollection elementsFromXpath = $$(getElementByTitle(elementsListTitle));
        elementsFromXpath.shouldHave(CollectionCondition.sizeGreaterThan(0));

        for (SelenideElement element : elementsFromXpath) {
            String text = element.getText().trim();
            if (text.equals(elementName)) {
                element.shouldBe(visible).click();
                logger.info("Element with name '" + ANSI_YELLOW + elementName + ANSI_RESET +
                        "' has been successfully selected from list '" + ANSI_BLUE + elementsListTitle + ANSI_RESET + "'.");
                return;
            }
        }
        Assertions.fail("Element with name '" + elementName + "' not found.");
    }

    @ActionsTitle({
            @ActionTitle(value = "checks list of elements"),
            @ActionTitle(value = "проверяет, что список")})
    public void validateListOfElementsContainsExpectedValues(String elementsListTitle, DataTable tableWithElements) throws Exception {
        List<String> expectedElements = tableWithElements.asList(String.class);
        ElementsCollection elementsFromXpath = $$(getElementByTitle(elementsListTitle));
        elementsFromXpath.shouldHave(CollectionCondition.sizeGreaterThan(0));
        Assertions.assertEquals(expectedElements.size(), elementsFromXpath.size(),
                "List of expected elements and list of elements from xpath must be the same size");

        for (int i = 0; i < elementsFromXpath.size(); i++) {
            Assertions.assertEquals(expectedElements.get(i), elementsFromXpath.get(i).getText(),
                    "Expected text '" + expectedElements.get(i) + "', but received '" + elementsFromXpath.get(i).getText() + "'");
            logger.info("Checked. Expected " + ANSI_YELLOW + expectedElements.get(i) + ANSI_RESET +
                    ", received from xpath " + ANSI_BLUE + elementsFromXpath.get(i).getText() + ANSI_RESET);
        }
    }

    @ActionsTitle({
            @ActionTitle(value = "checks field or element equals expected value"),
            @ActionTitle(value = "проверяет поле или элемент на ожидаемое значение")})
    public void validateElementOrFieldEqualsExpectedValue(String elementTitle, String expectedValue) throws Exception {
        String textFromElement = $(getElementByTitle(elementTitle)).getText();
        Assertions.assertEquals(textFromElement, expectedValue,
                "Expected value '" + expectedValue + "', but received '" + textFromElement + "'");
        logger.info("Checked. Expected '" + ANSI_YELLOW + expectedValue + ANSI_RESET +
                "', received from element or field '" + ANSI_BLUE + textFromElement + ANSI_RESET + "'");
    }

    @ActionsTitle({
            @ActionTitle(value = "awaits"),
            @ActionTitle(value = "ожидает")})
    public void waitForSomeSeconds(String seconds) throws Exception {
        Thread.sleep(Duration.ofSeconds(Long.parseLong(seconds)));
        }

}
