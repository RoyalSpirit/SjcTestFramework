package sjc.aft.framework.core;

import com.codeborne.selenide.Configuration;
import io.cucumber.java.AfterAll;
import io.cucumber.java.Before;
import io.cucumber.java.BeforeAll;
import org.openqa.selenium.chrome.ChromeOptions;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Objects;

import static com.codeborne.selenide.Selenide.*;


public class Setup {

    public static final Logger logger = LoggerFactory.getLogger(Setup.class);

    @BeforeAll
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
