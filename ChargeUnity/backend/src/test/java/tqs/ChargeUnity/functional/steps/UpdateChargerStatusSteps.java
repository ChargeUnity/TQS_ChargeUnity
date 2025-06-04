package tqs.ChargeUnity.functional.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.*;
import org.openqa.selenium.support.ui.*;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.*;

public class UpdateChargerStatusSteps {

  private WebDriver driver;
  private WebDriverWait wait;
  private String selectedChargerId = "";
  private String expectedStatus = "";

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

  @Given("the operator is on the Operator List page")
  public void operator_on_operator_list_page() {
    driver.get("http://localhost:5173/");
    WebElement operatorBtn =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("operator-button")));
    operatorBtn.click();
  }

  @When("they click on the operator {string}")
  public void they_click_on_the_operator(String operatorName) {
    WebElement operatorCard =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("operator-card-" + operatorName.replaceAll("\\s+", "-").toLowerCase())));
    operatorCard.click();
  }

  @And("they select the station {string}")
  public void they_select_the_station(String stationName) {
    WebElement stationElement =
        wait.until(
            ExpectedConditions.elementToBeClickable(
                By.id("station-link-" + stationName.replaceAll("\\s+", "-").toLowerCase())));
    stationElement.click();
  }

  @And("a list of chargers for that station is displayed")
  public void list_of_chargers_is_displayed() {
    WebElement chargerLink =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("edit-charger-link-1")));
    assertTrue(chargerLink.isDisplayed());
  }

  @And("they click on the charger number {string}")
  public void they_click_on_the_charger_number(String number) {
    selectedChargerId = number;
    WebElement chargerLink =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("edit-charger-link-" + number)));
    chargerLink.click();
  }

  @And("they change its status to {string} using the dropdown")
  public void they_change_status_using_dropdown(String newStatus) {
    expectedStatus = newStatus;
    WebElement dropdownElement =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("status-dropdown")));
    Select statusDropdown = new Select(dropdownElement);
    statusDropdown.selectByVisibleText(newStatus);
    WebElement updateButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("update-status-button")));
    updateButton.click();
  }

  @Then("the charger's status should be updated successfully")
  public void charger_status_updated_successfully() {
    WebElement successMessage =
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.cssSelector(".notification-box.notification-success")));
    assertTrue(successMessage.isDisplayed());
  }

  @Then("the updated status should be reflected in the charger list")
  public void updated_status_reflected_in_charger_list() {
    wait.until(ExpectedConditions.urlContains("/chargers"));
    WebElement chargerLink =
        wait.until(
            ExpectedConditions.visibilityOfElementLocated(
                By.id("edit-charger-link-" + selectedChargerId)));
    assertTrue(chargerLink.getText().toUpperCase().contains(expectedStatus.toUpperCase()));
  }
}
