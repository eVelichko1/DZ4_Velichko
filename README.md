# WebDriver

Сайт [https://the-internet.herokuapp.com](https://the-internet.herokuapp.com).

## Сценарии

1. **Headless** (`--headless=new`). На странице [/inputs](https://the-internet.herokuapp.com/inputs) в числовое поле вводится `42`, затем проверяется, что значение поля совпадает с введённым.
2. **Киоск** (`--kiosk`). На странице [/dialog](https://the-internet.herokuapp.com/dialog) нажимается «Open modal dialog», затем проверяется, что модальное окно стало видимым.
3. **Полный экран** (`--start-fullscreen`). На странице [/login](https://the-internet.herokuapp.com/login) вводятся имя `tomsmith` и пароль `SuperSecretPassword!`, форма отправляется кнопкой Login. На защищённой странице ожидается текст `Logged in as tomsmith`.

Хост стенда задаётся один раз в `BasePage` и читается из свойства `base.url`. Страницы хранят только путь. Другой стенд запускается так:

```bash
mvn test -Dbase.url=https://другой-хост
```

`BasePage.open()` открывает адрес и вызывает `waitForPage()` конкретной страницы.

## Структура

- `driver/ChromeDriverFactory` — опции Chrome для трёх режимов
- `pages/BasePage` — хост стенда и общее открытие страницы
- `pages/InputsPage`, `pages/DialogPage`, `pages/LoginPage` — путь, ожидание и действия страницы
- `ChromeModesTest` — три сценария из задания
