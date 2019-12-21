package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;

public class IssueListPage {
    private WebDriver driver;

    @FindBy(className = "yt-header__create-btn")
    private WebElement newButton;

    public IssueListPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public NewIssuePage clickNewIssue() {
        newButton.click();
        return new NewIssuePage(driver);
    }
}
