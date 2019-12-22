package ru.hse.crossopt;

import org.junit.jupiter.api.AfterEach;
import org.junit.jupiter.api.BeforeAll;
import org.junit.jupiter.api.BeforeEach;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.chrome.ChromeDriver;

import java.util.Random;

import static org.junit.jupiter.api.Assertions.*;

/**
 * Tests that check whether creating new issues works correctly.
 * Also check that the necessary success/error message is displayed.
 * */
class NewIssuePageTest {
    private static WebDriver driver;
    private static IssueListPage listPage;
    private static Random random;

    @BeforeAll
    static void startDriver() {
        random = new Random(179);
        System.setProperty("webdriver.chrome.driver", "src/test/resources/chromedriver");
    }

    @BeforeEach
    void setUp() {
        driver = new ChromeDriver();
        driver.get("localhost:8080");

        var mainPage = new LoginPage(driver).login("root", "root");
        listPage = mainPage.clickIssues();
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    void testCreateCorrect(String summary, String description) {
        var newIssuePage = listPage.clickNewIssue();
        assertDoesNotThrow(() -> newIssuePage.newIssue(summary, description));
        assertTrue(newIssuePage.successMatches("was reported"));
        var expectedDescription = description.equals("") ? "No description" : description;
        assertTrue(listPage.checkLastIssue(summary, expectedDescription));
    }

    void testCreateError(String summary, String description) {
        var newIssuePage = listPage.clickNewIssue();
        assertDoesNotThrow(() -> newIssuePage.newIssue(summary, description));
        assertTrue(newIssuePage.errorMatches("Summary is required"));
        assertDoesNotThrow(newIssuePage::clickIssues);
    }

    String generateString(int length) {
        char[] string = new char[length];
        for (int i = 0; i < length; i++) {
            string[i] = (char)('a' + random.nextInt(26));
        }
        return new String(string);
    }

    @Test
    void testCreateEmptyDescriptionIssue() {
        testCreateCorrect("Summary", "");
    }

    @Test
    void testCreateSimpleIssue() {
        testCreateCorrect("Summary", "Description");
    }

    @Test
    void testCreateEmptyIssue() {
        testCreateError("", "");
    }

    @Test
    void testCreateEmptySummaryIssue() {
        testCreateError("", "No description");
        testCreateCorrect("Summary", "");
    }

    @Test
    void testCreateIssueWithEmptyDescriptionText() {
        testCreateCorrect("This description is the one that shows up for an empty description field.", "No description");
    }

    @Test
    void testCreateEndSpacesInDescriptionIssue() {
        testCreateCorrect("Summary", "A Description       ");
    }

    @Test
    void testCreateLeadingSpacesInDescriptionIssue() {
        testCreateCorrect("Summary", "    A Description");
    }

    @Test
    void testCreateLongDescriptionIssue() {
        var summary = generateString(1000);
        var description = generateString(2000);
        testCreateCorrect(summary, description);
    }

    @Test
    void testCreateLongSummaryIssue() {
        var summary = generateString(1000);
        var description = generateString(100);
        testCreateCorrect(summary, description);
    }

    @Test
    void testCreateSpecialSymbolSummaryIssue() {
        testCreateCorrect("!@^(!)@*$^)@^!B)efoasb", "Something broke down.");
    }

    @Test
    void testCreateSpecialSymbolDescriptionIssue() {
        testCreateCorrect("Short summary", "Курица \u3527");
    }

    @Test
    void testCreateMultilineDescriptionIssue() {
        testCreateCorrect("Summary", "Description.\nSome elaboration on the description.\n\nUseless comment.");
    }
}