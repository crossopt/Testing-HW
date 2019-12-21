package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class NewIssuePage {
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
    }

    public void newIssue(String issueSummary, String issueDescription) {
        summary.sendKeys(issueSummary);
        description.sendKeys(issueDescription);
        submitIssue.click();
    }

    public boolean successMatches(String successMessage) {
        return successPopup.isDisplayed() && successPopup.getText().contains(successMessage);
    }

    public boolean errorMatches(String errorMessage) {
        return errorPopup.isDisplayed() && errorPopup.getText().contains(errorMessage);
    }
}
