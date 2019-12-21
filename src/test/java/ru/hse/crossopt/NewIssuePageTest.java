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

        try {
            var mainPage = new LoginPage(driver).login("root", "root");
            Thread.sleep(1000);
            listPage = mainPage.clickIssues();
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
    }

    @AfterEach
    void tearDown() {
        driver.quit();
    }

    void testCreateCorrect(String summary, String description) throws InterruptedException {
        var newIssuePage = listPage.clickNewIssue();
        Thread.sleep(1000);
        assertDoesNotThrow(() -> newIssuePage.newIssue(summary, description));
        Thread.sleep(1000);
        assertTrue(newIssuePage.successMatches("was reported"));
    }

    void testCreateError(String summary, String description) throws InterruptedException {
        var newIssuePage = listPage.clickNewIssue();
        Thread.sleep(1000);
        assertDoesNotThrow(() -> newIssuePage.newIssue(summary, description));
        Thread.sleep(1000);
        assertTrue(newIssuePage.errorMatches("Summary is required"));
    }

    String generateString(int length) {
        char[] string = new char[length];
        for (int i = 0; i < length; i++) {
            string[i] = (char)('a' + random.nextInt(26));
        }
        return new String(string);
    }

    @Test
    void testCreateSimpleIssue() throws InterruptedException {
        testCreateCorrect("Summary", "Description");
    }

    @Test
    void testCreateEmptyIssue() throws InterruptedException {
        testCreateError("", "");
    }

    @Test
    void testCreateEmptySummaryIssue() throws InterruptedException {
        testCreateError("", "Description");
    }

    @Test
    void testCreateEmptyDescriptionIssue() throws InterruptedException {
        testCreateCorrect("Summary", "");
    }

    @Test
    void testCreateBlankSpacesSummaryAndDescriptionIssue() throws InterruptedException {
        testCreateCorrect("                  ", "                           ");
    }

    @Test
    void testCreateSeveralIssues() throws InterruptedException {
        testCreateCorrect("Summary 1", "Description 1");
        testCreateCorrect("Summary 2", "Description 2");
        testCreateCorrect("Summary 2", "Description 2");
        testCreateCorrect("Summary", "Another Description");
        testCreateError("", "Description");
    }

    @Test
    void testCreateLongDescriptionIssue() throws InterruptedException {
        var summary = generateString(1000);
        var description = generateString(10000);
        testCreateCorrect(summary, description);
    }

    @Test
    void testCreateLongSummaryIssue() throws InterruptedException {
        var summary = generateString(1000);
        var description = generateString(100);
        testCreateCorrect(summary, description);
    }

    @Test
    void testCreateSpecialSymbolSummaryIssue() throws InterruptedException {
        testCreateCorrect("!@^(!)@*$^)@^!B)efoasb", "Something broke down.");
    }

    @Test
    void testCreateSpecialSymbolDescriptionIssue() throws InterruptedException {
        testCreateCorrect("Short summary", "Курица \u3527");
    }

    @Test
    void testCreateMultilineSummaryIssue() throws InterruptedException {
        testCreateCorrect("Summary.\nSome summary details.", "A description");
    }

    @Test
    void testCreateMultilineDescriptionIssue() throws InterruptedException {
        testCreateCorrect("Summary", "Description.\nSome elaboration on the description.\n\nUseless comment.");
    }
}