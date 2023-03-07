package tests.login;

import helpers.login.LoginHelper;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.Test;
import org.testng.asserts.SoftAssert;
import pageObjects.login.LoginPage;
import utils.GenericHelper;
import utils.TestBase;
import utils.fileHandlers.yaml.pojo.UserData;
import utils.fileHandlers.yaml.provider.UserProvider;
import utils.listeners.ListenerRetry;

public class LoginTest extends TestBase {
    LoginPage loginPage;
    LoginHelper loginHelper;
    GenericHelper genericHelper;
    UserData userPojo;

    @BeforeClass(alwaysRun = true)
    public void initialize(){
        loginPage = new LoginPage(driver);
        loginHelper = new LoginHelper(driver);
        genericHelper = new GenericHelper(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToLoginPage(){
        TestBase.log("Navigating to Login Page");
        genericHelper.navigateTo(TestBase.baseURL+"/index.php");
    }

    @Test(priority = 1, groups = "P1",retryAnalyzer = ListenerRetry.class)
    public void verifyLoginForPublicUser_LG0001() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        userPojo = UserProvider.getUserData("publicUser");
        String username = userPojo.getCredentials().getUsername();
        String password = userPojo.getCredentials().getPassword();

        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickSubmitButton();

        genericHelper.hardWait(3);

        softAssert.assertTrue(genericHelper.getURL().contains("login_sucess"),"URL Does not contain 'login_sucess'");
        softAssert.assertTrue(genericHelper.isDisplayed(loginPage.loginSuccessMessage),"'Login Success' Message not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 2, groups = "P2",retryAnalyzer = ListenerRetry.class)
    public void verifyLoginForSupportUser_LG0002() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        userPojo = UserProvider.getUserData("supportUser");

        loginPage.enterUsername(userPojo.getCredentials().getUsername());
        loginPage.enterPassword(userPojo.getCredentials().getPassword());
        loginPage.clickSubmitButton();

        genericHelper.hardWait(3);

        softAssert.assertTrue(genericHelper.getURL().contains("login_sucess"),"URL Does not contain 'login_sucess'");
        softAssert.assertTrue(genericHelper.isDisplayed(loginPage.loginSuccessMessage),"'Login Success' Message not displayed");
        softAssert.assertAll();
    }

    @Test(priority = 3, groups = "P3",retryAnalyzer = ListenerRetry.class)
    public void verifyLoginForAdminUser_LG0003() throws InterruptedException {
        SoftAssert softAssert = new SoftAssert();
        userPojo = UserProvider.getUserData("adminUser");

        loginHelper.doLogin(userPojo.getCredentials().getUsername(),userPojo.getCredentials().getPassword());

        genericHelper.hardWait(3);

        softAssert.assertTrue(genericHelper.getURL().contains("login_sucess"),"URL Does not contain 'login_sucess'");
        softAssert.assertTrue(genericHelper.isDisplayed(loginPage.loginSuccessMessage),"'Login Success' Message not displayed");
        softAssert.assertAll();
    }
}
