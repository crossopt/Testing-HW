package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class NewIssuePage {
    private final WebDriver driver;

    @FindBy(className = "edit-summary")
    private WebElement summary;

    @FindBy(className = "edit-description")
    private WebElement description;

    @FindBy(className = "submit-btn")
    private WebElement submitIssue;

    @FindBy(className = "errorSeverity")
    private WebElement errorPopup;

    @FindBy(className = "message")
    private WebElement successPopup;

    public NewIssuePage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public void newIssue(String issueSummary, String issueDescription) {
        var wait = new WebDriverWait(driver, 5);
        wait.until(driver -> submitIssue.isDisplayed());
        summary.sendKeys(issueSummary);
        description.sendKeys(issueDescription);
        submitIssue.click();
    }

    public boolean successMatches(String successMessage) {
        var wait = new WebDriverWait(driver, 5);
        wait.until(driver -> successPopup.isDisplayed());
        return successPopup.isDisplayed() && successPopup.getText().contains(successMessage);
    }

    public boolean errorMatches(String errorMessage) {
        var wait = new WebDriverWait(driver, 5);
        wait.until(driver -> errorPopup.isDisplayed());
        return errorPopup.isDisplayed() && errorPopup.getText().contains(errorMessage);
    }
}
