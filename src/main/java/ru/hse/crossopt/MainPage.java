package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class MainPage {
    private WebDriver driver;

    @FindBy(xpath = "/html/body/div[1]/div[1]/div/a[2]/span")
    private WebElement listButton;

    public MainPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public IssueListPage clickIssues() {
        var wait = new WebDriverWait(driver, 6);
        wait.until(driver -> listButton.isDisplayed());
        listButton.click();
        return new IssueListPage(driver);
    }
}
