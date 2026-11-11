package ru.otus.homework.webdriver.pages;

import java.time.Duration;
import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Страница https://the-internet.herokuapp.com/dialog
 */
public class DialogPage {

    public static final String URL = "https://the-internet.herokuapp.com/dialog";

    private static final By OPEN_MODAL = By.id("open-modal");
    private static final By MODAL_TITLE = By.id("modal-title");

    private final WebDriver driver;
    private final WebDriverWait wait;

    public DialogPage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    public DialogPage open() {
        driver.get(URL);
        wait.until(ExpectedConditions.visibilityOfElementLocated(OPEN_MODAL));
        return this;
    }

    public String openModalButtonLabel() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(OPEN_MODAL)).getText().trim();
    }

    public boolean isModalOpen() {
        Object open = ((JavascriptExecutor) driver).executeScript(
                "const dialog = document.getElementById('modal-dialog');"
                        + "return dialog !== null && dialog.open === true;"
        );
        return Boolean.TRUE.equals(open);
    }

    public void openModal() {
        WebElement button = wait.until(ExpectedConditions.elementToBeClickable(OPEN_MODAL));
        scrollIntoView(button);
        button.click();
        wait.until(driver -> isModalOpen());
    }

    public String modalTitle() {
        return wait.until(ExpectedConditions.visibilityOfElementLocated(MODAL_TITLE)).getText().trim();
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
