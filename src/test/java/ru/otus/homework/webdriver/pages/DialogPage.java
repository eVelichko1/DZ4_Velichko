package ru.otus.homework.webdriver.pages;

import org.openqa.selenium.By;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.ExpectedConditions;

public class DialogPage extends BasePage<DialogPage> {

    private static final By OPEN_MODAL = By.id("open-modal");
    private static final By MODAL_TITLE = By.id("modal-title");

    public DialogPage(WebDriver driver) {
        super(driver);
    }

    @Override
    protected String path() {
        return "/dialog";
    }

    @Override
    protected void waitForPage() {
        wait.until(ExpectedConditions.visibilityOfElementLocated(OPEN_MODAL));
    }

    @Override
    protected DialogPage self() {
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
}
