# Интеграция с приложением МодульКасса

## История изменений

Версия | Изменения
-------|----------
0.6.1 | Добавлена поддержка 2х чеков (кассира и покупателя, `Slip.DELIMITER_VALUE`)
0.6.0 | Дополнена информация о ККТ полями с данными точки (id, ИНН, название, адрес).<br>Добавлено новое событие `ru.modulkassa.pos.events.UI_RETURN_TO_MAIN_SCREEN`

[Подключение библиотеки](#Подключение-библиотеки)

[Окружение](#Окружение)

[Печать чека (фискализация)](#Печать-чека-фискализация)

[Печать чека (фискализация) в рамках интерфейса приложения МодульКасса](#Печать-чека-фискализация-в-рамках-интерфейса-приложения-МодульКасса)

[Поддержка внешней платежной системы](#Поддержка-внешней-платежной-системы)

[Получение информации о ККТ](#Получение-информации-о-ККТ)

## Подключение библиотеки

Для интеграции вашего приложения с МодульКасса, к вашему проекту потребуется подключить модуль 
```integration-library```. Демо приложение находится в модуле ```integration-demo```.

## Окружение

**Внимание!** Cуществует несколько окружений при работе с МодульКассой.

Отличаются они названием
 - МодульКасса - продакшен версия (https://my.modulkassa.ru)
 - МодульКасса (RC) - версия, которая работает в окружении RC (https://staging.dev.avanpos.com)
 - МодульКасса (Staging) - версия, которая работает в окружении Staging (https://rc.dev.avanpos.com)

В зависимости от окружением необходимо использовать соответсвующий intent/имя пакета (например, `ModulKassaServiceIntent`/`StagingModulKassaServiceIntent`/`RcModulKassaServiceIntent`

## Печать чека (фискализация)

Статус: **опубликован**

1. Приложение должно объявить в манифесте (AndroidManifest.xml), разрешение на печать 
```ru.modulkassa.pos.permission.PRINT_CHECK```

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.modulkassa.pos.integrationtest">
    <uses-permission android:name="ru.modulkassa.pos.permission.PRINT_CHECK"/>
    
    <application ...>
        ...
    </application>
</manifest>
```

2. Разрешение ```ru.modulkassa.pos.permission.PRINT_CHECK``` также должно быть запрошено перед 
подключением к сервису печати. Более подробно можно о запросе разрешения можно почитать тут - 
[Request App Permissions](https://developer.android.com/training/permissions/requesting)

```kotlin
        if (ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.PRINT_CHECK")
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf("ru.modulkassa.pos.permission.PRINT_CHECK"),
                PRINT_CHECK_PERMISSION_REQUEST)
        } else {
            connectToService()
        }
        
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PRINT_CHECK_PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    connectToService()
                } else {
                    Toast.makeText(this, "Разрешение на печать было отклонено пользователем",
                        Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
```

3.  После того, как разрешение было получено, можно подключаться к сервису.

```kotlin
    private var modulkassa: IModulKassa? = null

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            modulkassa = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            modulkassa = IModulKassa.Stub.asInterface(service)
        }
    }
    
    private fun connectToService() {
        val serviceIntent = StagingModulKassaServiceIntent() // ModulKassaServiceIntent()
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
```

Обратите внимание на испльзуемое окружение. Для подключения к сервису приложения настроенного 
на *staging* следует использовать ```StagingModulKassaServiceIntent```, *rc* - ```RcModulKassaServiceIntent```, 
*production* - ```ModulKassaServiceIntent```.

4.  Теперь можно отправлять чек на печать

```kotlin
val baseCheck = Check(
        id = "", // проинициализируем позже
        docType = DocumentType.SALE,
        employee = "Иванов Иван Иванович",
        printReceipt = true,
        email = "i.tretyakov@modulbank.ru",
        inventPositions = listOf(
            InventPosition(
                name = "Материнская плата AS—Rock H32M R3.0, Socket1150, iH81, 2DDR3, PCI-Ex16, 2SATA2, 2SATA3",
                price = BigDecimal("200"),
                barcode = "2880000023757",
                vatTag = 1103,
                quantity = BigDecimal.ONE,
                measure = "pcs",
                inventCode = "2880000023757"
            ),
            InventPosition(
                name = "Жесткий ™™™™™™ диск «вфыдл» ©®°µ£¢@¦ ",
                price = BigDecimal("100"),
                barcode = "2880000023757",
                vatTag = 1103,
                quantity = BigDecimal.ONE,
                measure = "pcs",
                inventCode = "2880000023757"
            )

        ),
        moneyPositions = listOf(MoneyPosition(
            paymentType = CARD,
            sum = BigDecimal("300")
        )),
        taxMode = COMMON
    )
    
    printCheck.setOnClickListener {
            modulkassa?.let {
                PrintCheckAction(
                    baseCheck.copy(id = UUID.randomUUID().toString())
                ).execute(it, object : ActionCallback<FiscalInfo> {
                    override fun succeed(result: FiscalInfo?) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Printed - $result",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun failed(message: String, extra: Map<String, Any>?) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "$message", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
```

**Внимание!** Сейчас приложение должно само управлять соединением (подкючение/отключение/переподключение) 
с сервисом печати МодульКассы.

## Печать чека (фискализация) в рамках интерфейса приложения МодульКасса

Статус: **опубликован**

Преимущества такого подхода:
- нет необходимости управлять соединением с сервисом МодульКассы
- отсутствует необходимость интегрироваться с платежными системами. При оплате будут доступны системы, которые поддерживаются МодульКассой.

Особенности:
- необходимо передавать в запросе полное имя кассира в том виде, котором он указан в ЛК МодульКассы
- необходимо передавать PIN (или пустую строку)

Для отправки чека на печать:
1. Приложение должно объявить в манифесте (AndroidManifest.xml), разрешение на получение данных закрытых чеков 
```ru.modulkassa.pos.permission.CHECK_INFO```

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.modulkassa.pos.integrationtest">
    <uses-permission android:name="ru.modulkassa.pos.permission.CHECK_INFO"/>
    
    <application ...>
        ...
    </application>
</manifest>
```

2. Разрешение ```ru.modulkassa.pos.permission.CHECK_INFO``` также должно быть запрошено перед 
отправкой чека на печать. Более подробно можно о запросе разрешения можно почитать тут - 
[Request App Permissions](https://developer.android.com/training/permissions/requesting)

```kotlin
        if (ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.CHECK_INFO")
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf("ru.modulkassa.pos.permission.CHECK_INFO"),
                PRINT_CHECK_PERMISSION_REQUEST)
        } else {
            connectToService()
        }
        
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PRINT_CHECK_PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    connectToService()
                } else {
                    Toast.makeText(this, "Разрешение на получение данных было отклонено пользователем",
                        Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
```

3. Для получения результата необходимо настроить `BroadcastReceiver`, который будет получать информацию о последнем закрытом чеке. Чтобы вернуться в приложение при получении уведомления, запустите нужную активити.
```kotlin
class DemoBroadcastReceiver : CheckUpdatesBroadcastReceiver() {
    override fun onCheckClosed(context: Context, checkId: String, linkedDocId: String?,
                               sum: BigDecimal, check: Check) {
        Log.i("DemoBroadcastReceiver", "чек - $check")
        Toast.makeText(
            context,
            "Чек закрыт: id - $checkId, linkedId - $linkedDocId, сумма - $sum, чек - $check}",
            Toast.LENGTH_LONG
        ).show()

        context.startActivity(Intent(context, MainActivity::class.java)
            .apply {
                flags = Intent.FLAG_ACTIVITY_NEW_TASK
            }
        )
    }
}
```
4. Зарегистрируйте `BroadcastReceiver` в манифесте  

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
   package="ru.modulkassa.pos.integrationtest">
   <uses-permission android:name="ru.modulkassa.pos.permission.CHECK_INFO"/>
   
   <application>
       <receiver android:name=".DemoBroadcastReceiver">
           <intent-filter>
               <action android:name="ru.modulkassa.pos.events.CHECK_CLOSED"/>
           </intent-filter>
       </receiver>
   </application>
</manifest>
```

5. Создайте чек. Указывать способы оплаты не обязательно - они будут проигнорированы
```kotlin
val baseCheck = Check(
        id = "", // проинициализируем позже
        docType = DocumentType.SALE,
        employee = "Иванов Иван Иванович",
        printReceipt = true,
        email = "i.tretyakov@modulbank.ru",
        inventPositions = listOf(
            InventPosition(
                name = "Материнская плата AS—Rock H32M R3.0, Socket1150, iH81, 2DDR3, PCI-Ex16, 2SATA2, 2SATA3",
                price = BigDecimal("200"),
                barcode = "2880000023757",
                vatTag = 1103,
                quantity = BigDecimal.ONE,
                measure = "pcs",
                inventCode = "2880000023757"
            ),
            InventPosition(
                name = "Жесткий ™™™™™™ диск «вфыдл» ©®°µ£¢@¦ ",
                price = BigDecimal("100"),
                barcode = "2880000023757",
                vatTag = 1103,
                quantity = BigDecimal.ONE,
                measure = "pcs",
                inventCode = "2880000023757"
            )

        )
        taxMode = COMMON
    )
```

6. Создайте `PrintCheckIntent`. Укажите чек, которые необходимо фискализировать, а также данные кассира. Запустите активити.
```kotlin
            val intent = PrintCheckIntent(
                check = baseCheck.copy(id = UUID.randomUUID().toString()),
                employeeName = "Кассир 1",
                pin = "")
            startActivity(intent)
```

## Поддержка внешней платежной системы

МодульКасса позволяет проводить платежи через другие приложения. После их установки на экране
подитога появляется соответствующий пункт оплаты.

Чтобы реализовать приложение платежной системы, необходимо объявить сервис в манифесте

```xml
        <service
            android:name=".DemoPaymentService"
            android:enabled="true"
            android:exported="true"
            android:icon="@drawable/ic_big_logo"
            android:label="С демо счета">
            <intent-filter>
                <action android:name="ru.modulkassa.PAYMENT_SERVICE"/>
            </intent-filter>
        </service>
``` 

`icon` - изображение/иконка приложения, которое будет отображаться в приложении МодульКасса

`label` - текст пункта оплаты

От МодульКассы может приходить несколько типов запросов:

- оплата (`PayRequest`)
- возврат (`RefundRequest`)
- отмена возврата/оплаты (`CancelRequest`)

Запросы будут приходить в объявленный сервис `DemoPaymentService`. Его следует унаследовать от `PluginService`

```kotlin
class SberbankPaymentService : PluginService() {

    override fun createHandlers(): List<OperationHandler> {
        // handlers
    }
}
```

Каждому запросу соответствует свой обработчик: для оплаты - `PayOperationHandler`,
для возврата - `RefundOperationHandler`, для отмены - `CancelOperationHandler`.

Результат обработки запроса должен быть сообщен через callback (см. `IPluginServiceCallback.aidl`).

В случае, если для обработки запроса необходим UI то callback можно сериализовать и положить в `Intent`:

```kotlin
    override fun createHandlers(): List<OperationHandler> {

        return listOf(
            object : PayOperationHandler() {
                override fun handlePay(payRequest: PayRequest, callback: PluginServiceCallbackHolder) {
                    val payIntent = Intent(applicationContext, PayActivity::class.java)
                    callback.putToIntent(payIntent)
                    payIntent.putExtra(KEY_DATA, payRequest.toBundle())
                    payIntent.flags = Intent.FLAG_ACTIVITY_NEW_TASK
                    startActivity(payIntent)
                }
            }
        )
    }
```

И вернуть результат:

```kotlin
fun paySucceeded(result: PayResult) {
    PluginServiceCallbackHolder.getFromIntent(activity.intent)?.get()?.succeeded(
        result.toBundle())
    activity.finish()
}
```

После успешной оплаты требуется вернуть соответсвующий объект: для оплаты - `PayResult`,
для возврата - `RefundResult`, для отмены - `CancelResult`.

## Получение информации о ККТ

Статус: **предоставляется по запросу**

1. Приложение должно объявить в манифесте (AndroidManifest.xml), разрешение на получение данных 
```ru.modulkassa.pos.permission.KKT_INFO```

```xml
<manifest xmlns:android="http://schemas.android.com/apk/res/android"
    package="ru.modulkassa.pos.integrationtest">
    <uses-permission android:name="ru.modulkassa.pos.permission.KKT_INFO"/>
    
    <application ...>
        ...
    </application>
</manifest>
```

2. Разрешение ```ru.modulkassa.pos.permission.KKT_INFO``` также должно быть запрошено перед
обращением подключением к сервису. Более подробно можно о запросе разрешения можно почитать тут - 
[Request App Permissions](https://developer.android.com/training/permissions/requesting)

```kotlin
        if (ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.KKT_INFO")
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf("ru.modulkassa.pos.permission.KKT_INFO"),
                PRINT_CHECK_PERMISSION_REQUEST)
        } else {
            connectToService()
        }
        
    override fun onRequestPermissionsResult(requestCode: Int, permissions: Array<String>, grantResults: IntArray) {
        when (requestCode) {
            PRINT_CHECK_PERMISSION_REQUEST -> {
                // If request is cancelled, the result arrays are empty.
                if ((grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED)) {
                    connectToService()
                } else {
                    Toast.makeText(this, "Разрешение на печать было отклонено пользователем",
                        Toast.LENGTH_LONG).show()
                    finish()
                }
                return
            }
            else -> {
                // Ignore all other requests.
            }
        }
    }
```

3. После того, как разрешение было получено, можно подключаться к сервису.

```kotlin
    private var modulkassa: IModulKassa? = null

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            modulkassa = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            modulkassa = IModulKassa.Stub.asInterface(service)
        }
    }
    
    private fun connectToService() {
        val serviceIntent = StagingModulKassaServiceIntent() // ModulKassaServiceIntent()
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
```

Обратите внимание на испльзуемое окружение. Для подключения к сервису приложения настроенного 
на *staging* следует использовать ```StagingModulKassaServiceIntent```, *rc* - ```RcModulKassaServiceIntent```, 
*production* - ```ModulKassaServiceIntent```.

4. Теперь можно запрашивать данные

```kotlin
        getKktDescription.setOnClickListener {
            modulkassa?.let {
                GetKktInfoAction().execute(it, object : ActionCallback<KktDescription> {
                    override fun succeed(result: KktDescription?) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "$result",
                                Toast.LENGTH_LONG).show()
                        }
                    }

                    override fun failed(message: String, extra: Map<String, Any>?) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "$message", Toast.LENGTH_LONG).show()
                        }
                    }
                })
            }
        }
```
