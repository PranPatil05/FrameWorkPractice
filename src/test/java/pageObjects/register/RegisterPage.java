package pageObjects.register;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.FindBys;
import org.openqa.selenium.support.PageFactory;
import utils.GenericHelper;
import utils.TestBase;

import java.util.List;

public class RegisterPage {

    @FindBy(xpath = "//input[@id='email']")
    public WebElement usernameInput;

    @FindBy(xpath = "//input[@name='password']")
    public WebElement passwordInput;

    @FindBy(xpath = "//input[@name='confirmPassword']")
    public WebElement confirmPassInput;

    @FindBy(xpath = "//input[@name='submit']")
    public WebElement submitButton;

    @FindBy(xpath = "//select")
    WebElement countryDropdown;

    @FindBys(
    @FindBy(xpath = "//option")
    )
    List<WebElement> countryOptions;

    @FindBy(xpath = "//b[contains(text(),'Dear')]/ancestor::p/following-sibling::p[1]/font")
    public WebElement successMessage;

    private GenericHelper genericHelper;

    public RegisterPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        genericHelper = new GenericHelper(driver);
    }

    public void setUsername(String uname){
        TestBase.log("Setting Username as : " + uname);
        genericHelper.setText(usernameInput,uname);
    }

    public void setPassword(String pass){
        TestBase.log("Setting Password as : " + pass);
        genericHelper.setText(passwordInput,pass);
    }

    public void setConfirmPassword(String cpass){
        TestBase.log("Setting Confirm Password as : " + cpass);
        genericHelper.setText(confirmPassInput,cpass);
    }

    public void clickSubmitButton(){
        TestBase.log("Clicking Submit button");
        genericHelper.click(submitButton);
    }

    public String getSuccessMessage(){
        TestBase.log("Getting Success Message");
        return genericHelper.getTextOfElement(successMessage);
    }

    public void openCountryDropdown(){
        TestBase.log("Opening country dropdown");
        genericHelper.click(countryDropdown);
    }

    public List<String> getListOfCountries(){
        TestBase.log("Getting options in countries");
        return genericHelper.getTextOfElementList(countryOptions);
    }

}
