package io.github.royalspirit.sjctestframework.core;

import com.codeborne.selenide.Configuration;
import com.codeborne.selenide.Selenide;
import io.cucumber.java.*;
import io.qameta.allure.Allure;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.io.ByteArrayInputStream;
import java.util.Objects;

import static com.codeborne.selenide.Selenide.closeWebDriver;
import static com.codeborne.selenide.Selenide.open;
import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.blue;
import static io.github.royalspirit.sjctestframework.core.logging.LogFormatter.cyan;


public class Setup {

    public static final Logger logger = LoggerFactory.getLogger(Setup.class);

    /**
     * Registers all pages in the framework.
     */
    @BeforeAll
    public static void registerAllPages() {
        PageContextRegistry.autoRegisterPages("io.github.royalspirit.sjctestframework.pages");
    }

    // Enables Selenide logger
//    @Before
//    public void setupAllureReports() {
//        SelenideLogger.addListener("AllureSelenide", new AllureSelenide());
//    }

    @Before(order = 1)
    public void scenarioNameLogging(Scenario scenario) {
        logger.info("Running scenario with name: " + cyan(scenario.getName()));
    }

    /**
     * Sets up browser configuration and capabilities before tests.
     */
    @Before(value = "@ui", order = 2)
    public static void setUpUi() {
        String browserName = GetPropertyValues.getRequiredProperty("browser.name");
        String browserSize = GetPropertyValues.getRequiredProperty("browser.size");
        String startingUrl = GetPropertyValues.getRequiredProperty("starting.url");
        String browserVersion = GetPropertyValues.getOptionalProperty("browser.version");
        String webdriverPath = GetPropertyValues.getOptionalProperty("path.to.webdriver");
        boolean headless = GetPropertyValues.getBooleanProperty("browser.headless", false);

        Configuration.browser = browserName;
        Configuration.browserSize = browserSize;
        Configuration.headless = headless;

        if (browserVersion != null) {
            Configuration.browserVersion = browserVersion;
        }

        if (webdriverPath != null) {
            if (Objects.equals(browserName, "chrome")) {
                System.setProperty("webdriver.chrome.driver", webdriverPath);
            } else if (Objects.equals(browserName, "firefox")) {
                System.setProperty("webdriver.gecko.driver", webdriverPath);
            }
        }

        if (Objects.equals(browserName, "chrome")) {
            ChromeOptions chromeOptions = new ChromeOptions();
            chromeOptions.addArguments("--disable-dev-shm-usage");

            if (headless) {
                chromeOptions.addArguments("--no-sandbox");
            }

            Configuration.browserCapabilities = chromeOptions;
        } else if (!Objects.equals(browserName, "firefox")) {
            throw new IllegalArgumentException("Unsupported browser: " + browserName);
        }

        open(startingUrl);
        logger.info("Opening URL: " + startingUrl);
    }

    @Before(value = "@api", order = 2)
    public static void setUpApi() {
        String baseUrl = GetPropertyValues.getRequiredProperty("api.base.url");
        logger.info("Base URL for API requests: '" + blue(baseUrl) + "'.");
    }

    @After(value = "@ui")
    public void takeScreenshotIfTestFails(Scenario scenario) {
        if (scenario.isFailed()) {
            byte[] screenshot = Selenide.screenshot(OutputType.BYTES);
            Allure.attachment("Failed step screenshot", new ByteArrayInputStream(screenshot));
        }
    }

    @AfterAll
    public static void windowAndDriverClose() {
        closeWebDriver();
    }

}
