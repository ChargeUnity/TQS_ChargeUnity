package tqs.ChargeUnity.functional.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class RegisterStationSteps {
    private WebDriver driver;
    private WebDriverWait wait;

    @Before
    public void setUp() {
        WebDriverSingleton.initialize();
        driver = WebDriverSingleton.getDriver();
        wait = WebDriverSingleton.getWait();
    }

    @After
    public void cleanUp() {
        WebDriverSingleton.quit();
    }

    // Scenario: Register a new charging station
    /*
        Given I am on the station page
        When I navigate to the register station feature
        And I enter valid details and submit
        Then the new station is added to the system
    */
    @Given("I am on the station page")
    public void i_am_on_the_station_page() {
        driver.get("http://localhost:5173/operators/1");
    }

    @When("I navigate to the register station feature")
    public void i_navigate_to_the_register_station_feature() {
        WebElement registerStationButton =
                wait.until(ExpectedConditions.elementToBeClickable(By.id("register-new-station-button")));
        registerStationButton.click();
    }

    @When("I enter valid details and submit")
    public void i_enter_valid_details_and_submit() {
        WebElement nameInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("station-name-input")));
        nameInput.sendKeys("UA Station");

        WebElement cityInput = driver.findElement(By.id("station-city-input"));
        cityInput.sendKeys("Aveiro");

        WebElement addressInput = driver.findElement(By.id("station-address-input"));
        addressInput.sendKeys("Rua da Universidade de Aveiro");

        WebElement latitudeInput = driver.findElement(By.id("station-latitude-input"));
        latitudeInput.sendKeys("40.633132");

        WebElement longitudeInput = driver.findElement(By.id("station-longitude-input"));
        longitudeInput.sendKeys("-8.659052");

        WebElement submitButton = driver.findElement(By.id("create-station-button"));
        submitButton.click();
    }

    @Then("the new station is added to the system")
    public void the_new_station_is_added_to_the_system() {
        WebElement stationList = wait.until(
            ExpectedConditions.presenceOfElementLocated(By.className("station-list"))
        );

        boolean found = stationList.getText().contains("UA Station");
        assertTrue(found, "New station 'UA Station' should be present in the station list");
    }

}
