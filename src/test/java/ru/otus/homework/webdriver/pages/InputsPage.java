package ru.otus.homework.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class InputsPage extends BasePage<InputsPage> {

    private static final By NUMBER = By.cssSelector("input[type='number']");

    public InputsPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String path() {
        return "/inputs";
    }

    @Override
    protected void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(NUMBER));
    }

    @Override
    protected InputsPage self() {
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
