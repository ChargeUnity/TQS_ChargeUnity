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

public class ChargeSteps {

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

  // Scenario: Access driver page
  @Given("I am in the home page")
  public void i_am_in_the_home_page() {
    driver.get("http://localhost:5173/");
  }

  @When("I click on {string}")
  public void i_click_on(String button) {
    WebElement element =
        wait.until(
            ExpectedConditions.elementToBeClickable(By.id(button.toLowerCase() + "-button")));
    element.click();
  }

  @When("I click on Driver {string}")
  public void i_click_on_driver(String string) {
    WebElement driverLink =
        wait.until(ExpectedConditions.elementToBeClickable(By.id("view-profile-button-" + string)));
    driverLink.click();
  }

  @Then("I should see the {string} page")
  public void i_should_see_the_page(String expectedPageName) {
    WebElement pageTitle =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("driver-name")));
    assertEquals("Hello, " + expectedPageName + "!", pageTitle.getText());
  }

  // Scenario: Start charging session
  @Given("I am on the Driver {string} page")
  public void i_am_in_the_driver_page(String driverName) {
    // jump to this page as we tested it previously
    driver.get("http://localhost:5173/driver/3");

    WebElement driverPage =
        wait.until(ExpectedConditions.visibilityOfElementLocated(By.id("driver-name")));
    assertEquals("Hello, " + driverName + "!", driverPage.getText());
  }

  @When("I click on {string} button")
  public void i_click_on_button(String buttonName) {
    WebElement button =
        wait.until(
            ExpectedConditions.elementToBeClickable(By.id(buttonName.toLowerCase() + "-button")));
    button.click();
  }

  @When("I click on {string} in the {string}")
  public void i_click_on_string_in_the_button(String buttonText, String buttonId) {
    WebElement button = wait.until(ExpectedConditions.elementToBeClickable(By.id(buttonId)));
    assertEquals(buttonText, button.getText());
    button.click();
  }

  @Then("I should see {string} in the {string}")
  public void i_should_see_in_button(String expectedText, String buttonId) {
    WebElement button = wait.until(ExpectedConditions.visibilityOfElementLocated(By.id(buttonId)));
    assertEquals(expectedText, button.getText());
  }
}
