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
}
