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

public class SearchChargerSteps {
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

  // Scenario: Display available charging stations
  /*
  Given the user has opened the app,
  When they navigate to the search stations feature,
  And they enter their current location or a destination,
  Then a list of available charging stations in that area should be displayed.
   */
  @Given("the user has opened the app")
  public void the_user_has_opened_the_app() {
    driver.get("http://localhost:5173/");
  }

  @When("they navigate to the search stations feature")
  public void they_navigate_to_the_search_stations_feature() {
    WebElement driverButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("driver-button")));
    driverButton.click();

    WebElement selectDriverButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("view-profile-button-3")));
    selectDriverButton.click();

    WebElement findStationsButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("find-stations-button")));
    findStationsButton.click();
  }

  @When("they enter their current location or a destination")
  public void they_enter_their_current_location_or_a_destination() {
    WebElement cityRadio = wait.until(ExpectedConditions.elementToBeClickable(By.id("city-radio")));
    cityRadio.click();

    WebElement cityInput = wait.until(ExpectedConditions.elementToBeClickable(By.id("city-input")));
    cityInput.sendKeys("Lisbon");

    WebElement searchButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("search-by-city-btn")));
    searchButton.click();
  }

  @Then("a list of available charging stations in that area should be displayed")
  public void a_list_of_available_charging_stations_in_that_area_should_be_displayed() {
    WebElement stationsList =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("station-results-list")));
    assertNotNull(stationsList);
    assertTrue(stationsList.isDisplayed());

    assertFalse(stationsList.findElements(By.tagName("li")).isEmpty());
  }
}
