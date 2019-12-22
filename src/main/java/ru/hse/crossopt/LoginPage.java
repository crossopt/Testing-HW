package ru.hse.crossopt;

import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.FindBy;
import org.openqa.selenium.support.PageFactory;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {
    private WebDriver driver;

    @FindBy(id = "id_l.L.login")
    private WebElement login;

    @FindBy(id = "id_l.L.password")
    private WebElement password;

    @FindBy(id = "id_l.L.loginButton")
    private WebElement loginButton;

    public LoginPage(WebDriver driver) {
        PageFactory.initElements(driver, this);
        this.driver = driver;
    }

    public MainPage login(String userLogin, String userPassword) {
        var wait = new WebDriverWait(driver, 10);
        wait.until(driver -> loginButton.isDisplayed());
        login.sendKeys(userLogin);
        password.sendKeys(userPassword);
        loginButton.click();
        return new MainPage(driver);
    }
}