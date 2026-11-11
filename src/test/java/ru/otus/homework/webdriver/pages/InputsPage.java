package ru.otus.homework.webdriver.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница https://the-internet.herokuapp.com/inputs
 */
public class InputsPage {

    public static final String URL = "https://the-internet.herokuapp.com/inputs";

    private static final By NUMBER = By.cssSelector("input[type='number']");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public InputsPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public InputsPage open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(NUMBER));
        return this;
    }

    public void enterNumber(String value) {
        WebElement input = wait.until(ExpectedConditions.elementToBeClickable(NUMBER));
        input.clear();
        input.sendKeys(value);
    }

    public String valueFromPage() {
        Object value = ((JavascriptExecutor) driver).executeScript(
                "return document.querySelector(\"input[type='number']\").value;"
        );
        return value == null ? "" : value.toString();
    }
}
