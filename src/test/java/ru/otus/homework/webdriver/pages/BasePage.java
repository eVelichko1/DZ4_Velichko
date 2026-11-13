package ru.otus.homework.webdriver.pages;

import java.time.Duration;
import org.openqa.selenium.JavascriptExecutor;
import org.openqa.selenium.WebDriver;
import org.openqa.selenium.WebElement;
import org.openqa.selenium.support.ui.WebDriverWait;

/**
 * Общие действия страниц стенда. Хост один на все страницы и подменяется
 * свойством {@code base.url}, путь и ожидание готовности задаёт конкретная страница.
 */
public abstract class BasePage<T extends BasePage<T>> {

    public static final String DEFAULT_HOST = "https://the-internet.herokuapp.com";
    private static final String HOST_PROPERTY = "base.url";

    protected final WebDriver driver;
    protected final WebDriverWait wait;

    protected BasePage(WebDriver driver) {
        this.driver = driver;
        this.wait = new WebDriverWait(driver, Duration.ofSeconds(15));
    }

    protected abstract String path();

    protected abstract void waitForPage();

    protected abstract T self();

    public T open() {
        driver.get(url());
        waitForPage();
        return self();
    }

    public String url() {
        return host() + path();
    }

    protected String host() {
        String configured = System.getProperty(HOST_PROPERTY, DEFAULT_HOST).trim();
        if (configured.endsWith("/")) {
            return configured.substring(0, configured.length() - 1);
        }
        return configured;
    }

    protected void scrollIntoView(WebElement element) {
        if (driver instanceof JavascriptExecutor executor) {
            executor.executeScript(
                    "arguments[0].scrollIntoView({block: 'center', inline: 'center'});",
                    element
            );
        }
    }
}
