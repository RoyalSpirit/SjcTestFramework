# SJC Test Framework

![Java](https://img.shields.io/badge/Java-21-blue)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A)
![Selenide](https://img.shields.io/badge/Selenide-7.x-43B02A)
![Cucumber](https://img.shields.io/badge/Cucumber-7.x-23D96C)
![RestAssured](https://img.shields.io/badge/RestAssured-5.x-6DB33F)
![Allure](https://img.shields.io/badge/Allure-Report-orange)

SJC Test Framework (Simple Java Cucumber-based Test Framework) is a Java-based test automation framework built around Cucumber, Selenide, RestAssured, JUnit Platform, and Allure.

The current implementation includes readable browser-based UI scenarios and a minimal REST API testing layer using the same BDD approach.

---

[English Version](#english-version) | [Русская версия](#русская-версия)

---

## English Version

### Purpose

This repository is an evolving test automation framework for writing readable BDD scenarios for UI and REST API checks.

[SauceDemo](https://www.saucedemo.com) is used as the example UI target because it is a public practice resource commonly used for UI test automation scenarios.

[JSONPlaceholder](https://jsonplaceholder.typicode.com) is used as the example REST API target because it is a public fake API commonly used for API testing and prototyping.

### Architecture Idea

The framework is built around a simple idea: feature files should describe test intent in readable language, while framework internals hide technical implementation details.

For the current UI layer, the main flow is:

```text
Feature step -> Step definition -> Action executor -> Page object -> Selenide element
```

### DSL Example

UI scenario:

```gherkin
Scenario: Successful login
  * user is on page "Page Swag Labs"
  * user (fills field) "Username" with value "standard_user"
  * user (fills field) "Password" with value "secret_sauce"
  * user (press button) "Login"
  * user is on page "Products"
```

API scenario:

```gherkin
Scenario: Get post by id
  * api (sends GET request) to endpoint "/posts/1"
  * api (checks response status code) equals "200"
  * api (checks response field value) "id" equals "1"
  * api (checks response field is not empty) "title"

Scenario: Get posts by user id
  * api (sends GET request) to endpoint "/posts" with query params:
    | userId | 1 |
  * api (checks response status code) equals "200"
  * api (checks response list is not empty)
  * api (checks response list size) equals "10"
  * api (checks response field is not empty) "[0].title"

Scenario: Create post request with JSON body
  * api (sends POST request) to endpoint "/posts" with JSON body:
    """
    {
      "title": "foo",
      "body": "bar",
      "userId": 1
    }
    """
  * api (checks response status code) equals "201"
  * api (checks response field value) "title" equals "foo"
  * api (checks response field value) "body" equals "bar"
  * api (checks response field value) "userId" equals "1"
  * api (checks response field is not empty) "id"
```

### Features

* Cucumber feature files with readable test steps.
* Page Object structure for UI pages.
* Annotation-based page, action, and element registration.
* Shared actions for common UI operations: filling fields, clicking elements, checking lists, checking text values.
* Selenide-based browser interaction and waits.
* Basic REST API checks with RestAssured.
* Allure integration with screenshots attached on failed scenarios.
* Configurable browser settings via `config.properties` and `-D` system properties.
* No local WebDriver binary is required by default.

### Technologies

* Language: Java 21
* Build System: Gradle 8
* Test Runner: JUnit Platform
* BDD: Cucumber JVM
* UI Automation: Selenide / Selenium WebDriver
* API Testing: RestAssured
* Reporting: Allure
* Logging: Logback

### Project Structure

```text
src/test/java/io/github/royalspirit/sjctestframework/
├── RunCucumberTest.java                # Cucumber suite entry point
├── core/
│   ├── annotations/                    # Framework annotations
│   ├── api/                            # REST API request, response, and specification helpers
│   ├── stepdefs/                       # Cucumber step definitions
│   ├── Setup.java                      # Browser and scenario hooks
│   ├── FrameworkPage.java              # Base page action executor
│   ├── PageContextRegistry.java        # Current page and page object registry
│   ├── ElementsObjectRegistry.java     # Element title registry
│   └── FrameworkRegistryValidator.java # Startup validation for framework annotations
└── pages/                              # Page objects and common page actions

src/test/resources/
├── features/                           # Cucumber feature files
├── configuration/config.properties     # Test execution configuration
├── junit-platform.properties           # Cucumber/JUnit configuration
├── allure.properties                   # Allure results configuration
└── logback.xml                         # Logging configuration
```

### Build and Run

#### Requirements

* JDK 21+
* Gradle wrapper from this repository
* Chrome or Firefox installed locally

#### Run all tests

```bash
./gradlew test
```

#### Run in headless mode

```bash
./gradlew test -Dbrowser.headless=true
```

#### Run a specific tagged scenario

```bash
./gradlew test -Dcucumber.filter.tags=@TEST-003
```

#### Run API scenarios

```bash
./gradlew test -Dcucumber.filter.tags=@api
```

#### Run with a different browser

```bash
./gradlew test -Dbrowser.name=firefox
```

### Configuration

Default configuration is stored in:

```text
src/test/resources/configuration/config.properties
```

Supported properties:

```properties
starting.url=https://www.saucedemo.com
browser.name=chrome
browser.size=1920x1200
browser.version=null
browser.headless=false
path.to.webdriver=null
api.base.url=https://jsonplaceholder.typicode.com
logs.color.enabled=true
```

System properties passed with `-D` have priority over values from `config.properties`.

Disable colored console log values:

```bash
./gradlew test -Dlogs.color.enabled=false
```

### Allure Report

Test execution writes Allure results to:

```text
build/allure-results
```

Generate and open the report:

```bash
./gradlew allureServe
```

---

## Русская версия

SJC Test Framework (Simple Java Cucumber-based Test Framework) — фреймворк автоматизации тестирования на Java, построенный на Cucumber, Selenide, RestAssured, JUnit Platform и Allure.

Текущая реализация включает читаемые браузерные UI-сценарии и минимальный слой для REST API-тестирования с тем же BDD-подходом.

### Назначение

Это развивающийся фреймворк автоматизации тестирования для написания читаемых BDD-сценариев для UI и REST API-проверок.

В качестве UI-ресурса для примеров выбран [SauceDemo](https://www.saucedemo.com) — публичный тренировочный сайт, который часто используют для отработки сценариев UI-автоматизации.

В качестве REST API-ресурса для примеров выбран [JSONPlaceholder](https://jsonplaceholder.typicode.com) — публичный fake API, который часто используют для тестирования и прототипирования API.

### Архитектурная идея

Фреймворк построен вокруг простой идеи: feature-файлы должны описывать намерение теста читаемым языком, а внутренние механизмы фреймворка скрывают технические детали реализации.

Сейчас для UI-тестов цепочка выглядит так:

```text
Feature step -> Step definition -> Action executor -> Page object -> Selenide element
```

### Пример DSL

UI-сценарий:

```gherkin
Сценарий: Успешная авторизация пользователя
  * открывается страница "Page Swag Labs"
  * пользователь (заполняет поле) "Username" значением "standard_user"
  * пользователь (заполняет поле) "Password" значением "secret_sauce"
  * пользователь (нажимает кнопку) "Login"
  * открывается страница "Products"
```

API-сценарий:

```gherkin
Сценарий: Проверка ответа сервиса по идентификатору
  * api (отправляет GET запрос) на endpoint "/posts/1"
  * api (проверяет статус ответа) равен "200"
  * api (проверяет значение поля ответа) "id" равно "1"
  * api (проверяет что поле ответа не пустое) "title"

Сценарий: Проверка ответа сервиса с параметрами
  * api (отправляет GET запрос) на endpoint "/posts" с параметрами:
    | userId | 1 |
  * api (проверяет статус ответа) равен "200"
  * api (проверяет что список в ответе не пустой)
  * api (проверяет размер списка в ответе) равен "10"
  * api (проверяет что поле ответа не пустое) "[0].title"

Сценарий: Создание пост запроса с передачей JSON body
  * api (отправляет POST запрос) на endpoint "/posts" с JSON body:
    """
    {
      "title": "foo",
      "body": "bar",
      "userId": 1
    }
    """
  * api (проверяет статус ответа) равен "201"
  * api (проверяет значение поля ответа) "title" равно "foo"
  * api (проверяет значение поля ответа) "body" равно "bar"
  * api (проверяет значение поля ответа) "userId" равно "1"
  * api (проверяет что поле ответа не пустое) "id"
```

### Возможности

* Cucumber feature-файлы с читаемыми шагами.
* Page Object структура для UI-страниц.
* Регистрация страниц, действий и элементов через аннотации.
* Общие действия: заполнение полей, клики, проверки списков, проверки текстовых значений.
* Работа с браузером через Selenide.
* Базовые REST API-проверки через RestAssured.
* Интеграция с Allure и прикрепление скриншота при падении сценария.
* Настройка браузера через `config.properties` и `-D` параметры.
* Локальный WebDriver binary по умолчанию не требуется.

### Технологии

* Java 21
* Gradle 8
* JUnit Platform
* Cucumber JVM
* Selenide / Selenium WebDriver
* RestAssured
* Allure
* Logback

### Запуск

#### Требования

* JDK 21+
* Gradle wrapper из репозитория
* Установленный Chrome или Firefox

#### Запуск всех тестов

```bash
./gradlew test
```

#### Запуск в headless-режиме

```bash
./gradlew test -Dbrowser.headless=true
```

#### Запуск конкретного сценария по тегу

```bash
./gradlew test -Dcucumber.filter.tags=@TEST-003
```

#### Запуск API-сценариев

```bash
./gradlew test -Dcucumber.filter.tags=@api
```

#### Запуск в другом браузере

```bash
./gradlew test -Dbrowser.name=firefox
```

### Конфигурация

Основной конфиг находится здесь:

```text
src/test/resources/configuration/config.properties
```

Поддерживаемые параметры:

```properties
starting.url=https://www.saucedemo.com
browser.name=chrome
browser.size=1920x1200
browser.version=null
browser.headless=false
path.to.webdriver=null
api.base.url=https://jsonplaceholder.typicode.com
logs.color.enabled=true
```

Значения, переданные через `-D`, имеют приоритет над значениями из файла.

Отключить цветные значения в консольных логах:

```bash
./gradlew test -Dlogs.color.enabled=false
```

### Allure Report

Результаты Allure сохраняются в:

```text
build/allure-results
```

Сгенерировать и открыть отчет:

```bash
./gradlew allureServe
```

### Feedback

If you found a bug or want to suggest an improvement, feel free to create an Issue or Pull Request.
