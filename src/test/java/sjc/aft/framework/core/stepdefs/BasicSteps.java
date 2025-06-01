package sjc.aft.framework.core.stepdefs;

import io.cucumber.java.ru.Когда;
import sjc.aft.framework.core.FrameworkPage;
import sjc.aft.framework.core.PageContextRegistry;


import static sjc.aft.framework.core.PageContextRegistry.getCurrentPage;
import static sjc.aft.framework.core.PageContextRegistry.getPageByTitle;

public class BasicSteps {

    @Когда("^(?:user is on page|открывается страница) \"([^\"]*)\"$")
    public static void openPage(String pageTitle) {
        FrameworkPage page = getPageByTitle(pageTitle);
        PageContextRegistry.setCurrentPage(page);
        page.assertIsOpen();
//        logger.info("User is located on the page: " + "\u001B[33m" + pageTitle + "\u001B[0m");
    }

    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\"$")
    public static void userMakesActionWithOneParameter(String action, String param1) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1);
    }

    @Когда("^(?:user|пользователь) \\((.*?)\\)[^\"]* \"([^\"]*)\" [^\"]+ \"([^\"]*)\"[^\"]*$")
    public static void userMakesActionWithTwoParams(String action, String param1, String param2) {
        PageContextRegistry.getCurrentPage().executeMethodByTitle(action, param1, param2);
    }


}

