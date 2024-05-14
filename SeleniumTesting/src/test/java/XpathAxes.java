import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import javax.swing.*;

public class XpathAxes {



    // initializing a global driver
    WebDriver driver;

    /**
     * Setup method to initialize the WebDriver and open the web page before each test.
     *
     * This method is annotated with @Before, indicating that it will be executed before each test method.
     * It initializes the WebDriver object for the Edge browser and navigates to the specified URL.
     * Adjust the screen to be in fullscreen mode.
     * It makes a valid login to reach the inventory page
     */
    @Before
    public void setup(){
        // creating a driver object
        driver = new EdgeDriver();

        // making a get request for a given url
        driver.get("https://www.saucedemo.com");

        // setting the fullscreen mode
        driver.manage().window().fullscreen();
    }


    @Test
    public void tryFollowing(){
        // Following - Selects everything in the document after the closing tag of the current node

        // Getting the login button through div that contains the form
        WebElement loginButton = driver.findElement(By.xpath("(//form//following::input)[2]"));
    }
    @Test
    public void tryFollowingSibling() throws InterruptedException {
        // FollowingSibling - Selects all siblings after the current node

        // Making incorrect login
        driver.findElement(By.id("login-button")).submit();

        // Getting the second sibling div to the form that carries the error message if the credentials aren't correct
        WebElement errorDiv = driver.findElement(By.xpath("(//form//following-sibling::div)[2]"));

        Thread.sleep(1500);

        // Then clicking the button in it
        WebElement cancelErrorButton = errorDiv.findElement(By.tagName("button"));
        cancelErrorButton.click();
    }
    @Test
    public void tryPreceding() throws InterruptedException {
        // Preceding - Selects all nodes that appear before current node

        // Making incorrect login
        driver.findElement(By.id("login-button")).submit();

        // Getting the div that shows the error message by knowing that it is 1 level preceds the login button
        WebElement errorDiv = driver.findElement(By.xpath("//input[@type='submit']//preceding::div[1]"));

        // Then clicking the button in it
        WebElement cancelErrorButton = errorDiv.findElement(By.tagName("button"));
        Thread.sleep(1500);
        cancelErrorButton.click();
    }
    @Test
    public void tryChild(){
        // Child - Selects all children of the current node

        // Making incorrect login
        driver.findElement(By.id("login-button")).submit();


        // getting the child h3 that holds the error message
        Assert.assertEquals(driver.findElement(By.xpath("(//form//child::h3)")).getText(), "Epic sadface: Username is required");

    }
    @Test
    public void tryParent(){
        // Parent - Selects the parent of the current node

        // Getting the div that contains the usernames by knowing that it contains an h4 with text 'Accepted usernames are:'
        WebElement usernamesDiv = driver.findElement(By.xpath("//h4[text()='Accepted usernames are:']/parent::div"));


        // Getting the text immediately following the usernames div
        String allUsernames = usernamesDiv.getText();

        // Check the existence of the user (standard_user)
        Assert.assertTrue(allUsernames.contains("standard_user"));
    }
    @Test
    public void tryAncestor(){
        // Ancestor - Selects all ancestors (parent or grandparent) of the current node

        // Getting the div of the form and verifying that the first ancestor for it is the div with class = 'login-box'
        WebElement loginBoxDiv = driver.findElement(By.xpath("//div[@class='form_group']/ancestor::div[1]"));

        String divClass = loginBoxDiv.getAttribute("class");
        Assert.assertEquals(divClass, "login-box");

    }
    @Test
    public void tryDescendants(){
        // Descendants - Select all descendants (children or grandchildren) of the current node

        // Getting the div of class='login-box' and verifying that the first descendant for it is the div with class = 'form_group'
        WebElement formGroupDiv = driver.findElement(By.xpath("//div[@class='login-box']//descendant::div[1]"));

        String divClass = formGroupDiv.getAttribute("class");
        Assert.assertEquals(divClass, "form_group");
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
