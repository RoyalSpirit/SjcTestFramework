package io.github.royalspirit.sjctestframework.core.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import io.github.royalspirit.sjctestframework.core.FrameworkPage;
import io.github.royalspirit.sjctestframework.core.PageContextRegistry;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static io.github.royalspirit.sjctestframework.core.PageContextRegistry.getPageByTitle;
import static io.github.royalspirit.sjctestframework.pages.AbstractPage.ANSI_PURPLE;
import static io.github.royalspirit.sjctestframework.pages.AbstractPage.ANSI_RESET;

public class BasicSteps {

    public static final Logger logger = LoggerFactory.getLogger(BasicSteps.class);

    /**
     * Opens the specified page and asserts it is open.
     * @param pageTitle the title of the page to open
     */
    @Когда("^(?:user is on page|открывается страница) \"([^\"]*)\"$")
    public static void openPage(String pageTitle) {
        FrameworkPage page = getPageByTitle(pageTitle);
        PageContextRegistry.setCurrentPage(page);
        page.assertIsOpen();
        logger.info("User is located on the page: " + ANSI_PURPLE + pageTitle + ANSI_RESET);
    }

    /**
     * Executes an action with one parameter.
     * @param action the action to execute
     * @param param1 the parameter for the action
     */
    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\"$")
    public static void userMakesActionWithOneParameter(String action, String param1) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1);
    }

    /**
     * Executes an action with two parameters.
     * @param action the action to execute
     * @param param1 the first parameter
     * @param param2 the second parameter
     */
    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\" [^\"]+ \"([^\"]*)\"[^\"]*$")
    public static void userMakesActionWithTwoParams(String action, String param1, String param2) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1, param2);
    }

    /**
     * Executes an action with one parameter and a data table.
     * @param action the action to execute
     * @param param1 the parameter for the action
     * @param table the data table
     */
    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\" (?:with data|содержит данные):$")
    public static void userMakesActionWithOneParameterAndDataTable(String action, String param1, DataTable table) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1, table);
    }

}