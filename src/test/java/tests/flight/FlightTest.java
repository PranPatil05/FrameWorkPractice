package tests.flight;

import org.testng.Assert;
import org.testng.annotations.BeforeClass;
import org.testng.annotations.BeforeMethod;
import org.testng.annotations.DataProvider;
import org.testng.annotations.Test;
import helpers.flight.FlightPage;
import utils.GenericHelper;
import utils.TestBase;
import utils.fileHandlers.ExcelUtils;
import utils.listeners.TestListeners;

import java.io.IOException;
import java.util.List;
import java.util.Map;

public class FlightTest extends TestBase {
    FlightPage flightPage;
    GenericHelper genericHelper;

    @BeforeClass(alwaysRun = true)
    public void initialize(){
        flightPage = new FlightPage(driver);
        genericHelper = new GenericHelper(driver);
    }

    @BeforeMethod(alwaysRun = true)
    public void navigateToFlightPage(){
        TestBase.log("Navigating to Flight Page");
        genericHelper.navigateTo(TestBase.baseURL+"/reservation.php");
    }

    @DataProvider(name = "FindFlightData")
    public Object[][] findFlightDataProvider() throws IOException {
        List<Map<String,String>> dataFromExcel = ExcelUtils.readExcelSheet("FindFlightData");
        Object[][] data = new Object[dataFromExcel.size()][1];

        for(int i=0;i<dataFromExcel.size();i++){
            data[i][0] = dataFromExcel.get(i);
        }
        return data;
    }

    @Test(priority = 1, groups = "P1",dataProvider = "FindFlightData")
    public void verifyFindFlight_FF0001(Map<String,String> travelDetail) throws IOException {
        flightPage.selectTripType(travelDetail.get("Trip Type"));
        flightPage.selectNumberOfPassengers(travelDetail.get("No.Of Passengers"));
        flightPage.selectSource(travelDetail.get("Source"));
        flightPage.selectStartMonth(travelDetail.get("Start Month"));
        flightPage.selectStartDay(travelDetail.get("Start Day"));
        flightPage.selectDestination(travelDetail.get("Destination"));
        flightPage.selectReturnMonth(travelDetail.get("Return Month"));
        flightPage.selectReturnDay(travelDetail.get("Return Day"));
        flightPage.selectClass(travelDetail.get("Class"));
        flightPage.selectAirlinePreference(travelDetail.get("Airline Preference"));

        TestListeners.attachScreenshotFullPage("After filling flight search details",driver);

        flightPage.clickFindFlightButton();

        Assert.assertTrue(genericHelper.getTextOfElement(flightPage.resultAfterFlightFinder).contains("After flight finder - No Seats Avaialble"),"Flight / Seats Not Found");
    }
}
