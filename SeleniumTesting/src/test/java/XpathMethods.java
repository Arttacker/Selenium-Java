import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.NoSuchElementException;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.edge.EdgeDriver;

import java.util.ArrayList;
import java.util.List;

public class XpathMethods {

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
    public void tryContains() throws InterruptedException {
        // getting the username input field with just the word 'user'
        WebElement usernameField = driver.findElement(By.xpath("//input[contains(@name, 'user')]"));
        usernameField.sendKeys("this is the username field !!!");

        Thread.sleep(3000);
        // getting the password input field with just the word 'pass'
        WebElement passwordField = driver.findElement(By.xpath("//input[contains(@name, 'pass')]"));
        passwordField.sendKeys("this is the password field !!!");

    }
    @Test
    public void tryStartWith() throws InterruptedException {
        // getting the submit button with just we know that its class starts with the word 'submit'
        WebElement submitButton = driver.findElement(By.xpath("//input[starts-with(@class, 'submit')]"));
        submitButton.click();
        Thread.sleep(3000);

        // then cancelling the error found using the error button
        WebElement errorButton = driver.findElement(By.xpath("//button[starts-with(@class, 'error')]"));
        errorButton.click();

    }

    @Test
    public void tryAndOr() throws InterruptedException {

        // getting the username input field with knowing the name and type
        WebElement usernameField = driver.findElement(By.xpath("//input[@name='user-name' and @type='text']"));
        usernameField.sendKeys("this is the username field !!!");

        Thread.sleep(3000);
        // getting the password input field with knowing the name or type
        WebElement passwordField = driver.findElement(By.xpath("//input[@name='pass' or @type='password']"));
        passwordField.sendKeys("this is the password field !!!");
    }

    @Test
    public void tryText(){

        login();

        // getting the div that contains the item with price 29.99 using the text() function
        WebElement knownPriceItem = driver.findElement(By.xpath("//div[text()='29.99']"));

        //then printing the currency used
        String currency = knownPriceItem.getText().replace("29.99", "");
        System.out.println(currency);

    }

    @Test
    public void tryIndexing(){

        login();
        // Now assuming we only know that the button which add some item to cart is called 'Add to cart'
        // and we need to get the third one in the page

        // Finding the button using its name and indexing to get the third button
        WebElement thirdButton = driver.findElement(By.xpath("(//button[text()='Add to cart'])[3]"));

        // Clicking the button
        thirdButton.click();

        // Initialize the list to hold cart item names
        List<String> cartItemsNames = new ArrayList<>();

        // Get the cart items
        List<WebElement> cartItems = getCartItems();

        // Loop through cart items to get their names
        for (WebElement item : cartItems) {
            // Find the element containing the item name relative to the current item
            WebElement itemNameElement = item.findElement(By.xpath(".//div/a/div[@class='inventory_item_name']"));

            // Get the text of the item name element and add it to the list
            cartItemsNames.add(itemNameElement.getText());
        }


        Assert.assertTrue(cartItemsNames.contains("Sauce Labs Bolt T-Shirt"));

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


    /**
     * Helper method to perform login with valid credentials.
     *
     * This method finds the username and password input fields using Selenium WebDriver,
     * clears any default values, inserts valid username and password, finds the login button,
     * and submits the login form.
     *
     * @throws NoSuchElementException if any of the required elements (username field, password field, login button) are not found.
     */
    private void login(){
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
    }

    private List<WebElement> getCartItems(){
        // navigating to the cart page
        driver.navigate().to("https://www.saucedemo.com/cart.html");

        // graping all items in the cart
        List<WebElement> cartItems = driver.findElements(By.xpath("//div[@class='cart_item']"));

        return cartItems;
    }

}
