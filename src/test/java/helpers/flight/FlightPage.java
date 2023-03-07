package helpers.flight;

import org.bouncycastle.util.test.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;
import utils.GenericHelper;
import utils.TestBase;

import java.util.Locale;

public class FlightPage {

    @FindBy(xpath = "//input[@value='roundtrip']")
    WebElement radioButtonRoundTrip;

    @FindBy(xpath = "//input[@value='oneway']")
    WebElement radioButtonOneWay;

    @FindBy(name = "passCount")
    WebElement passengerCount;

    @FindBy(name = "fromPort")
    WebElement fromPort;

    @FindBy(name = "fromMonth")
    WebElement fromMonth;

    @FindBy(name = "fromDay")
    WebElement fromDay;

    @FindBy(name = "toPort")
    WebElement toPort;

    @FindBy(name = "toMonth")
    WebElement returnMonth;

    @FindBy(name = "toDay")
    WebElement returnDay;

    @FindBy(xpath = "//input[@value='Coach']")
    WebElement radioButtonClassEconomy;

    @FindBy(xpath = "//input[@value='Business']")
    WebElement radioButtonClassBusiness;

    @FindBy(xpath = "//input[@value='First']")
    WebElement radioButtonClassFirst;

    @FindBy(name = "airline")
    WebElement preference;

    @FindBy(name = "findFlights")
    WebElement buttonFindFlight;

    @FindBy(xpath = "//tbody/descendant::b[1]/font[1]")
    public WebElement resultAfterFlightFinder;


    private GenericHelper genericHelper;

    public FlightPage(WebDriver driver){
        PageFactory.initElements(driver,this);
        genericHelper = new GenericHelper(driver);
    }

    public void selectTripType(String type){
        TestBase.log("Selecting Trip Type as : " + type);
        if(type.equalsIgnoreCase("Round Trip")){
            selectRoundTrip();
        }
        else{
            selectOneWay();
        }
    }

    public void selectRoundTrip(){
        TestBase.log("Clicking Radio Button for Round Trip");
        genericHelper.click(radioButtonRoundTrip);
    }

    public void selectOneWay(){
        TestBase.log("Clicking Radio Button for One Way");
        genericHelper.click(radioButtonOneWay);
    }

    public void selectNumberOfPassengers(String num){
        TestBase.log("Selecting Number of passenger to : " + num);
        genericHelper.selectByVisibleText(passengerCount,num);
    }

    public void selectSource(String source){
        TestBase.log("Selecting source as : " + source);
        genericHelper.selectByVisibleText(fromPort,source);
    }

    public void selectStartMonth(String month){
        TestBase.log("Selecting Start Month as : " + month);
        genericHelper.selectByVisibleText(fromMonth,month);
    }

    public void selectStartDay(String day){
        TestBase.log("Selecting Start Day as : " + day);
        genericHelper.selectByVisibleText(fromDay,day);
    }

    public void selectDestination(String destination){
        TestBase.log("Selecting Destination as : " + destination);
        genericHelper.selectByVisibleText(toPort,destination);
    }

    public void selectReturnMonth(String month){
        if(month.length()<=0){
            TestBase.log("Keeping return Month to default as no data provided");
        }else{
            TestBase.log("Selecting Return Month as : " + month);
            genericHelper.selectByVisibleText(returnMonth,month);
        }
    }

    public void selectReturnDay(String day){
        if(day.length()<=0){
            TestBase.log("Keeping return Day to default as no data provided");
        }else{
            TestBase.log("Selecting Return Day as : " + day);
            genericHelper.selectByVisibleText(returnDay,day);
        }
    }

    public void selectClass(String travelClass){
        TestBase.log("Selecting Travel Class : " + travelClass);
        switch (travelClass.toLowerCase()){
            case "economy" :
                clickRadioButtonForEconomyClass();
                break;
            case "business" :
                clickRadioButtonForBusinessClass();
                break;
            case "first" :
                clickRadioButtonForFirstClass();
                break;
            default:
                Assert.fail("No Class Matched");
        }
    }

    public void clickRadioButtonForEconomyClass(){
        TestBase.log("Clicking radio button for Economy Class");
        genericHelper.click(radioButtonClassEconomy);
    }

    public void clickRadioButtonForBusinessClass(){
        TestBase.log("Clicking radio button for Business Class");
        genericHelper.click(radioButtonClassBusiness);
    }

    public void clickRadioButtonForFirstClass(){
        TestBase.log("Clicking radio button for First Class");
        genericHelper.click(radioButtonClassFirst);
    }

    public void selectAirlinePreference(String airlinePreference){
        if(airlinePreference.length()>0){
            TestBase.log("Selecting Airline Preference as : " + airlinePreference);
            genericHelper.selectByVisibleText(preference,airlinePreference);
        }
        else{
            TestBase.log("Setting No Preference");
        }
    }

    public void clickFindFlightButton(){
        TestBase.log("Clicking Find Flight Button");
        genericHelper.click(buttonFindFlight);
    }


}
