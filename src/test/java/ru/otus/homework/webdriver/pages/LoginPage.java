package ru.otus.homework.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class LoginPage extends BasePage<LoginPage> {

    private static final By USERNAME = By.id("username");
    private static final By PASSWORD = By.id("password");
    private static final By SUBMIT = By.cssSelector("#login button[type='submit']");
    private static final By LOGGED_IN = By.xpath("//p[span[@id='username']]");

    public LoginPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String path() {
        return "/login";
    }

    @Override
    protected void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(USERNAME));
    }

    @Override
    protected LoginPage self() {
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
        wait.until(ExpectedConditions.visibilityOfElementLocated(LOGGED_IN));
        Object text = ((JavascriptExecutor) driver).executeScript(
                "return document.querySelector('p span#username').parentElement.innerText;"
        );
        return text == null ? "" : text.toString().trim();
    }
}
