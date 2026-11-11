package ru.otus.homework.webdriver.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

public class LoginPage {

    public static final String URL = "https://the-internet.herokuapp.com/login";

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By SUBMIT = By.cssSelector("#login button[type='submit']");
    private static final By LOGGED_IN = By.xpath("//p[span[@id='username']]");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public LoginPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public LoginPage open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
        return this;
    }

    /**
     * Кнопка отправки формы на английской странице подписана Login.
     */
    public String submitButtonLabel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(SUBMIT))
                .getText()
                .trim();
    }

    public void login(String username, String password) {
        WebElement usernameInput = wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
        usernameInput.clear();
        usernameInput.sendKeys(username);

        WebElement passwordInput = wait.until(ExpectedConditions.visibilityOfElementLocated(PASSWORD));
        passwordInput.clear();
        passwordInput.sendKeys(password);

        WebElement submit = wait.until(ExpectedConditions.elementToBeClickable(SUBMIT));
        scrollIntoView(submit);
        submit.click();
    }

    public String loggedInAs() {
        wait.until(ExpectedConditions.urlContains("/secure"));
        return wait.until(ExpectedConditions.visibilityOfElementLocated(LOGGED_IN)).getText().trim();
    }

    private void scrollIntoView(WebElement element) {
        if (driver instanceof JavascriptExecutor executor) {
            executor.executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                    element
            );
        }
    }
}
