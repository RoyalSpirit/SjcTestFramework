package sjc.aft.framework.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;
import static sjc.aft.framework.pages.AbstractPage.ANSI_CYAN;
import static sjc.aft.framework.pages.AbstractPage.ANSI_RESET;


public class Setup {

    public static final Logger logger = LoggerFactory.getLogger(Setup.class);

    @BeforeAll
    public static void registerAllPages() throws Exception {
        PageContextRegistry.autoRegisterPages("sjc.aft.framework.pages");
    }

    // Enables Selenide logger
//    @Before
//    public void setupAllureReports() {
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
//    }

    @Before
    public static void sepUp() throws Exception {
        Configuration.browser = GetPropertyValues.getProperty("browser.name");
        Configuration.browserSize = GetPropertyValues.getProperty("browser.size");
        if (Objects.equals(GetPropertyValues.getProperty("browser.name"), "chrome")) {
            // Custom path to Chromedriver
//        System.setProperty("webdriver.chrome.driver", Objects.requireNonNull(GetPropertyValues.getProperty("path.to.webdriver")));
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--no-sandbox");
            Configuration.browserCapabilities = chromeOptions;
        } else if (Objects.equals(GetPropertyValues.getProperty("browser.name"), "firefox")) {
            // Custom path to Firefoxdriver
//        System.setProperty("webdriver.gecko.driver", Objects.requireNonNull(GetPropertyValues.getProperty("path.to.webdriver")));
        } else throw new Exception("Webdriver not found or not set in configuration");
    }

    @Before
    public void scenarioNameLogging(Scenario scenario) {
        logger.info("Running scenario with name: " + ANSI_CYAN + scenario.getName() + ANSI_RESET);
    }

    @Before
    public void opensStartingUrl() throws Exception {
        String startingUrl = GetPropertyValues.getProperty("starting.url");
        open(startingUrl);
        logger.info("Opening URL: " + startingUrl);
    }

    @After
    public void takeScreenshotIfTestFails(Scenario scenario) throws IOException {
        if (scenario.isFailed()) {
            byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
            try (InputStream inputStream = new ByteArrayInputStream(screenshot)) {
                Allure.attachment("Failed step screenshot", inputStream);
            }
        }
    }

    @AfterAll
    public static void windowAndDriverClose() {
        closeWindow();
        closeWebDriver();
    }

}
