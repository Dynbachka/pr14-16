package tests;

import io.qameta.allure.Allure;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import org.testng.ITestResult;
import org.testng.annotations.AfterMethod;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Optional;
import org.testng.annotations.Parameters;
import utils.AllureAttachments;

import java.time.Duration;
import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

public abstract class BaseTest {
    protected WebDriver driver;
    protected WebDriverWait wait;

    @Parameters({"browser", "headless"})
    @BeforeMethod
    public void setUp(@Optional("chrome") String browser, @Optional("false") String headless) {
        driver = createDriver(browser, Boolean.parseBoolean(headless));
        driver.manage().window().maximize();
        wait = new WebDriverWait(driver, Duration.ofSeconds(10));

        Allure.addAttachment("Environment", "text/plain",
                "os.name=" + System.getProperty("os.name") + "\n" +
                        "jdk.version=" + System.getProperty("java.version") + "\n" +
                        "selenium=4.22.0\n" +
                        "testng=7.10.2\n" +
                        "browser=" + browser + "\n" +
                        "driver.manager=Selenium Manager", ".properties");
    }

    private WebDriver createDriver(String browser, boolean headless) {
        return switch (browser.toLowerCase()) {
            case "firefox" -> createFirefoxDriver(headless);
            default -> createChromeDriver(headless);  // chrome по умолчанию
        };
    }

    private WebDriver createChromeDriver(boolean headless) {
        ChromeOptions options = new ChromeOptions();

        Map<String, Object> prefs = new HashMap<>();
        prefs.put("credentials_enable_service", false);
        prefs.put("profile.password_manager_enabled", false);
        prefs.put("profile.password_manager_leak_detection", false);
        prefs.put("profile.default_content_setting_values.notifications", 2);

        options.setExperimentalOption("prefs", prefs);

        List<String> args = Arrays.asList(
                "--disable-extensions",
                "--disable-plugins",
                "--disable-notifications",
                "--no-default-browser-check",
                "--no-first-run",
                "--disable-dev-shm-usage",
                "--no-sandbox",
                "--disable-gpu",
                "--password-store=basic",
                "--use-mock-keychain"
        );

        options.addArguments(args);
        if (headless) options.addArguments("--headless=new");

        options.setExperimentalOption("useAutomationExtension", false);
        options.setExperimentalOption("excludeSwitches", List.of("enable-automation"));

        return new ChromeDriver(options);
    }

    private WebDriver createFirefoxDriver(boolean headless) {
        FirefoxOptions options = new FirefoxOptions();

        options.addPreference("profile.password_manager_enabled", false);
        options.addPreference("dom.popup_maximum", 0);
        options.addPreference("browser.shell.checkDefaultBrowser", false);
        options.addPreference("startup.homepage_welcome_url.additional", "");

        if (headless) options.addArguments("--headless");

        return new FirefoxDriver(options);
    }

    @AfterMethod(alwaysRun = true)
    public void tearDown(ITestResult result) {
        if (driver != null && !result.isSuccess()) {
            AllureAttachments.screenshot(driver, "Failure screenshot");
            AllureAttachments.pageSource(driver, "Page source on failure");
        }
        if (driver != null) {
            driver.quit();
        }
    }
}
