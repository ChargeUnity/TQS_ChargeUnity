package tqs.ChargeUnity.functional.steps;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.firefox.FirefoxDriver;
import org.openqa.selenium.firefox.FirefoxOptions;
import org.openqa.selenium.support.ui.WebDriverWait;
import java.time.Duration;

public class WebDriverSingleton {
  private static WebDriver driver;
  private static WebDriverWait wait;

  public static void initialize() {
    if (driver == null) {
      WebDriverManager.firefoxdriver().setup();
      FirefoxOptions options = new FirefoxOptions();
      options.addArguments("--no-sandbox", "--disable-dev-shm-usage");
      driver = new FirefoxDriver(options);
      wait = new WebDriverWait(driver, Duration.ofSeconds(10));
    }
  }

  public static WebDriver getDriver() {
    return driver;
  }

  public static WebDriverWait getWait() {
    return wait;
  }

  public static void quit() {
    if (driver != null) {
      driver.quit();
      driver = null;
      wait = null;
    }
  }
}
