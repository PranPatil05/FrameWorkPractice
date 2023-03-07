package helpers.login;

import org.openqa.selenium.WebDriver;
import pageObjects.login.LoginPage;

public class LoginHelper {
    LoginPage loginPage;

    public LoginHelper(WebDriver driver){
        loginPage = new LoginPage(driver);
    }

    public void doLogin(String username, String password){
        loginPage.enterUsername(username);
        loginPage.enterPassword(password);
        loginPage.clickSubmitButton();
    }
}
