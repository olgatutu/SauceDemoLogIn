package SwagLabsTesting;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.Assertions;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.By;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;
import org.openqa.selenium.support.ui.WebDriverWait;

import java.util.concurrent.TimeUnit;

public class LogIn{


    WebDriver driver;
    WebDriverWait wait;

    By logInButton = By.cssSelector("#login-button");
    By errorButton = By.cssSelector("[data-test='error']");
    By usernameField = By.cssSelector("#user-name");
    By passwordField = By.cssSelector("#password");

    String correctPassword = "secret_sauce";
    String correctUsername = "standard_user";
    String inCorrectPassword = "sauce";
    String inCorrectUsername = "user";

    String[] correctUsernames = {"standard_user", "locked_out_user", "problem_user", "performance_glitch_user"};



    @BeforeEach
    public void driverSetup () {
        System.setProperty("webdriver.chrome.driver", "C:\\chromedriver\\chromedriver.exe");
        driver = new ChromeDriver();

        driver.manage().window().maximize();

        driver.manage().timeouts().pageLoadTimeout(10, TimeUnit.SECONDS);
        wait = new WebDriverWait(driver, 10);

        driver.navigate().to("https://www.saucedemo.com/");

    }

    @Test
    public void noUsernameNoPasswordTest () {
        driver.findElement(logInButton).click();
        Assertions.assertEquals("Epic sadface: Username is required", driver.findElement(errorButton).getText(), "Message is not correct");
    }

    @Test
    public void noUsernameAndPasswordTest () {
        driver.findElement(passwordField).sendKeys(correctPassword);
        driver.findElement(logInButton).click();
        Assertions.assertEquals("Epic sadface: Username is required", driver.findElement(errorButton).getText(), "Message is not correct");
    }

    @Test
    public void usernameNoPasswordTest () {
        driver.findElement(usernameField).sendKeys(correctUsername);
        driver.findElement(logInButton).click();
        Assertions.assertEquals("Epic sadface: Password is required", driver.findElement(errorButton).getText(), "Message is not correct");
    }

    @Test
    public void usernameAndPassword () {
        driver.findElement(usernameField).sendKeys(correctUsername);
        driver.findElement(passwordField).sendKeys(correctPassword);
        driver.findElement(logInButton).click();
        Assertions.assertEquals("https://www.saucedemo.com/inventory.html", driver.getCurrentUrl(), "Redirection is wrong.");
    }

    @Test
    public void correctUsernameincorrectPassword () {
        driver.findElement(usernameField).sendKeys(correctUsername);
        driver.findElement(passwordField).sendKeys(inCorrectPassword);
        driver.findElement(logInButton).click();
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in this service", driver.findElement(errorButton).getText(),
                "Message is not correct");
    }

    @Test
    public void incorrectUsernameCorrectPassword () {
        driver.findElement(usernameField).sendKeys(inCorrectUsername);
        driver.findElement(passwordField).sendKeys(correctPassword);
        driver.findElement(logInButton).click();
        Assertions.assertEquals("Epic sadface: Username and password do not match any user in this service", driver.findElement(errorButton).getText(),
              "Message is not correct");
    }



    @AfterEach
    public void driverQuit () {
        driver.quit();
    }

}
