package tqs.ChargeUnity.functional.steps;

import static org.junit.jupiter.api.Assertions.*;

import org.openqa.selenium.Alert;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.Select;
import org.openqa.selenium.support.ui.WebDriverWait;

import io.cucumber.java.After;
import io.cucumber.java.Before;
import io.cucumber.java.en.Given;
import io.cucumber.java.en.Then;
import io.cucumber.java.en.When;

public class BookTimesSlotsSteps {
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

  // Scenario: Book a time slot in a charging station
  /*
  Given the user has selected a charging station,
  When they select an available time slot,
  And they submit the reservation,  
  Then the reservation should be confirmed and shown in the user’s bookings panel.
   */
  @Given("the user has selected a charging station,")
  public void the_user_has_opened_the_app() {
    // Navigate directly to the chargers page of a specific station
    driver.get("http://localhost:5173/driver/3/stations/1/chargers/1/book");
  }

  @When("they select a time slot,")
  public void they_navigate_to_the_search_stations_feature() {
    WebElement startDateInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start-date-input")));
    startDateInput.sendKeys("2025-06-05");

    WebElement startTimeInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start-time-input")));
    Select select = new Select(startTimeInput);
	select.selectByValue("10:00");

    WebElement endDateInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("end-date-input")));
    endDateInput.sendKeys("2025-06-05");

    WebElement endTimeInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("end-time-input")));
	Select selectEnd = new Select(endTimeInput);
	selectEnd.selectByValue("11:00");
  }


  @When("they submit the reservation,")
  public void they_submit_the_reservation() {
    WebElement bookButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
    bookButton.click();
  }

  @Then("the reservation should be confirmed and shown in the user’s bookings panel.")
  public void the_reservation_should_be_confirmed_and_shown_in_the_user_s_bookings_panel() {
    WebElement confirmationMessage =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("booking-confirm-message")));
    assertNotNull(confirmationMessage);
    assertTrue(confirmationMessage.isDisplayed());
    assertEquals("Booking Confirmed!", confirmationMessage.getText());
  }

  // Scenario: Faild to book a time slot because of overlaping
  /*
  Given the user already has a reservation at the same time,
  When they try to book another slot,
  Then the system should prevent duplicate bookings.
  */
  @Given("the user already has a reservation at the same time,")
  public void the_user_already_has_a_reservation_at_the_same_time() {
    // Navigate directly to the chargers page of a specific station
    driver.get("http://localhost:5173/driver/3/stations/1/chargers/1/book");
  }

  @When("they try to book another slot,")
  public void they_try_to_book_another_slot() {
    WebElement startDateInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start-date-input")));
    startDateInput.sendKeys("2025-06-05");

    WebElement startTimeInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("start-time-input")));
    Select select = new Select(startTimeInput);
    select.selectByValue("10:00");

    WebElement endDateInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("end-date-input")));
    endDateInput.sendKeys("2025-06-05");

    WebElement endTimeInput =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("end-time-input")));
    Select selectEnd = new Select(endTimeInput);
    selectEnd.selectByValue("11:00");

    WebElement bookButton =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("submit-btn")));
    bookButton.click();
  }

  @Then("the system should prevent duplicate bookings.")
  public void the_system_should_prevent_duplicate_bookings() {
    // cath the alert message
    wait.until(ExpectedConditions.alertIsPresent());
    Alert alert = driver.switchTo().alert();
    assertNotNull(alert);
    assertEquals("You already have a booking at this time.", alert.getText());
    alert.accept();
  }
}
