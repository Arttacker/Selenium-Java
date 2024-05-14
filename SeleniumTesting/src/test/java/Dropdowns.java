import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;
import org.openqa.selenium.*;
import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.support.ui.Select;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

public class Dropdowns {
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

        login();
    }


    /**
     * Test case to verify sorting functionality of the drop-down menu.
     *
     * This test finds the drop-down element for product sorting using Selenium WebDriver,
     * creates a Select object to interact with the drop-down, selects the option for sorting
     * products from Z to A, retrieves the active value from the drop-down, and verifies that
     * the selected value matches the expected value.
     *
     * @throws NoSuchElementException if the drop-down element or active option span element is not found.
     * @throws AssertionError if the selected value does not match the expected value.
     */
    @Test
    public void testSortDropDownMenu(){
        // Finding the dropdown element
        WebElement productSortContainer = driver.findElement(By.xpath("//select[@class='product_sort_container']"));

        // Create a Select object to interact with the dropdown
        Select select = new Select(productSortContainer);

        // Selecting the second choice (Z to A)
        select.selectByIndex(1);

        // Finding the span that holds the active value
        WebElement sortingChoice = driver.findElement(By.xpath("//span[@class='active_option']"));

        // Getting the active value
        String selectedValue = sortingChoice.getText();
        Assert.assertEquals(selectedValue, "Name (Z to A)");
    }


    /**
     * Test case to verify multiple selection behavior of dropdown menus.
     *
     * This test checks if the dropdown menu in the main website setup does not allow multiple selection,
     * then navigates to another website to test a dropdown menu that allows multiple selection. It selects
     * multiple options by their indexes, prints the selected options, and verifies if they are selected.
     *
     * @throws NoSuchElementException if any of the required elements (dropdown menus) are not found.
     * @throws AssertionError if the expected multiple selection behavior does not match the actual behavior.
     */
    @Test
    public void testMultipleSelection(){
        // Testing for multiple selection in the main website in the setup >> it shouldn't be multiple
        // Finding the dropdown element
        WebElement productSortContainer = driver.findElement(By.xpath("//select[@class='product_sort_container']"));
        Select select1 = new Select(productSortContainer);
        Assert.assertFalse(select1.isMultiple());

        // Now testing for multiple selection in another website's dropdown menu
        driver.navigate().to("https://only-testing-blog.blogspot.com/");

        WebElement countryDropdown = driver.findElement(By.xpath("//select[@name='FromLB']"));
        Select select2 = new Select(countryDropdown);
        Assert.assertTrue(select2.isMultiple());

        // Now selecting more than one value by indexes
        select2.selectByIndex(0);
        select2.selectByIndex(1);
        select2.selectByIndex(3);
        select2.selectByIndex(5);

        // Printing all the selected options
        List<WebElement> selectedOptions = select2.getAllSelectedOptions();
        for (WebElement option : selectedOptions){
            System.out.println(option.getAttribute("value"));
        }
    }


    /**
     * Test case to verify product sorting functionality from high to low price.
     *
     * This test selects the 'hilo' option in the product sorting dropdown,
     * retrieves all product prices, sorts them in descending order, and
     * verifies that the prices are sorted correctly.
     *
     * @throws NoSuchElementException if the product sorting dropdown element or any product item element is not found.
     * @throws NumberFormatException if the price text of any product item cannot be parsed as a float.
     * @throws AssertionError if the original prices do not match the sorted prices in descending order.
     */
    @Test
    public void testProductSortHilo(){
        // Find the select element for product sorting
        WebElement productSortContainer = driver.findElement(By.xpath("//select[@class='product_sort_container']"));

        // Create a Select object to interact with the dropdown
        Select select = new Select(productSortContainer);
        // Select the option to sort products from high to low price
        select.selectByValue("hilo");

        // Get all the product prices
        List<Float> originalPrices = getProductPrices(driver.findElements(By.className("inventory_item_price")));

        // Create a copy of the original list and sort it in descending order
        List<Float> sortedPrices = new ArrayList<>(originalPrices);
        Collections.sort(sortedPrices, Collections.reverseOrder());

        // Verify that the original prices match the sorted prices
        Assert.assertEquals(originalPrices, sortedPrices);
    }


    /**
     * Test case to verify product sorting functionality from low to high price.
     *
     * This test selects the 'lohi' option in the product sorting dropdown,
     * retrieves all product prices, sorts them in ascending order, and
     * verifies that the prices are sorted correctly.
     *
     * @throws NoSuchElementException if the product sorting dropdown element or any product item element is not found.
     * @throws NumberFormatException if the price text of any product item cannot be parsed as a float.
     * @throws AssertionError if the original prices do not match the sorted prices in ascending order.
     */
    @Test
    public void testProductSortLiho(){
        // Find the select element for product sorting
        WebElement productSortContainer = driver.findElement(By.xpath("//select[@class='product_sort_container']"));

        // Create a Select object to interact with the dropdown
        Select select = new Select(productSortContainer);
        // Select the option to sort products from low to high price
        select.selectByValue("lohi");

        // Get all the product prices
        List<Float> originalPrices = getProductPrices(driver.findElements(By.className("inventory_item_price")));

        // Create a copy of the original list and sort it
        List<Float> sortedPrices = new ArrayList<>(originalPrices);
        Collections.sort(sortedPrices);

        // Verify that the original prices match the sorted prices
        Assert.assertEquals(originalPrices, sortedPrices);
    }


    /**
     * Teardown method to close the WebDriver after all tests have been executed.
     *
     * This method is annotated with @After, indicating that it will be executed after each test method.
     * It quits the WebDriver to release system resources.
     */
    @After
    public void tearDown(){
        // quitting the WebDriver
        driver.quit();
    }


    /**
     * Helper method to extract and parse product prices from WebElement objects.
     *
     * This method takes a list of WebElement objects representing product price elements,
     * extracts the text content from each element, parses it as a float value after removing
     * the dollar sign ('$'), and adds it to a list of float prices. It then returns the
     * list of parsed prices.
     *
     * @param productPriceElements List of WebElement objects representing product price elements.
     * @return List of float prices parsed from the text content of the product price elements.
     * @throws NumberFormatException if the price text of any product item cannot be parsed as a float.
     */
    private List<Float> getProductPrices(List<WebElement> productPriceElements) {
        List<Float> prices = new ArrayList<>();
        for (WebElement productPriceElement : productPriceElements) {
            String textPrice = productPriceElement.getText().replace("$", "");
            prices.add(Float.parseFloat(textPrice));
        }
        return prices;
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

}
