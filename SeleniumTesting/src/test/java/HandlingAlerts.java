import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

public class HandlingAlerts {

    // initializing a global driver
    WebDriver driver;

    /**
     * Setup method to initialize the WebDriver and open the web page before each test.
     *
     * This method is annotated with @Before, indicating that it will be executed before each test method.
     * It initializes the WebDriver object for the Edge browser and navigates to the specified URL.
     * It makes a valid login to reach the inventory page
     */
    @Before
    public void setup(){
        // creating a driver object
        driver = new EdgeDriver();

        // making a get request for a given url
        driver.get("https://www.selenium.dev/selenium/web/alerts.html");
    }


    @Test
    public void handlingAlerts() throws InterruptedException {
        // Simple Alert — Used to display some information to user and only contains an ok button

        //Finding the simple alert button
        WebElement simpleAlertBtn = driver.findElement(By.xpath("//a[@id='alert']"));
        WebElement promptAlertBtn = driver.findElement(By.xpath("//a[@id='prompt']"));
        WebElement confirmationAlertBtn = driver.findElement(By.xpath("//a[@id='confirm']"));


        simpleAlertBtn.click();
        driver.switchTo().alert().accept();
        Thread.sleep(2000);

        promptAlertBtn.click();
        driver.switchTo().alert().sendKeys("SALEH IS A HACKER!!");
        Thread.sleep(2000);
        driver.switchTo().alert().accept();

        confirmationAlertBtn.click();
        String alertText = driver.switchTo().alert().getText();
        System.out.println(alertText);
        driver.switchTo().alert().dismiss();
        Thread.sleep(2000);

    }

    /**
     * Teardown method to close the WebDriver after all tests have been executed.
     *
     * This method is annotated with @After, indicating that it will be executed after each test method.
     * It quits the WebDriver to release system resources.
     */
    @After
    public void tearDown() throws InterruptedException {
        // wait 2 seconds
        Thread.sleep(3000);
        // quitting the WebDriver
        driver.quit();
    }
}
