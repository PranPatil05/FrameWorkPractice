package pageObjects.login;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import utils.GenericHelper;
import utils.TestBase;

public class LoginPage {

    @FindBy(name = "userName")
    WebElement inputUsername;

    @FindBy(name = "password")
    WebElement inputPassword;

    @FindBy(name = "submit")
    WebElement buttonSubmit;

    @FindBy(xpath = "//h3[contains(text(),'Login Success')]")
    public WebElement loginSuccessMessage;

    private GenericHelper genericHelper;

    public LoginPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        genericHelper = new GenericHelper(driver);
    }

    public void enterUsername(String username){
        TestBase.log("Entering username as : " + username);
        genericHelper.setText(inputUsername,username);
    }

    public void enterPassword(String password){
        TestBase.log("Entering Password as : " + password);
        genericHelper.setText(inputPassword,password);
    }

    public void clickSubmitButton(){
        TestBase.log("Clicking Submit Button");
        genericHelper.click(buttonSubmit);
    }

}
