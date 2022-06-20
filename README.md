# Библиотека для интеграции с приложением МодульКасса

[![Release](https://jitpack.io/v/modulkassa/android-integration-sdk.svg)](https://jitpack.io/#modulkassa/android-integration-sdk) [![Maven Central](https://img.shields.io/maven-central/v/ru.modulkassa.pos/integration-library)](https://search.maven.org/artifact/ru.modulkassa.pos/integration-library)

Предоставляет набор сущностей и методов для работы с МодульКассой.

## Возможности

- работа со сменами
- регистрация чеков
- работа с различными платежными системами (Ingenico, INPAS, AliPay, PayMe, Sunmi P1)
- интеграция своей платежной системы в МодульКассу
- и тд

## Подключение библиотеки

Добавить репозиторий в build.gradle файл

```groovy
allprojects {
    repositories {
        mavenCentral()
    }
}
```

Подключить библиотеку

```groovy
dependencies {
    implementation 'ru.modulkassa.pos:integration-library:1.3.0'
}
```

## Пример использования

Печать чека:

```kotlin
startActivityForResult(
    modulKassaClient.checkManager().createPrintCheckIntent(
        demoCheck.copy(
            id = UUID.randomUUID().toString()
        )
    ),
    PRINT_CHECK_REQUEST_CODE
)
```

## Wiki

Для более подробной информации по использованию смотрите [wiki](https://github.com/modulkassa/android-integration-sdk/wiki)
