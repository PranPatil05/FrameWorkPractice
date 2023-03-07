package utils.listeners;

import io.qameta.allure.Attachment;
import io.qameta.allure.Allure;
import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import org.testng.IInvokedMethod;
import org.testng.IInvokedMethodListener;
import org.testng.ITestListener;
import org.testng.ITestResult;
import org.testng.reporters.Files;
import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.shooting.ShootingStrategies;
import utils.BrowserSetup;
import utils.TestBase;

import javax.imageio.ImageIO;
import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.File;
import java.io.IOException;



public class TestListeners implements IInvokedMethodListener, ITestListener {
    private static Logger logger = LogManager.getLogger(TestListeners.class);

    public void beforeInvocation(IInvokedMethod method, ITestResult testResult) {
        if(TestBase.classLogs!=null){
            TestBase.classLogs.delete(0,TestBase.classLogs.length());
        }
        logger.info("Method Started $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
    }

    public void afterInvocation(IInvokedMethod method, ITestResult testResult) {
        logger.info("Execution Finished For $$ " + method.getTestMethod().getMethodName() + " of Class : " + testResult.getTestClass());
        if(TestBase.classLogs != null){
            attachLogs(method.getTestMethod().getMethodName(),TestBase.classLogs);
        }
        if(testResult.getThrowable() != null){
            logger.info(testResult.getThrowable().getMessage());
            attachScreenshot(method.getTestMethod().getMethodName(),BrowserSetup.getDriver());
        }
    }

    @Override
    public void onTestSuccess(ITestResult testResult){
        logger.info("Case Pass $$ " + testResult.getMethod().getMethodName());
        attachScreenshot(testResult.getMethod().getMethodName(),BrowserSetup.getDriver());
    }

    @Override
    public void onTestFailure(ITestResult testResult){
        logger.info("Case Failed $$ " + testResult.getMethod().getMethodName());
    }

    @Override
    public void onTestSkipped (ITestResult testResult){
        logger.info("Case Skipped $$ " + testResult.getMethod().getMethodName());
    }

    @Attachment(value = "Logs", type = "text/plain")
    public static String attachLogs(String logs) {
        return logs;
    }

    @Attachment(value = "Logs", type = "text/plain")
    public void attachLogs(String name,StringBuilder logs){
        logger.info("Attaching Logs for " + name);
        try{
            Allure.addAttachment("Logs_"+name, logs.toString()); // Files.readFile(new File("src/test/output/logs/Automation.log"))
        }
        catch (Exception e){
            logger.info("Unable to attach logs. " + e.getMessage());
        }
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public byte[] attachScreenshot(WebDriver driver) {
        logger.info("Attaching screenshot of URL $$ " + driver.getCurrentUrl());
        return ((TakesScreenshot) driver).getScreenshotAs(OutputType.BYTES);
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static void attachScreenshot(String name,WebDriver driver) {
        logger.info("Attaching screenshot of URL $$ " + driver.getCurrentUrl());
        Allure.addAttachment("Screenshot_"+name, new ByteArrayInputStream(((TakesScreenshot)driver).getScreenshotAs(OutputType.BYTES)));
    }

    @Attachment(value = "Screenshot", type = "image/png")
    public static void attachScreenshotFullPage(String name,WebDriver driver){
        logger.info("Attaching Full Page Screenshot of URL $$ " + driver.getCurrentUrl());
        try{
            Screenshot screenshot=new AShot().shootingStrategy(ShootingStrategies.viewportPasting(1000)).takeScreenshot(driver);
            ByteArrayOutputStream bos = new ByteArrayOutputStream();
            ImageIO.write(screenshot.getImage(), "jpg", bos );
            byte [] data = bos.toByteArray();
            Allure.addAttachment("Screenshot_"+name, new ByteArrayInputStream(data));
        }
        catch (IOException ioe){
            logger.info("Error is attaching fullpage screenshot. Hence attaching normal screenshot");
            attachScreenshot(name, driver);
        }

    }
}
