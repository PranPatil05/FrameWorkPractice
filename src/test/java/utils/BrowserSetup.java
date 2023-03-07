package utils;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.Platform;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.chrome.ChromeOptions;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.remote.DesiredCapabilities;
import utils.fileHandlers.ExcelUtils;

public class BrowserSetup {
    public static ThreadLocal<WebDriver> threadDriver = new ThreadLocal<>();
    public static WebDriver driver;
    private Platform platform;

    public static synchronized WebDriver getDriver() {
        return threadDriver.get();
    }

    public static WebDriver launchChromeBrowser() {
        TestBase.log("Launching Chrome Browser");
        ChromeOptions options = new ChromeOptions();
        options.addArguments("--incognito");

        WebDriverManager.chromedriver().setup();
        driver = new ChromeDriver(options);
        return driver;
    }

    public static WebDriver launchFirefoxBrowser() {
        TestBase.log("Launching Firefox Browser");
        DesiredCapabilities caps = new DesiredCapabilities();
        FirefoxOptions options = new FirefoxOptions();
        options.addArguments("-private");
        caps.setCapability("moz:firefoxOptions", options);

        WebDriverManager.firefoxdriver().setup();
        driver = new FirefoxDriver(caps);
        return driver;
    }

    public WebDriver invokeWebDriver() {
        try {
            switch (ExcelUtils.readPropertyFromExcel("Configuration", "Browser").toUpperCase()) {
                case "CHROME": launchChromeBrowser();
                    break;

                case "FIREFOX": launchFirefoxBrowser();
                    break;

                default: TestBase.log("Launching Default Browser");
                    launchChromeBrowser();
            }

            threadDriver.set(driver);
            return getDriver();
        } catch (Exception e) {
            TestBase.log("Exception occurred.\n" + e.getStackTrace());
            return getDriver();
        }
    }

    //this can be used to invoke platform specific browser
    private Platform getCurrentPlatform() {
        String currentPlatform = System.getProperty("os.name").toLowerCase();
        if (currentPlatform.contains("win")) {
            this.platform = Platform.WINDOWS;
        } else if (currentPlatform.contains("nix") || currentPlatform.contains("nux") || currentPlatform.contains("ais")) {
            this.platform = Platform.LINUX;
        } else if (currentPlatform.contains("mac")) {
            this.platform = Platform.MAC;
        }
        return platform;
    }
}
