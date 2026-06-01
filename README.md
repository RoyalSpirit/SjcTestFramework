# SJC Test Framework

![Java](https://img.shields.io/badge/Java-21-blue)
![Gradle](https://img.shields.io/badge/Gradle-8.x-02303A)
![Selenide](https://img.shields.io/badge/Selenide-7.x-43B02A)
![Cucumber](https://img.shields.io/badge/Cucumber-7.x-23D96C)
![Allure](https://img.shields.io/badge/Allure-Report-orange)

SJC Test Framework (Simple Java Cucumber-based Test Framework) is a Java-based test automation framework built around Cucumber, Selenide, JUnit Platform, and Allure.

The current implementation focuses on readable browser-based UI scenarios, while the framework name and structure leave room for extending the same BDD approach to other test layers.

---

[English Version](#english-version) | [Русская версия](#русская-версия)

---

## English Version

### Purpose

This repository is an evolving test automation framework for writing readable BDD scenarios.

[SauceDemo](https://www.saucedemo.com) is used as the example UI target because it is a public practice resource commonly used for UI test automation scenarios.

### Architecture Idea

The framework is built around a simple idea: feature files should describe user behavior in readable language, while page objects and annotations hide technical UI implementation details.

For the current UI layer, the main flow is:

```text
Feature step -> Step definition -> Action executor -> Page object -> Selenide element
```

### DSL Example

```gherkin
Scenario: Successful login
  * user is on page "Page Swag Labs"
  * user (fills field) "Username" with value "standard_user"
  * user (fills field) "Password" with value "secret_sauce"
  * user (press button) "Login"
  * user is on page "Products"
```

### Features

* Cucumber feature files with readable test steps.
* Page Object structure for UI pages.
* Annotation-based page, action, and element registration.
* Shared actions for common UI operations: filling fields, clicking elements, checking lists, checking text values.
* Selenide-based browser interaction and waits.
* Allure integration with screenshots attached on failed scenarios.
* Configurable browser settings via `config.properties` and `-D` system properties.
* No local WebDriver binary is required by default.

### Technologies

* Language: Java 21
* Build System: Gradle 8
* Test Runner: JUnit Platform
* BDD: Cucumber JVM
* UI Automation: Selenide / Selenium WebDriver
* Reporting: Allure
* Logging: Logback

### Project Structure

```text
src/test/java/io/github/royalspirit/sjctestframework/
├── RunCucumberTest.java                # Cucumber suite entry point
├── core/
│   ├── annotations/                    # Framework annotations
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

SJC Test Framework (Simple Java Cucumber-based Test Framework) — фреймворк автоматизации тестирования на Java, построенный на Cucumber, Selenide, JUnit Platform и Allure.

Текущая реализация сфокусирована на читаемых браузерных UI-сценариях, но название и структура фреймворка оставляют пространство для расширения того же BDD-подхода на другие уровни тестирования.

### Назначение

Это развивающийся фреймворк автоматизации тестирования для написания читаемых BDD-сценариев.

В качестве UI-ресурса для примеров выбран [SauceDemo](https://www.saucedemo.com) — публичный тренировочный сайт, который часто используют для отработки сценариев UI-автоматизации.

### Архитектурная идея

Фреймворк построен вокруг простой идеи: feature-файлы должны описывать поведение пользователя читаемым языком, а Page Objects и аннотации скрывают технические детали взаимодействия с UI.

Сейчас для UI-тестов цепочка выглядит так:

```text
Feature step -> Step definition -> Action executor -> Page object -> Selenide element
```

### Пример DSL

```gherkin
Сценарий: Успешная авторизация пользователя
  * открывается страница "Page Swag Labs"
  * пользователь (заполняет поле) "Username" значением "standard_user"
  * пользователь (заполняет поле) "Password" значением "secret_sauce"
  * пользователь (нажимает кнопку) "Login"
  * открывается страница "Products"
```

### Возможности

* Cucumber feature-файлы с читаемыми шагами.
* Page Object структура для UI-страниц.
* Регистрация страниц, действий и элементов через аннотации.
* Общие действия: заполнение полей, клики, проверки списков, проверки текстовых значений.
* Работа с браузером через Selenide.
* Интеграция с Allure и прикрепление скриншота при падении сценария.
* Настройка браузера через `config.properties` и `-D` параметры.
* Локальный WebDriver binary по умолчанию не требуется.

### Технологии

* Java 21
* Gradle 8
* JUnit Platform
* Cucumber JVM
* Selenide / Selenium WebDriver
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

#### Запуск в другом браузере

```bash
./gradlew test -Dbrowser.name=firefox
```

### Конфигурация

Основной конфиг находится здесь:

```text
src/test/resources/configuration/config.properties
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
