import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.interactions.Actions;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.List;
import java.util.Set;
import java.util.concurrent.TimeUnit;

public class HandlingMultipleWindows {
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
        driver.get("https://www.saucedemo.com");

        driver.manage().window().maximize();
    }

    @Test
    public void closingAllWindowsExceptMainWindow() throws InterruptedException {
        openMultipleTaps();
        // Getting the main window
        String mainWindow = driver.getWindowHandle();
        // Storing other windows in a set
        Set<String> handles = driver.getWindowHandles();

        for (String handle: handles){
            if (! handle.equals(mainWindow)){
                System.out.println(driver.switchTo().window(handle).getTitle());
                // Switching the other window
                driver.switchTo().window(handle);
                // Closing it
                driver.close();
                Thread.sleep(1000);
            }
        }

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


    private void openMultipleTaps(){
        for(int i =0; i<5; i++) ((JavascriptExecutor) driver).executeScript("window.open()");
        // Switch to the newly opened tabs
        ArrayList<String> tabs = new ArrayList<>(driver.getWindowHandles());
        for (int i =0; i<5; i++) {
            driver.switchTo().window(tabs.get(i)); // Assuming the new tab is the second tab
            // Navigate to the desired website in the new tab
            driver.get("https://google.com"); // Replace "https://example.com" with the desired URL
        }
    }
}
