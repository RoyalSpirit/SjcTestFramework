package sjc.aft.framework.core.stepdefs;

import io.cucumber.datatable.DataTable;
import io.cucumber.java.ru.Когда;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import sjc.aft.framework.core.FrameworkPage;
import sjc.aft.framework.core.PageContextRegistry;

import static sjc.aft.framework.core.PageContextRegistry.getPageByTitle;
import static sjc.aft.framework.pages.AbstractPage.ANSI_PURPLE;
import static sjc.aft.framework.pages.AbstractPage.ANSI_RESET;

public class BasicSteps {

    public static final Logger logger = LoggerFactory.getLogger(BasicSteps.class);

    @Когда("^(?:user is on page|открывается страница) \"([^\"]*)\"$")
    public static void openPage(String pageTitle) {
        FrameworkPage page = getPageByTitle(pageTitle);
        PageContextRegistry.setCurrentPage(page);
        page.assertIsOpen();
        logger.info("User is located on the page: " + ANSI_PURPLE + pageTitle + ANSI_RESET);
    }

    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\"$")
    public static void userMakesActionWithOneParameter(String action, String param1) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1);
    }

    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\" [^\"]+ \"([^\"]*)\"[^\"]*$")
    public static void userMakesActionWithTwoParams(String action, String param1, String param2) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1, param2);
    }

    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\" (?:with data|содержит данные):$")
    public static void userMakesActionWithOneParameterAndDataTable(String action, String param1, DataTable table) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1, table);
    }


}

