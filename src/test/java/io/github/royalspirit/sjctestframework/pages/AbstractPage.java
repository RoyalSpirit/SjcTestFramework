package io.github.royalspirit.sjctestframework.pages;

import com.codeborne.selenide.CollectionCondition;
import com.codeborne.selenide.ElementsCollection;
import com.codeborne.selenide.SelenideElement;
import io.cucumber.datatable.DataTable;
import io.github.royalspirit.sjctestframework.core.FrameworkPage;
import io.github.royalspirit.sjctestframework.core.annotations.ActionTitle;
import io.github.royalspirit.sjctestframework.core.annotations.ActionsTitle;
import io.github.royalspirit.sjctestframework.core.annotations.ElementTitle;
import io.github.royalspirit.sjctestframework.core.annotations.PageTitle;
import org.junit.jupiter.api.Assertions;
import org.openqa.selenium.Keys;
import org.openqa.selenium.Platform;

import java.time.Duration;
import java.util.List;

import static com.codeborne.selenide.Condition.visible;
import static com.codeborne.selenide.Selenide.$;
import static com.codeborne.selenide.Selenide.$$;
import static io.github.royalspirit.sjctestframework.core.ElementsObjectRegistry.getElementByTitle;
import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.*;

@PageTitle(title = "Abstract class with common elements and methods / Абстрактный класс с общими методами и элементами")
public abstract class AbstractPage extends FrameworkPage {

    /**
     * Fills a visible field resolved by its element title.
     * @param elementTitle element title declared in {@link ElementTitle}
     * @param expectedValue value to set into the field
     */
    @ActionsTitle({
            @ActionTitle(value = "fills field"),
            @ActionTitle(value = "заполняет поле")})
    public void fillField(String elementTitle, String expectedValue) {
        $(getElementByTitle(elementTitle)).shouldBe(visible).setValue(expectedValue);
        logger.info("Filled the: '" + blue(elementTitle) + "' field with the value: '" + green(expectedValue) + "'");
    }

    /**
     * Clears a field resolved by its element title.
     * @param elementTitle element title declared in {@link ElementTitle}
     */
    @ActionsTitle({
            @ActionTitle(value = "clears field"),
            @ActionTitle(value = "очищает поле")})
    public void clearField(String elementTitle) {
        Keys cmdCtrl = Platform.getCurrent().is(Platform.MAC) ? Keys.COMMAND : Keys.CONTROL;
        SelenideElement element = $(getElementByTitle(elementTitle));
        element.sendKeys(cmdCtrl, "a");
        element.sendKeys(Keys.DELETE);
        logger.info("Cleared field with title: '" + blue(elementTitle) + "'.");
    }

    /**
     * Clicks a visible element resolved by its element title.
     * @param elementTitle element title declared in {@link ElementTitle}
     */
    @ActionsTitle({
            @ActionTitle(value = "press button"),
            @ActionTitle(value = "click on element"),
            @ActionTitle(value = "нажимает кнопку")})
    public void clickButton(String elementTitle) {
        $(getElementByTitle(elementTitle)).shouldBe(visible).click();
        logger.info("Pressed the button: " + red(elementTitle));
    }

    /**
     * Selects an element from an element list by exact visible text.
     * @param elementsListTitle element collection title declared in {@link ElementTitle}
     * @param elementName exact text of the element to select
     */
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
                logger.info("Element with name: '" + yellow(elementName) +
                        "' has been successfully selected from list: '" + blue(elementsListTitle) + "'.");
                return;
            }
        }
        Assertions.fail("Element with name '" + elementName + "' not found.");
    }

    /**
     * Verifies that an element list contains exactly the expected values in the same order.
     * @param elementsListTitle element collection title declared in {@link ElementTitle}
     * @param tableWithElements expected element values presented as a DataTable
     */
    @ActionsTitle({
            @ActionTitle(value = "checks list of elements"),
            @ActionTitle(value = "проверяет, что список")})
    public void validateListOfElementsContainsExpectedValues(String elementsListTitle, DataTable tableWithElements) {
        List<String> expectedElements = tableWithElements.asList(String.class);
        ElementsCollection elementsFromXpath = $$(getElementByTitle(elementsListTitle));
        elementsFromXpath.shouldHave(CollectionCondition.sizeGreaterThan(0));
        Assertions.assertEquals(expectedElements.size(), elementsFromXpath.size(),
                "List of expected elements and list of elements from xpath must be the same size");

        for (int i = 0; i < elementsFromXpath.size(); i++) {
            Assertions.assertEquals(expectedElements.get(i), elementsFromXpath.get(i).getText(),
                    "Expected text: '" + expectedElements.get(i) + "', but received: '" + elementsFromXpath.get(i).getText() + "'");
            logger.info("Checked. Expected: '" + yellow(expectedElements.get(i)) +
                    "', received from xpath: '" + blue(elementsFromXpath.get(i).getText()) + "'");
        }
    }

    /**
     * Verifies that an element text equals the expected value.
     * @param elementTitle element title declared in {@link ElementTitle}
     * @param expectedValue expected text value
     */
    @ActionsTitle({
            @ActionTitle(value = "checks field or element equals expected value"),
            @ActionTitle(value = "проверяет поле или элемент на ожидаемое значение")})
    public void validateElementOrFieldEqualsExpectedValue(String elementTitle, String expectedValue) {
        String textFromElement = $(getElementByTitle(elementTitle)).getText();
        Assertions.assertEquals(textFromElement, expectedValue,
                "Expected value: '" + expectedValue + "', but received: '" + textFromElement + "'");
        logger.info("Checked. Expected: '" + yellow(expectedValue) +
                "', received from element or field: '" + blue(textFromElement) + "'");
    }

    /**
     * Pauses scenario execution for the specified number of seconds.
     * @param seconds number of seconds to wait
     */
    @ActionsTitle({
            @ActionTitle(value = "awaits"),
            @ActionTitle(value = "ожидает")})
    public void waitForSomeSeconds(String seconds) {
        try {
            Thread.sleep(Duration.ofSeconds(Long.parseLong(seconds)));
        } catch (NumberFormatException e) {
            throw new IllegalArgumentException("Seconds value must be a number: " + seconds, e);
        } catch (InterruptedException e) {
            Thread.currentThread().interrupt();
            throw new RuntimeException("Waiting was interrupted.", e);
        }
    }

}
