package org.example.inloggningstest;

import io.github.bonigarcia.wdm.WebDriverManager;
import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeEach;
import  org.junit.jupiter.api.Test;

import org.openqa.selenium.WebDriver;

import org.openqa.selenium.chrome.ChromeDriver;


import java.util.Objects;

import static org.junit.jupiter.api.Assertions.assertTrue;

public class LoginTests {

    WebDriver driver;
    LoginPage loginPage;

    @BeforeEach
    public void setup() {
        WebDriverManager.chromedriver().setup();

        driver = new ChromeDriver();
        driver.manage().window().maximize();
        driver.get("https://www.saucedemo.com/");
        loginPage = new LoginPage(driver);

    }
    @AfterEach
    public void teardown() {
        driver.quit();
    }

    @Test
    public void successfulLoginTest(){
        //test1
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("secret_sauce");
        loginPage.clickLoginButton();

        try {Thread.sleep(2000);} catch (InterruptedException e) {}

        String currentUrl = driver.getCurrentUrl();
        String title = driver.getTitle();

        assert currentUrl != null;
        assertTrue(currentUrl.contains("inventory.html") || Objects.requireNonNull(title).contains("inventory.html"));
    }

    @Test
    public void failLoginTest(){
        //test2
        loginPage.enterUserName("standard_user");
        loginPage.enterPassword("wrong_password");
        loginPage.clickLoginButton();

        assertTrue(loginPage.getErrorMessage().contains("Username and password do not match"));
    }
}
