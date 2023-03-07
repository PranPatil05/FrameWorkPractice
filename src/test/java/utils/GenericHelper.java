package utils;

import org.apache.log4j.LogManager;
import org.apache.log4j.Logger;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.interactions.Action;
import org.openqa.selenium.support.ui.Select;
import org.testng.Assert;

import javax.swing.*;
import java.util.ArrayList;
import java.util.List;

public class GenericHelper {
    WebDriver driver;
    private Logger logger;
    JavascriptExecutor js;

    public GenericHelper(WebDriver driver){
        this.driver = driver;
        logger = LogManager.getLogger(this.getClass());
        js = (JavascriptExecutor)driver;
    }

    public void navigateTo(String url){
        TestBase.log("Navigating to : " + url);
//        driver.navigate().to(url);
        try{
            driver.navigate().to(url);
        }
        catch (Exception e){
            TestBase.log("Some issue occurred while navigation. \n"+ e.getStackTrace());
        }
    }

    public String getTitleOfWebPage(){
        TestBase.log("Getting title of Web Page");
        TestBase.log("Title is : " + driver.getTitle());
        return driver.getTitle();
    }

    public String getURL(){
        TestBase.log("Getting URL of Web Page");
        TestBase.log("URL is : " + driver.getCurrentUrl());
        return driver.getCurrentUrl();
    }

    public void setText(WebElement element, String text){
        element.clear();
        element.sendKeys(text);
        TestBase.log("Text set : " + text);
    }

    public void click(WebElement element){
        element.click();
        TestBase.log("Clicked");
    }

    public void clickByJS(WebElement element){
        js.executeScript("arguments[0].click()",element);
        TestBase.log("Clicked using JS");
    }

    public void clickWithRetryByJS(WebElement element){
        try{
            click(element);
        }
        catch (Exception e){
            TestBase.log("Retry click by JS");
            clickByJS(element);
        }
    }

    public void clickElementWithFocus(WebElement element){
        TestBase.log("Clicking element after scrolling");
        scrollToElement(element);
        click(element);
    }

    public void scrollToElement(WebElement element){
        TestBase.log("Scrolling to Element");
        js.executeScript("arguments[0].scrollIntoView(true)",element);
        TestBase.log("Scrolled to element");
    }

    public void scrollToTop(){
        TestBase.log("Scrolling to Top");
        js.executeScript("window.scrollTo(0,0)");
        TestBase.log("Scrolled to Top");
    }

    public void scrollToBottom(){
        TestBase.log("Scrolling to Bottom");
        js.executeScript("window.scrollTo(0,document.body.scrollHeight)");
        TestBase.log("Scrolled to Bottom");
    }

    public void setBrowserZoomLevel(float level){
        TestBase.log("Setting browser zoom level to " + level +" %");
        js.executeScript("document.body.style.zoom = '" + Float.toString(level/100)+"'");
        TestBase.log("Zoom level set");
    }

    public String getXpath(WebElement element){
        String elementToString = element.toString();
        return elementToString.substring(elementToString.indexOf('/'),elementToString.length()-1);
    }

    public String getTextOfElement(WebElement element){
        TestBase.log("Text is : " + element.getText());
        return element.getText();
    }

    public List<String> getTextOfElementList(List<WebElement> elementList){
        List<String> textList = new ArrayList<>();
        for(WebElement element:elementList){
            textList.add(element.getText());
        }
        TestBase.log("Text for List is : \n" + textList);
        return textList;
    }

    public void clickFromList(List<WebElement> elements,String text){
        boolean elementFound = false;
        TestBase.log("Clicking from list with text : " + text);
        for(WebElement element:elements){
            if(element.getText().equals(text)){
                click(element);
                elementFound = true;
                break;
            }
        }
        if(!elementFound){
            logger.error("Element with following text not found : " + text);
        }
    }

    public void selectByVisibleText(WebElement element, String text){
        Select select = new Select(element);
        try{
            select.selectByVisibleText(text);
            TestBase.log("Selected by Visible text : " + text);
        }catch (Exception e){
            TestBase.log("No Visible Text found for : " + text);
//            Assert.fail("No Visible Text found for : " + text);
        }
    }

    public void refreshPage(){
        TestBase.log("Refreshing page : " + driver.getCurrentUrl());
        driver.navigate().refresh();
    }

    public boolean isDisplayed(WebElement element){
        try{
            return element.isDisplayed();
        }
        catch (Exception e){
            return false;
        }
    }

    public void pressEscapeKey(){
        TestBase.log("Pressing Escape key");
        Actions action = new Actions(driver);
        Action pressEscKey = action.sendKeys(Keys.ESCAPE).build();
        pressEscKey.perform();
    }

    public void hardWait(long timeInSeconds) throws InterruptedException {
        TestBase.log("Waiting for " + timeInSeconds + " seconds");
        Thread.sleep(timeInSeconds * 1000);
        TestBase.log("Waiting Finished");
    }
}

