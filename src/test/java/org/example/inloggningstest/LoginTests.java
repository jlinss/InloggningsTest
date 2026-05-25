package org.example.inloggningstest;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.edge.EdgeDriver;
import org.openqa.selenium.edge.EdgeOptions;

import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests {

    WebDriver driver;
    LoginPage loginPage;


    @BeforeEach
    public void setup() {

        EdgeOptions options = new EdgeOptions();

        options.addArguments("--headless=new");
        options.addArguments("--no-sandbox");
        options.addArguments("--disable-dev-shm-usage");
        options.addArguments("--remote-allow-origins=*");

        driver = new EdgeDriver(options);

        driver.get("https://www.saucedemo.com/");

        loginPage = new LoginPage(driver);

//        driver = new EdgeDriver();
//        driver.manage().window().maximize();
//        driver.get("https://www.saucedemo.com/");
//        loginPage = new LoginPage(driver);

    }

    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void successfulLoginTest() {
        //test1
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        try {
            Thread.sleep(2000);
        } catch (InterruptedException e) {
        }

        String currentUrl = driver.getCurrentUrl();
        String title = driver.getTitle();

        assert currentUrl != null;
        assertTrue(currentUrl.contains("inventory.html") || Objects.requireNonNull(title).contains("inventory.html"));
    }

    @Test
    public void failLoginTest() {
        //test2
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLoginButton();

        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }
}
