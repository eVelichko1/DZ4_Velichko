package ru.otus.homework.webdriver;

import static org.assertj.core.api.Assertions.assertThat;

import java.io.IOException;
import java.nio.file.Files;
import java.nio.file.Path;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.function.Consumer;
import org.apache.logging.log4j.LogManager;
import org.apache.logging.log4j.Logger;
import org.junit.jupiter.api.DisplayName;
import org.junit.jupiter.api.Test;
import org.openqa.selenium.OutputType;
import org.openqa.selenium.TakesScreenshot;
import org.openqa.selenium.WebDriver;
import ru.otus.homework.webdriver.driver.ChromeDriverFactory;
import ru.otus.homework.webdriver.driver.ChromeLaunchMode;
import ru.otus.homework.webdriver.pages.DialogPage;
import ru.otus.homework.webdriver.pages.InputsPage;
import ru.otus.homework.webdriver.pages.LoginPage;

class ChromeModesTest {

    private static final Logger log = LogManager.getLogger(ChromeModesTest.class);
    private static final String NUMBER = "42";
    private static final String USERNAME = "tomsmith";
    private static final String PASSWORD = "SuperSecretPassword!";

    @Test
    @DisplayName("Headless: в поле ввода значение 42")
    void numberInputMatchesEnteredValueInHeadlessChrome() {
        withChrome(ChromeLaunchMode.HEADLESS, driver -> {
            InputsPage page = new InputsPage(driver);
            log.info("Headless: открываю {}", page.url());
            page.open();
            page.enterNumber(NUMBER);

            String actual = page.valueFromPage();
            log.info("Значение поля, прочитанное через JavaScript: {}", actual);
            assertThat(actual).isEqualTo(NUMBER);
        });
    }

    @Test
    @DisplayName("Киоск: по кнопке Open modal dialog открывается модальное окно")
    void modalOpensInKioskMode() {
        withChrome(ChromeLaunchMode.KIOSK, driver -> {
            DialogPage page = new DialogPage(driver);
            log.info("Киоск: открываю {}", page.url());
            page.open();

            assertThat(page.openModalButtonLabel()).isEqualTo("Open modal dialog");
            assertThat(page.isModalOpen()).isFalse();
            page.openModal();

            log.info("Модальное окно открыто: {}, заголовок: {}", page.isModalOpen(), page.modalTitle());
            assertThat(page.isModalOpen()).isTrue();
            assertThat(page.modalTitle()).isEqualTo("Modal dialog");
        });
    }

    @Test
    @DisplayName("Полный экран: после входа видно Logged in as tomsmith")
    void secureAreaShowsLoggedInUserInFullscreen() {
        withChrome(ChromeLaunchMode.FULL_SCREEN, driver -> {
            LoginPage page = new LoginPage(driver);
            log.info("Полный экран: открываю {} и вхожу как {}", page.url(), USERNAME);
            page.open();

            assertThat(page.submitButtonLabel()).isEqualTo("Login");
            page.login(USERNAME, PASSWORD);

            String actual = page.loggedInAs();
            log.info("Текст после входа, прочитанный через JavaScript: {}", actual);
            assertThat(actual).isEqualTo("Logged in as " + USERNAME);
        });
    }

    private static void withChrome(ChromeLaunchMode mode, Consumer<WebDriver> scenario) {
        WebDriver driver = ChromeDriverFactory.create(mode);
        try {
            scenario.accept(driver);
        } catch (RuntimeException | AssertionError error) {
            saveScreenshot(driver, mode);
            throw error;
        } finally {
            driver.quit();
        }
    }

    private static void saveScreenshot(WebDriver driver, ChromeLaunchMode mode) {
        if (!(driver instanceof TakesScreenshot screenshot)) {
            return;
        }
        try {
            Path directory = Path.of("target", "screenshots");
            Files.createDirectories(directory);
            String stamp = LocalDateTime.now().format(DateTimeFormatter.ofPattern("yyyyMMdd-HHmmss"));
            Path file = directory.resolve(mode.name().toLowerCase() + "-" + stamp + ".png");
            Files.write(file, screenshot.getScreenshotAs(OutputType.BYTES));
        } catch (IOException | RuntimeException ignored) {
            // Снимок нужен только для разбора падения и не должен скрывать исходную ошибку.
        }
    }
}
