package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class IssueListPage {
    private WebDriver driver;

    @FindBy(className = "yt-header__create-btn")
    private WebElement newButton;

    @FindBy(className = "issueId")
    private WebElement lastIssue;

    @FindBy(id = "id_l.I.ic.icr.it.issSum")
    private WebElement issueSummary;

    @FindBy(id = "id_l.I.ic.icr.d.description")
    private WebElement issueDescription;

    public IssueListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public NewIssuePage clickNewIssue() {
        var wait = new WebDriverWait(driver, 10);
        wait.until(driver -> newButton.isDisplayed());
        newButton.click();
        return new NewIssuePage(driver);
    }

    public boolean checkLastIssue(String summary, String description) {
        var wait = new WebDriverWait(driver, 10);
        wait.until(driver -> lastIssue.isDisplayed());
        lastIssue.click();

        var issueWait = new WebDriverWait(driver, 15);
        issueWait.until(driver -> issueDescription.isDisplayed() && issueSummary.isDisplayed());
        System.out.println("DESCRIPTION" + issueDescription.getText());
        return summary.equals(issueSummary.getText()) && description.equals(issueDescription.getText());
    }
}
