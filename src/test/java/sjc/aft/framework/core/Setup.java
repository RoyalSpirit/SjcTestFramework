package sjc.aft.framework.core;

import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import static com.codeborne.selenide.Selenide.*;


public class Setup {

    public static final Logger logger = LoggerFactory.getLogger(Setup.class);

    @BeforeAll
    public static void openStartingUrl() throws Exception {
        String startingUrl = GetPropertyValues.getProperty("starting.url");
        open(startingUrl);
        logger.info("Opening URL: " + startingUrl);
    }

    @Before
    public void registerAllPages() throws Exception {
        PageContextRegistry.autoRegisterPages("sjc.aft.framework.pages");
    }

    @AfterAll
    public static void windowAndDriverClose() {
        closeWindow();
        closeWebDriver();
    }

}
