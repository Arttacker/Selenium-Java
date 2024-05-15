import org.checkerframework.checker.units.qual.K;
import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.Keys;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.interactions.Actions;

public class ActionsClass {

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


    /**
     * Test function to demonstrate double-click action followed by copying text and pasting into another field.
     */
    @Test
    public void tryActions(){
        // Creating an Actions object to perform advanced user interactions
        Actions actions = new Actions(driver);

        // Finding the username field WebElement by its id
        WebElement usernameField = driver.findElement(By.id("user-name"));
        // Finding the password field WebElement by its id
        WebElement passwordField = driver.findElement(By.id("password"));

        // Performing a sequence of actions:
        // 1. Moving the mouse cursor to the username field
        // 2. Clicking on the username field
        // 3. Typing "USERNAME" into the username field
        // 4. Double-clicking on the username field
        // 5. Copying the inserted username
        // 6. Simulating focusing on the password field
        // 7. Simulating pasting the copied text into the password field
        // All these actions are built into a single composite action and performed
        actions
                .moveToElement(usernameField) // Move to the username field
                .click(usernameField).sendKeys(usernameField, "standard_user") // Click and type "standard_user"
                .doubleClick(usernameField) // Double-click on the username field
                .build() // Build the composite action
                .perform(); // Perform the composite action
        // Copying the username and pasting it in the password field
        usernameField.sendKeys(Keys.chord(Keys.CONTROL, "c"));
        // Focusing on the password field
        passwordField.click();
        // Simulating pasting the copied text into the password field
        passwordField.sendKeys(Keys.chord(Keys.CONTROL, "v"));

        // Check that the pasted is equal to the copyed text
        Assert.assertEquals("standard_user", passwordField.getAttribute("value"));
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
