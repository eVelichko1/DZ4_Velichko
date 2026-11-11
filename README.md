# Домашнее задание: WebDriver

Автотесты сайта [https://the-internet.herokuapp.com](https://the-internet.herokuapp.com).
Каждый сценарий открывает Google Chrome со своей опцией запуска и проверяет элемент страницы.

## Сценарии

1. **Headless** (`--headless=new`). На странице [/inputs](https://the-internet.herokuapp.com/inputs) в числовое поле вводится `42`, затем проверяется, что значение поля совпадает с введённым.
2. **Киоск** (`--kiosk`). На странице [/dialog](https://the-internet.herokuapp.com/dialog) нажимается «Open modal dialog», затем проверяется, что модальное окно стало видимым.
3. **Полный экран** (`--start-fullscreen`). На странице [/login](https://the-internet.herokuapp.com/login) вводятся имя `tomsmith` и пароль `SuperSecretPassword!`, форма отправляется кнопкой Login. На защищённой странице ожидается текст `Logged in as tomsmith`.

## Требования

- JDK 21
- Maven 3.8+
- Google Chrome

Драйвер Chrome подбирает Selenium Manager, отдельно ставить ChromeDriver не нужно.

Режимы киоска и полного экрана открывают обычное окно, поэтому им нужен графический дисплей. Headless работает без него. На Linux без рабочего стола тесты можно запустить так:

```bash
xvfb-run -a mvn test
```

## Запуск

```bash
mvn test
```

## Структура

- `driver/ChromeDriverFactory` — опции Chrome для трёх режимов
- `pages/InputsPage`, `pages/DialogPage`, `pages/LoginPage` — действия на страницах стенда
- `ChromeModesTest` — три сценария из задания
