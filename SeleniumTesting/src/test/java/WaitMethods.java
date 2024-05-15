import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.FluentWait;
import org.openqa.selenium.support.ui.Wait;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.time.Duration;
import java.util.NoSuchElementException;
import java.util.concurrent.TimeUnit;
import java.util.function.Function;

public class WaitMethods {

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
    }


    @Test
    public void tryImplicitWait(){
        // Implicit Wait - WebDriver waits for a certain duration of time until all the elements on the page are loaded.
        driver.manage().timeouts().implicitlyWait(2, TimeUnit.SECONDS);
        driver.findElement(By.id("login-button")).click();
        /*
            sets an implicit wait timeout for Selenium WebDriver.
            This means that when Selenium WebDriver tries to find an element using findElement() or findElements(),
            it will wait for a maximum of 2 seconds for the element to be found.
            If the element is found within this timeout period, WebDriver will proceed with the next steps of the test.
            If the element is not found within the specified timeout, WebDriver will throw a NoSuchElementException.
        */
    }

    @Test
    public void tryExplicitWait(){
        // Explicit Wait - WebDriver will wait for a specific element on the page until the given condition is satisfied.
        Duration duration = Duration.ofSeconds(2);
        WebDriverWait wait = new WebDriverWait(driver, duration);
        wait.until(ExpectedConditions.elementToBeClickable(By.id("login-button"))).click();
        /*
        this code waits for a maximum of 3 seconds for the element with id "login-button" to become clickable.
         Once it becomes clickable, it performs a click action on the element.
         If the element doesn't become clickable within 3 seconds, a TimeoutException will be thrown.
        */
    }

    @Test
    public void tryFluentWait(){
        // Fluent Wait - It tells the WebDriver to wait for a condition, as well as the frequency with which to check the condition.
        Wait<WebDriver> wait = new FluentWait<WebDriver>(driver)
                .withTimeout(Duration.ofSeconds(1))
                .pollingEvery(Duration.ofMillis(30))
                .ignoring(NoSuchElementException.class);

        WebElement element = wait.until(new Function<WebDriver, WebElement>() {
            @Override
            public WebElement apply(WebDriver webDriver) {
                return driver.findElement(By.id("login-button"));
            }
        });

        /*
        This code snippet utilizes FluentWait in Selenium WebDriver to dynamically wait for an element's presence on a web page.
        It sets a maximum wait time of 1 second, polls the DOM every 30 milliseconds, and ignores NoSuchElementExceptions during the wait.
        The wait continues until the specified condition, locating the element with id "login-button," is met.
        Once found, the WebElement is stored in the 'element' variable for further interaction in the test.
        */
    }

    @Test
    public void tryPageLoadTimeout(){
        // PageLoad Timeout - Sets the amount of time to wait for a page-load to complete before throwing an error.

        // Set the page load timeout to 30 seconds
        driver.manage().timeouts().pageLoadTimeout(30, TimeUnit.SECONDS);
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
        Thread.sleep(2000);
        // quitting the WebDriver
        driver.quit();
    }

}
