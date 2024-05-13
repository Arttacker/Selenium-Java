import org.junit.After;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.testng.Assert;

public class LoginPageTest {

    // initializing a global driver
    WebDriver driver;

    /**
     * Setup method to initialize the WebDriver and open the web page before each test.
     *
     * This method is annotated with @Before, indicating that it will be executed before each test method.
     * It initializes the WebDriver object for the Edge browser and navigates to the specified URL and also
     * adjust the screen position.
     */
    @Before
    public void setup(){
        // creating a driver object
        driver = new EdgeDriver();

        // making a get request for a given url
        driver.get("https://www.saucedemo.com");

        // setting the window size
        Dimension dimension = new Dimension(400, 600);
        driver.manage().window().setSize(dimension);

        // setting the window position
        Point point = new Point(0, 0); // top left
        driver.manage().window().setPosition(point);
    }


    /**
     * Test case to validate the title of the web page.
     *
     * This test retrieves the title of the current web page using Selenium WebDriver,
     * and then asserts that the title matches the expected value, in this case, "Swag Labs".
     *
     * @throws AssertionError if the title of the page does not match the expected title.
     */
    @Test
    public void testTitle(){


        // getting the title of the page
        String title = driver.getTitle();

        // validating correctness for the title
        Assert.assertTrue(title.equals("Swag Labs"));

    }


    /**
     * Test case to verify basic attributes and elements of a login form.
     *
     * This test verifies the existence and correctness of attributes such as placeholders
     * and types for the username and password input fields. It also checks the visibility
     * of the login button on the web page.
     *
     * @throws org.openqa.selenium.NoSuchElementException if any of the required elements (username field, password field, login button) are not found.
     * @throws AssertionError if any attribute or element does not match the expected values.
     */
    @Test
    public void testBasicLoginForm(){
        // checking the existence of the correct Attributes for the username and password input fields
        WebElement usernameField = driver.findElement(By.id("user-name"));
        WebElement passwordField = driver.findElement(By.id("password"));

        // checking the placeholders
        String usernameFieldPlaceholder = usernameField.getAttribute("placeholder");
        String passwordFieldPlaceholder = passwordField.getAttribute("placeholder");
        Assert.assertTrue(usernameFieldPlaceholder.equalsIgnoreCase("Username"));
        Assert.assertTrue(passwordFieldPlaceholder.equalsIgnoreCase("Password"));


        // checking the types
        String usernameFieldType = usernameField.getAttribute("type");
        String passwordFieldType = passwordField.getAttribute("type");
        Assert.assertEquals(usernameFieldType, "text");
        Assert.assertEquals(passwordFieldType, "password");


        // checking if the button is displayed
        WebElement loginButton = driver.findElement(By.id("login-button"));
        Assert.assertTrue(loginButton.isDisplayed());
    }


    /**
     * Test case to verify CSS attributes of the login button.
     *
     * This test retrieves the login button element using Selenium WebDriver
     * and verifies specific CSS attributes such as background color and font-family.
     *
     * @throws org.openqa.selenium.NoSuchElementException if the login button element is not found.
     * @throws AssertionError if any CSS attribute of the login button does not match the expected value.
     */
    @Test
    public void testLoginButtonCssAttributes(){
        // getting the login button
        WebElement loginButton = driver.findElement(By.id("login-button"));

        // checking the background color
        String color = loginButton.getCssValue("background-color");
        Assert.assertEquals(color, "rgba(61, 220, 145, 1)");

        // checking the text font
        String font = loginButton.getCssValue("font-family");
        Assert.assertTrue(font.contains("sans-serif"));
    }


    /**
     * Test case to verify successful login with valid credentials.
     *
     * This test simulates a user logging in with a valid username and password on a web page.
     * It locates the username and password input fields using Selenium WebDriver,
     * clears any default values, inputs valid credentials, and submits the login form.
     * After successful login, it asserts that the URL navigated to matches the expected URL.
     *
     * @throws org.openqa.selenium.NoSuchElementException if any of the required elements (username field, password field, login button) are not found.
     * @throws AssertionError if the URL after login does not match the expected URL.
     */
    @Test
    public void testValidLogin(){

        // getting the username input field
        WebElement usernameField = driver.findElement(By.id("user-name"));
        // clearing any default values
        usernameField.clear();
        // inserting the valid username
        usernameField.sendKeys("standard_user");

        // getting the password input field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        passwordField.sendKeys("secret_sauce");

        // getting the login button and submitting the form
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.submit();

        // retrieving the current URL after successful login
        String currentURL = driver.getCurrentUrl();
        // asserting that the URL matches the expected URL
        Assert.assertEquals("https://www.saucedemo.com/inventory.html", currentURL);

    }


    /**
     * Test case to verify login failure with invalid credentials.
     *
     * This test simulates a user attempting to log in with invalid credentials
     * on a web page. It locates the username and password input fields using
     * Selenium WebDriver, inserts invalid values, and submits the login form.
     * After submission, it checks if the error message for invalid credentials
     * is displayed on the page.
     *
     * @throws org.openqa.selenium.NoSuchElementException if any of the required elements (username field, password field, login button) are not found.
     * @throws AssertionError if the error message for invalid credentials is not displayed after login attempt.
     */
    @Test
    public void testInvalidLogin(){
        // getting the username input field
        WebElement usernameField = driver.findElement(By.id("user-name"));
        // clearing any default values
        usernameField.clear();
        // inserting the valid username
        usernameField.sendKeys("standard_user");

        // getting the password input field
        WebElement passwordField = driver.findElement(By.id("password"));
        passwordField.clear();
        // inserting the invalid password
        passwordField.sendKeys("123");

        // getting the login button and submitting the form
        WebElement loginButton = driver.findElement(By.id("login-button"));
        loginButton.submit();

        // retrieving the page source to check for error message
        String pageSource = driver.getPageSource();
        // asserting that the error message for invalid credentials is displayed
        Assert.assertTrue(pageSource.contains("Username and password do not match"));
    }


    /**
     * Teardown method to close the WebDriver after all tests have been executed.
     *
     * This method is annotated with @After, indicating that it will be executed after each test method.
     * It quits the WebDriver to release system resources.
     */
    @After
    public void Teardown(){
        // quitting the WebDriver
        driver.quit();
    }

}
