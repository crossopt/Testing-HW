package ru.hse.crossopt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import static org.junit.jupiter.api.Assertions.*;


/** Tests that check whether logging in works correctly and further operations can be performed. */
class LoginPageTest {
    private static WebDriver driver;

    @BeforeAll
    static void startDriver() {
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("localhost:8080");
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    @Test
    void testCorrectLogin() throws InterruptedException {
        var loginPage = new LoginPage(driver);
        var mainPage = loginPage.login("root", "root");
        Thread.sleep(1000);
        assertDoesNotThrow(mainPage::clickIssues);
    }

    @Test
    void testWrongPasswordLogin() throws InterruptedException {
        var loginPage = new LoginPage(driver);
        var mainPage = loginPage.login("root", "not-root");
        Thread.sleep(1000);
        assertThrows(Exception.class, mainPage::clickIssues);
    }

    @Test
    void testInvalidUserLogin() throws InterruptedException {
        var loginPage = new LoginPage(driver);
        var mainPage = loginPage.login("not-a-user", "root");
        Thread.sleep(1000);
        assertThrows(Exception.class, mainPage::clickIssues);
    }
}