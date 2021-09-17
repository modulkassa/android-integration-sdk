# Библиотека для интеграции с приложением МодульКасса

[![Release](https://jitpack.io/v/antashev/android-integration-sdk.svg)](https://jitpack.io/#antashev/android-integration-sdk)



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
