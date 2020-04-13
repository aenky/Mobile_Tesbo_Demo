package CustomStep;

import Selenium.ExtendTesboDriver;
import io.appium.java_client.*;
import io.appium.java_client.TouchAction;
import io.appium.java_client.android.AndroidElement;

import io.appium.java_client.touch.WaitOptions;
import io.appium.java_client.touch.offset.PointOption;
import org.apache.commons.io.FileUtils;
import org.junit.Assert;
import org.openqa.selenium.*;
import reportAPI.Reporter;

import static io.appium.java_client.touch.WaitOptions.waitOptions;
import static io.appium.java_client.touch.offset.PointOption.point;
import static java.time.Duration.ofMillis;
import Exception.*;

import javax.imageio.ImageIO;
import java.awt.image.BufferedImage;
import java.awt.image.DataBufferByte;
import java.io.File;
import java.io.IOException;
import java.time.Duration;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Arrays;

import ru.yandex.qatools.ashot.AShot;
import ru.yandex.qatools.ashot.Screenshot;
import ru.yandex.qatools.ashot.comparison.ImageDiff;
import ru.yandex.qatools.ashot.comparison.ImageDiffer;

public class Code extends ExtendTesboDriver {
    public Code(MobileDriver driver) {
        super(driver);
    }
    Reporter reporter=new Reporter();
    static String UnitTaskName="";
    public static String pastQuestionTitle;

    @Step("Click on Coordinates")
    void clickOnCoordinates(String x,String y)
    {

        new TouchAction(driver)
                .tap(point(Integer.parseInt(x),Integer.parseInt(y)))
                .waitAction(waitOptions(ofMillis(1000)))
                .perform();

    }

    @Step("Swipe Left")
    void swipeLeft()
    {
        MobileElement element;

        try{
            element= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[@text='DD Test Property 14']"));
        }catch (Exception e){
            element= (MobileElement) driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='DD Test Property 14']"));
        }

        int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        int endX = element.getLocation().getX() + (element.getSize().getWidth() / 8);
        int endY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        new TouchAction(driver)
                .press(point(startX,startY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endX, endY))
                .release().perform();

    }

    @Step("Verify Categories")
    void verifyCategories() {
        String[] CategoriesList={"Accounting", "Asset Management", "Code", "Commercial", "Compliance", "Leasing", "Maintenance", "Marketing", "Operations", "Risk Management", "Training"};

            //Marketing
        for(String category:CategoriesList){
            try {
                if(category.equals("Leasing")){
                    swipeToBottom();
                    wait(1);
                }
                if ((driver.findElement(By.xpath("//android.widget.TextView[@text='" + category + "']"))).getText().equals(category)) {
                    reporter.printStep("'"+category+"' category is display!!!");
                }
            }catch (Exception e){
                throw new AssertException("'"+category+"' category is not found!!!");
            }
        }
        scrollTop();
    }

   /* @Step("Verify saved notes")
    void verifySavedNotesList()
    {
        AndroidElement notesList= (AndroidElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text, 'DDTester 14')]"));
        for (WebElement list : notesList) {
            if(!list.equals("Hey,I am unsaved note"))
            {
                System.out.println("not Saved");
            }
            else
            {
                System.out.println("Saved");
            }

        }

    }*/

    @Step("Scroll Bottom")
    public void swipeToBottom()
    {
        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();
        int x = width/2;
        int top_y = (int)(height*0.80);
        int bottom_y = (int)(height*0.20);

        new TouchAction(driver)
                .press(point(x, top_y))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(x, bottom_y))
                .release().perform();
    }

    public void scrollTop() {
        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();
        int x = width/2;
        int top_y = (int)(height*0.80);
        int bottom_y = (int)(height*0.20);

        new TouchAction(driver)
                .press(point(x, bottom_y))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(x, top_y))
                .release().perform();
    }

    public void wait(int sec) {
        try {
            Thread.sleep(sec * 1000);
        } catch (InterruptedException e) {
        }
    }


    @Step("Select Value for Question")
    public void selectAnswerFortQuestion(String QuestionTitle, String option) {
        String osName=driver.getPlatformName().toLowerCase();

        if(QuestionTitle.equals("GFCI in Kitchen")){
            QuestionTitle="GFCI(s) in Kitchen";
        }
        else if(QuestionTitle.equals("GFCI in Bathroom?")){
            QuestionTitle="GFCI(s) in Bathroom?";
        }
        else if(QuestionTitle.equals("Bathroom Shower Door")){
            QuestionTitle="Bathroom Shower Door(s)";
        }
        else if(QuestionTitle.equals("Sprinkler Head")){
            QuestionTitle="Sprinkler Head(s)";
        }

        try{
            if(QuestionTitle.equals("Kitchen Light Fixture")){
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 500)).release().perform();
            }
            if(osName.equals("ios")){
                driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();
            }else {
                driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();
            }
        }catch (Exception e1){
            try{
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();
            }catch (Exception e){
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();

                }catch (Exception e2) {
                    try {
                        TouchAction action = new TouchAction(driver);
                        action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                        driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();

                    }catch (Exception e3){
                        System.err.println("Error ");
                        System.out.println("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']");
                    }
                }
            }
        }

    }

    @Step("Verify Saved Value Of Question Old")
    public void VerifySavedTaskDetails(String QuestionTitle, String option) {
        WebElement element=null;
        boolean isDisplay=true;
        try{
            driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();
        }catch (Exception e1) {
            try {
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                element = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']"));
            } catch (Exception e) {
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    element = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']"));

                } catch (Exception e2) {
                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                    element = driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']"));
                }
            }
        }
        if(element!=null){
            element.isDisplayed();
        }
    }

    @Step("Scroll to questions")
    public void scrollToQuestions() {

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 700)).release().perform();
        wait(1);
        action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(6000))).moveTo(PointOption.point(300, 200)).release().perform();
        action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(6000))).moveTo(PointOption.point(300, 400)).release().perform();


    }

    @Step("Scroll From questions")
    public void scrollFromQuestions() {

        TouchAction action = new TouchAction(driver);
        action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(6000))).moveTo(PointOption.point(300, 200)).release().perform();
        action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(6000))).moveTo(PointOption.point(300, 400)).release().perform();

    }

    @Step("Verify Filter")
    public void verifyFilter(){
        driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Priority: Minor')]")).getText().equals("Priority: Minor");
        reporter.printStep("Filter is Verified");
    }

    @Step("Verify Unit Task")
    public void verifyUnitTask(){
        String osName=driver.getPlatformName().toLowerCase();

        int i=2;
        int j=1;
        String unitTasks;
        String Bidg;
        if(osName.equals("ios")){
            Bidg="((//XCUIElementTypeOther[contains(@text,'Bldg: 1 Unit:')])[3])["+i+"]";
            unitTasks= "//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')] | //android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]";
        }
        else {
            Bidg="(//android.widget.TextView[contains(@text,'Bldg: 1 Unit:')])["+i+"]";
            unitTasks="//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')] | //android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]";
        }
        MobileElement element01;

        while(true) {

            try {
                element01 = (MobileElement) driver.findElement(By.xpath(unitTasks));
                break;
            }
            catch (Exception e){
                try {
                    driver.findElement(By.xpath(Bidg)).click();
                    i++;
                    wait(5);
                }catch (Exception e1){i++;}
            }
        }
    }

    @Step("Select Unit Task")
    public void selectUnitTask(){

        String osName=driver.getPlatformName().toLowerCase();

        int i=2;
        int j=1;
        String unitTasks;
        String Bidg;
        if(osName.equals("ios")){
            Bidg="((//XCUIElementTypeOther[contains(@text,'Bldg: 1 Unit:')])[3])["+i+"]";
            unitTasks= "//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')] | //android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]";
        }
        else {
            Bidg="(//android.widget.TextView[contains(@text,'Bldg: 1 Unit:')])["+i+"]";
            unitTasks="//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')] | //android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]";
        }
        MobileElement element01;

        while(true) {

            try {
                element01 = (MobileElement) driver.findElement(By.xpath(unitTasks));
                break;
            }
            catch (Exception e){
                try {
                    driver.findElement(By.xpath(Bidg)).click();
                    i++;
                    wait(5);
                }catch (Exception e1){i++;}
            }
        }
        int startX = element01.getLocation().getX() + (element01.getSize().getWidth());
        int startY = element01.getLocation().getY() + (element01.getSize().getHeight() / 2);

        int endX = element01.getLocation().getX() + (element01.getSize().getWidth() / 10);
        int endY = element01.getLocation().getY() + (element01.getSize().getHeight() / 2);
        UnitTaskName =element01.getText();
        System.out.println("======> UnitTaskName: "+UnitTaskName);
        new TouchAction(driver)
                .press(point(startX,startY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endX, endY))
                .release().perform();

        wait(2);

        if(osName.equals("ios")){
            driver.findElement(By.xpath("(//XCUIElementTypeOther[contains(@name,'DD Unit by Unit Test - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1] | (//XCUIElementTypeOther[contains(@name,'Due Diligence Unit by Unit - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1] | (//XCUIElementTypeOther[contains(@name,'Test from - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]")).click();
        }
        else{
            driver.findElement(By.xpath("(//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')]/following::android.widget.ImageView)[3] | (//android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]/following::android.widget.ImageView)[3]")).click();
        }

    }

    @Step("Select Value for Occupancy Status Question")
    public void selectAnswerForOccupancyStatusQuestion() {

        Dimension dim = driver.manage().window().getSize();
        int height = dim.getHeight();
        int width = dim.getWidth();
        TouchAction action = new TouchAction(driver);
        int x = width/2;
        int top_y = (int)(height*0.80);
        int bottom_y = (int)(height*0.20);

        String isOccupied=driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'Occupancy Status *')]/following::android.widget.TextView[@text='Occupied']")).getAttribute("checked");

       // if(driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']")).click();


    }

    @Step("Verify complete Unit task msg")
    public void VerifyCompleteUnitTaskMsg() {
        MobileElement element= (MobileElement) driver.findElement(By.id("com.webpartners.leonardo:id/snackbar_text"));
        if(element.isDisplayed()){
            System.out.println("Success Message is displayed!!!");
        }
        else {
            System.out.println("Message is not displayed!!!");
        }

    }


    @Step("Select Unit Task for Cannot do Task")
    public void selectUnitTaskCanNotDoTask(){
        int i=2;
        int j=1;
        String osName=driver.getPlatformName().toLowerCase();
        String unitTasks="//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')] | //android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]";
        MobileElement element01;


        if(osName.equals("ios")){
            try{
                element01 = (MobileElement) driver.findElement(By.xpath("(//XCUIElementTypeOther[contains(@name,'DD Unit by Unit Test - 1')])[17]"));

            }catch (Exception e){
                try{
                    element01 = (MobileElement) driver.findElement(By.xpath("(//XCUIElementTypeOther[contains(@name,'Due Diligence Unit by Unit - 1')])[17]"));

                }catch (Exception e1){
                    element01 = (MobileElement) driver.findElement(By.xpath("(//XCUIElementTypeOther[contains(@name,'Test from - 1')])[17]"));
                }
            }

        }
        else {

            while(true) {
                String Bidg="(//android.widget.TextView[contains(@text,'Bldg: 1 Unit:')])["+i+"]";
                try {
                    element01 = (MobileElement) driver.findElement(By.xpath(unitTasks));
                    break;
                }
                catch (Exception e){
                    try {
                        driver.findElement(By.xpath(Bidg)).click();
                        i++;
                        wait(5);
                    }catch (Exception e1){i++;}
                }
            }

        }






        int startX = element01.getLocation().getX() + (element01.getSize().getWidth());
        int startY = element01.getLocation().getY() + (element01.getSize().getHeight() / 2);

        int endX = element01.getLocation().getX() + (element01.getSize().getWidth() / 10);
        int endY = element01.getLocation().getY() + (element01.getSize().getHeight() / 2);

        UnitTaskName=element01.getText();
        System.out.println("====> UnitTaskName: "+UnitTaskName);

        new TouchAction(driver)
                .press(point(startX,startY))
                .waitAction(waitOptions(ofMillis(1000)))
                .moveTo(point(endX, endY))
                .release().perform();

        wait(2);
        if(osName.equals("ios")){
            driver.findElement(By.xpath("(//XCUIElementTypeOther[contains(@name,'DD Unit by Unit Test - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1] | (//XCUIElementTypeOther[contains(@name,'Due Diligence Unit by Unit - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1] | (//XCUIElementTypeOther[contains(@name,'Test from - 1')])[17]/following::XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther[1]")).click();
            wait(3);
            new TouchAction(driver).tap(PointOption.point(150,150)).perform();
        }else {
            driver.findElement(By.xpath("(//android.widget.TextView[contains(@text,'DD Unit by Unit Test - 1')]/following::android.widget.ImageView)[2] | (//android.widget.TextView[contains(@text,'Due Diligence Unit by Unit - 1')]/following::android.widget.ImageView)[2]")).click();
        }
    }

    @Step("Verify validation message")
    public void VerifyValidationMessage(){
        MobileElement element= (MobileElement) driver.findElement(By.id("com.webpartners.leonardo:id/snackbar_text"));
        if(element.isDisplayed()){
            System.out.println("Message is displayed!!!");
        }
        else {
            System.out.println("Message is not displayed!!!");
        }
    }

    @Step("Verify task moved to cannot do success message")
    public void VerifyValidationMessageOfTaskMoved(){
        MobileElement element= (MobileElement) driver.findElement(By.id("com.webpartners.leonardo:id/snackbar_text"));
        if(element.isDisplayed()){
            System.out.println("Success Message is displayed");
        }
        else {
            System.out.println("Message is not displayed!!!");
        }
    }

    @Step("Click on list tab")
    public void ClickOnListTab(){
        MobileElement element;
        try{
            element= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[@text='LIST']"));

        }catch (Exception e){
            element= (MobileElement) driver.findElement(By.xpath("//XCUIElementTypeOther[@name='LIST']"));
        }

        int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        new TouchAction(driver).tap(PointOption.point(startX, startY)).perform();

    }

    @Step("Click on eye icon")
    public void ClickOnEyeIcon(){
        MobileElement element;
        try{
            element= (MobileElement) driver.findElement(By.xpath("/hierarchy/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.LinearLayout/android.widget.FrameLayout/android.widget.FrameLayout/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup[2]/android.widget.ScrollView/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup[2]/android.view.ViewGroup/android.view.ViewGroup/android.widget.ImageView"));

        }catch (Exception e){
            element= (MobileElement) driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='DD Test Property 14 Due Diligence Testing - Mobile App 1 %'])[2]/XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther/XCUIElementTypeOther"));
        }

        int startX = element.getLocation().getX() + (element.getSize().getWidth() / 2);
        int startY = element.getLocation().getY() + (element.getSize().getHeight() / 2);

        new TouchAction(driver).tap(PointOption.point(startX, startY)).perform();

    }


    @Step("Verify Leave Note Created msg")
    public void VerifyLeaveNoteCreatedMsg() {

         String text=driver.findElement(By.xpath("//android.widget.TextView[@text='Note created']")).getText();

        System.out.println("Test : "+text);

        text=driver.findElement(By.id("com.webpartners.leonardo:id/snackbar_text")).getText();
        System.out.println("Text >> : "+text);
        if(driver.findElement(By.id("com.webpartners.leonardo:id/snackbar_text")).isDisplayed()){
            System.out.println("Success Message is displayed!!!");
        }
        else {
            System.out.println("Message is not displayed!!!");
        }

    }

    static String notes;


    @Step("Verify Leave Notes Message")
    public void verifyLeaveNotesMessage() {
        String osName=driver.getPlatformName().toLowerCase();

        String xPath;
        if(osName.equals("ios")){xPath="//XCUIElementTypeStaticText[@name='"+notes+"']"; }
        else {xPath="//android.widget.TextView[contains(@text,'"+notes+"')]";}

        MobileElement element = null;
        boolean isElement=false;

        for(int i=0; i<=15;i++){
            if(i==15){
                swipeToBottom();
                element = (MobileElement) driver.findElement(By.xpath(xPath));
                isElement=true;
            }else {
                try {
                    swipeToBottom();
                    element = (MobileElement) driver.findElement(By.xpath(xPath));
                    isElement = true;
                    break;
                } catch (Exception e) {
                }
            }
        }
        if(isElement){
            if(element.getText().equals(notes)){
                System.out.println("'"+notes+"' Leave Notes is displayed!!!!");
            }
        }

    }

    @Step("Add Leave Details")
    public void enterLeaveNotesDetails(){
        String osName=driver.getPlatformName().toLowerCase();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmm");

        LocalDateTime today = LocalDateTime.now();
        notes= "Test Automation "+ formatter.format(today);
        if(osName.equals("ios")){ driver.findElement(By.xpath("//XCUIElementTypeTextView[@name='Write a note']")).sendKeys(notes); }
        else{driver.findElement(By.xpath("//android.widget.EditText")).sendKeys(notes); }

        reporter.printStep("Enter leave notes: "+notes);
    }

    @Step("Verify Value is not saved for Question")
    public void imageComparison(String QuestionTitle, String option)
    {
        MobileElement checkBox = null;
        try{

            checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));

        }catch (Exception e1){
            try{
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));
            }catch (Exception e){
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));

                }catch (Exception e2) {
                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                    checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));
                }
            }
        }

        BufferedImage expectedImage = null;
        BufferedImage actualImage = null;
        String actualScreenshot="";
        try {
            expectedImage = ImageIO.read(new File(System.getProperty("user.dir") +"/src/test/Resources/CheckBox.png"));
            actualScreenshot= screenshotElement(checkBox,QuestionTitle, option);
            actualImage = ImageIO.read(new File(System.getProperty("user.dir") +"/"+actualScreenshot));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageDiffer imgDiff = new ImageDiffer();
        if(actualImage!= null) {
            ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
            if(diff.hasDiff()){
                throw new TesboException("Option '"+option+"' of question '"+QuestionTitle+"' is selected");

            }

            reporter.printStep("Verified value is not saved for Question!!!");
        }

    }

    @Step("Verify Saved Value Of Question")
    public void imageComparisonForSelectedCheckBox(String QuestionTitle, String option)
    {
        MobileElement checkBox = null;
        TouchAction action = new TouchAction(driver);
        try{
            //action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 600)).release().perform();
            checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));

        }catch (Exception e1){
            try{
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));
            }catch (Exception e){
                try {
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));

                }catch (Exception e2) {
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                    checkBox= (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'" + QuestionTitle + "')]/following::android.widget.TextView[@text='" + option + "']/../android.widget.ImageView"));
                }
            }
        }

        BufferedImage expectedImage = null;
        BufferedImage actualImage = null;
        String actualScreenshot="";
        try {
            expectedImage = ImageIO.read(new File(System.getProperty("user.dir") +"/src/test/Resources/SelectedCheckBox.png"));
            actualScreenshot= screenshotElement(checkBox,QuestionTitle, option);
            actualImage = ImageIO.read(new File(System.getProperty("user.dir") +"/"+actualScreenshot));
        } catch (IOException e) {
            e.printStackTrace();
        }
        ImageDiffer imgDiff = new ImageDiffer();
        if(actualImage!= null) {
            ImageDiff diff = imgDiff.makeDiff(actualImage, expectedImage);
            if(diff.hasDiff()){
                throw new TesboException("Option '"+option+"' of question '"+QuestionTitle+"' is not selected");
            }

            reporter.printStep("Verified value is saved for Question!!!");
        }

    }


    public String screenshotElement(MobileElement element,String QuestionTitle,String option) throws IOException {

        String path;
        File screenshot = ((TakesScreenshot)driver).getScreenshotAs(OutputType.FILE);
        BufferedImage fullImg = null;

        fullImg = ImageIO.read(screenshot);

        Point point = element.getLocation();

        int eleWidth = element.getSize().getWidth();
        int eleHeight = element.getSize().getHeight();

        BufferedImage eleScreenshot= fullImg.getSubimage(point.getX(), point.getY(), eleWidth, eleHeight);
        ImageIO.write(eleScreenshot, "png", screenshot);
        DateTimeFormatter dtf = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        path ="ElementScreenshots/"+QuestionTitle.replaceAll(" ","_")+"_"+option.replaceAll(" ","_")+ "_" + dtf.format(LocalDateTime.now()) + ".png";
        File screenshotLocation = new File(path);
        FileUtils.copyFile(screenshot, screenshotLocation);

        return path;
    }


    @Step("Scroll to questions till Completed tab is display and click on it")
    public void scrollToQuestionsTillCompletedTabIsDisplay() {

        TouchAction action = new TouchAction(driver);
       while(true) {
           action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 200)).release().perform();
           try {
               driver.findElement(By.xpath("//android.widget.TextView[@text='Completed']")).click();
               break;
           }catch (Exception e){}

       }
    }

    @Step("Scroll to questions till Cannot Do tab is display and click on it")
    public void scrollToQuestionsTillCannotDoTabIsDisplay() {

        String osName=driver.getPlatformName().toLowerCase();

        TouchAction action = new TouchAction(driver);
        while(true) {
            action.press(PointOption.point(300, 1000)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 200)).release().perform();
            try {
                if(osName.equals("ios")){driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='Cannot Do'])[3]")).click(); }
                else {driver.findElement(By.xpath("//android.widget.TextView[@text='Cannot Do']")).click();}

                break;
            }catch (Exception e){}

        }
    }

    @Step("Verify Completed Unit Task is Display")
    public void verifyCompletedUnitTaskDisplay() {
        boolean isDisplay=false;
        try{

            driver.findElement(By.xpath("//android.widget.TextView[@text='"+UnitTaskName+"']")).isDisplayed();
            isDisplay=true;
        }catch (Exception e1){
            try{
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                driver.findElement(By.xpath("//android.widget.TextView[@text='"+UnitTaskName+"']")).isDisplayed();
                isDisplay=true;
            }catch (Exception e){
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    driver.findElement(By.xpath("//android.widget.TextView[@text='"+UnitTaskName+"']")).isDisplayed();
                    isDisplay=true;
                }catch (Exception e2) {
                    try {
                        TouchAction action = new TouchAction(driver);
                        action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                        driver.findElement(By.xpath("//android.widget.TextView[@text='"+UnitTaskName+"']")).isDisplayed();
                        isDisplay=true;
                    }catch (Exception e3){

                    }
                }
            }

            if(isDisplay){
                reporter.printStep("Completed Unit Task is Displayed!!");
            }
            else {
                throw new TesboException("Completed Unit Task is not Displayed!!");
            }
        }

    }

    @Step("Verify Cannot do Unit Task is Display")
    public void verifyCannotDoUnitTaskDisplay() {
        String osName=driver.getPlatformName().toLowerCase();
        String path;

        if(osName.equals("ios")){path="(//XCUIElementTypeOther[@name='"+UnitTaskName+"'])[5]"; }
        else {path = "//android.widget.TextView[@text='"+UnitTaskName+"']"; }

        boolean isDisplay=false;
        try{


            System.out.println("====> path: "+path);
            MobileElement element= (MobileElement) driver.findElement(By.xpath(path));
            System.out.println("=====> "+element.getText());
            driver.findElement(By.xpath(path)).isDisplayed();
            isDisplay=true;
        }catch (Exception e1){
            try{
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                driver.findElement(By.xpath(path)).isDisplayed();
                isDisplay=true;
            }catch (Exception e){
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    driver.findElement(By.xpath(path)).isDisplayed();
                    isDisplay=true;
                }catch (Exception e2) {
                    try {
                        TouchAction action = new TouchAction(driver);
                        action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                        driver.findElement(By.xpath(path)).isDisplayed();
                        isDisplay=true;
                    }catch (Exception e3){

                    }
                }
            }

            if(isDisplay){
                reporter.printStep("Completed Unit Task is Displayed!!");
            }
            else {
                throw new TesboException("Completed Unit Task is not Displayed!!");
            }
        }

    }

    static String firstUserName="";
    @Step("Get first user name")
    public void getUserName() {
        String osName=driver.getPlatformName().toLowerCase();

        if(osName.equals("ios")){firstUserName=  driver.findElement(By.xpath("//XCUIElementTypeStaticText[contains(@name,'users selected')]/../XCUIElementTypeOther[2]//XCUIElementTypeStaticText[1]")).getText(); }
        else {firstUserName=  driver.findElement(By.xpath("//android.widget.TextView[contains(@text,' users selected')]/../android.widget.ScrollView//android.widget.TextView")).getText(); }
        System.out.println("=====> firstUserName: "+firstUserName);
    }

    @Step("Verify Assign User is Displayed in List")
    public void verifyAssignUserDisplayedList() {
        String osName=driver.getPlatformName().toLowerCase();
        MobileElement AssignUser;
        if(osName.equals("ios")){ AssignUser=  (MobileElement) driver.findElement(By.xpath("//XCUIElementTypeStaticText[@name='"+firstUserName+"']"));}
        else{ AssignUser=  (MobileElement) driver.findElement(By.xpath("//android.widget.TextView[contains(@text,'"+firstUserName+"')]"));}

        if(AssignUser.isDisplayed()){
            reporter.printStep("Assign User is displayed on list!!!!");
        }
        else {
            throw new TesboException("Assign User is not display on list");
        }
    }

    public static String[] Notes;
    @Step("Add Notes Ten time")
    public void enterNotesDetailsTenTime(){

        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyyMMddHHmmss");
        for(int i=0; i<10; i++) {
            LocalDateTime today = LocalDateTime.now();

            driver.findElement(By.xpath("//XCUIElementTypeTextView[@name='Write a note']")).sendKeys(notes);
            Notes[i] = "Test Notes " + formatter.format(today);
            reporter.printStep("Enter notes "+i+" : " + Notes[i]);
            wait(1);
            driver.findElement(By.xpath("//XCUIElementTypeOther[@name='Leave a note']")).click();
            wait(2);
            driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='ADD NOTE'])[2]")).click();
            wait(1);

        }
    }

    @Step("Verify added all ten notes")
    public void verifyAddedTenNotesMessage() {

        for(int j=0; j<10; j++){

            String xPath="//XCUIElementTypeStaticText[@name='"+Notes[j]+"']";

            MobileElement element = null;
            boolean isElement=false;

            for(int i=0; i<=15;i++){
                try {
                    element = (MobileElement) driver.findElement(By.xpath(xPath));
                    isElement = true;
                    break;
                } catch (Exception e) {
                    swipeToBottom();
                    element = (MobileElement) driver.findElement(By.xpath(xPath));
                    isElement = true;
                    break;
                }
            }
            if(isElement){
                if(element.getText().equals(Notes[j])){
                    System.out.println("'"+Notes[j]+"' Leave Notes is displayed!!!!");
                }
            }

        }
    }

    @Step("Get first workflow name")
    public void getWorkflowName() {
        String osName=driver.getPlatformName().toLowerCase();

        if(osName.equals("ios")){UnitTaskName=  driver.findElement(By.xpath("(//XCUIElementTypeOther[@name='Workflow'])[1]/../XCUIElementTypeOther[2]/XCUIElementTypeOther/XCUIElementTypeOther[3]/XCUIElementTypeOther/XCUIElementTypeOther")).getText();}
        else { UnitTaskName=  driver.findElement(By.xpath("//android.widget.TextView[@text='Workflow']/../../../../android.view.ViewGroup[2]//android.widget.TextView[1]")).getText();}
        System.out.println("=====> firstWorkFlowName: "+UnitTaskName);
    }

    @Step("Verify Cannot do Workflow Task is Display")
    public void verifyCannotDoWorkFlowTaskDisplay() {
        String osName=driver.getPlatformName().toLowerCase();

        String xPath;

        if(osName.equals("ios")){xPath="//XCUIElementTypeOther[@name='"+UnitTaskName+"']";}
        else {xPath="//android.widget.TextView[@text='"+UnitTaskName+"']";}



        boolean isDisplay=false;
        try{


            MobileElement element= (MobileElement) driver.findElement(By.xpath(xPath));
            System.out.println("=====> "+element.getText());
            driver.findElement(By.xpath("")).isDisplayed();
            isDisplay=true;
        }catch (Exception e1){
            try{
                TouchAction action = new TouchAction(driver);
                action.press(PointOption.point(300, 1200)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(1000))).moveTo(PointOption.point(300, 400)).release().perform();

                driver.findElement(By.xpath(xPath)).isDisplayed();
                isDisplay=true;
            }catch (Exception e){
                try {

                    TouchAction action = new TouchAction(driver);
                    action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 1000)).release().perform();
                    driver.findElement(By.xpath(xPath)).isDisplayed();
                    isDisplay=true;
                }catch (Exception e2) {
                    try {
                        TouchAction action = new TouchAction(driver);
                        action.press(PointOption.point(300, 500)).waitAction(WaitOptions.waitOptions(Duration.ofMillis(2000))).moveTo(PointOption.point(300, 800)).release().perform();
                        driver.findElement(By.xpath(xPath)).isDisplayed();
                        isDisplay=true;
                    }catch (Exception e3){

                    }
                }
            }

            if(isDisplay){
                reporter.printStep("Completed Unit Task is Displayed!!");
            }
            else {
                throw new TesboException("Completed Unit Task is not Displayed!!");
            }
        }

    }

    @Step("Verify User is Already Assign WorkFlow")
    public void VerifyUserIsAlreadyAssign() {
        UnitTaskName=  driver.findElement(By.xpath("//android.widget.TextView[@text='Workflow']/../../../../android.view.ViewGroup[2]//android.widget.TextView[1]")).getText();
        System.out.println("=====> firstWorkFlowName: "+UnitTaskName);
    }


}

