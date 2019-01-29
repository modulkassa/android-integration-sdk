# Библиотека для интеграции с приложением МодульКасса

[![Download](https://api.bintray.com/packages/modulkassa/public/ru.modulkassa.integration/images/download.svg?version=0.8.0) ](https://bintray.com/modulkassa/public/ru.modulkassa.integration/0.8.0/link)

Предоставляет набор сущностей и методов для работы с МодульКассой.

## Возможности

- работа со сменами
- регистрация чеков
- работа с различными платежными системами (Ingenico, INPAS, AliPay, PayMe, Sunmi P1)
- интеграция своей платежной системы в МодульКассу
- и тд

## Подключение библиотеки

```groovy
dependencies {
    implementation 'ru.modulkassa.pos:integration-library:x.x.x'
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