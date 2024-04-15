package ru.modulkassa.pos.integrationtest

import android.app.Activity
import android.content.ComponentName
import android.content.Context
import android.content.Intent
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Build
import android.os.Bundle
import android.os.IBinder
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import ru.modulkassa.pos.integration.core.action.ActionCallback
import ru.modulkassa.pos.integration.core.action.CloseShiftAction
import ru.modulkassa.pos.integration.core.action.GetCheckInfoAction
import ru.modulkassa.pos.integration.core.action.GetKktInfoAction
import ru.modulkassa.pos.integration.core.action.GetShiftInfoAction
import ru.modulkassa.pos.integration.core.action.OpenShiftAction
import ru.modulkassa.pos.integration.core.action.PrintCheckAction
import ru.modulkassa.pos.integration.core.action.PrintMoneyCheckAction
import ru.modulkassa.pos.integration.core.action.PrintTextAction
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.CheckInfoRequest
import ru.modulkassa.pos.integration.entity.check.DocumentType.RETURN
import ru.modulkassa.pos.integration.entity.check.Employee
import ru.modulkassa.pos.integration.entity.check.FiscalInfo
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.TextReport
import ru.modulkassa.pos.integration.entity.kkt.KktDescription
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheckType.INCOME
import ru.modulkassa.pos.integration.entity.kkt.Shift
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CASH
import ru.modulkassa.pos.integration.intent.ModulKassaServiceIntent
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integrationtest.databinding.ActivityMainBinding
import java.math.BigDecimal
import java.util.UUID

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PRINT_CHECK_PERMISSION_REQUEST = 1
        private const val PRINT_CHECK_REQUEST_CODE = 2
        private const val SHIFT_ACTION_REQUEST_CODE = 3
        private const val CREATE_MONEY_DOC_REQUEST_CODE = 4
    }

    private var modulkassa: IModulKassa? = null

    private val modulKassaClient = DemoApp.instance.modulKassaClient

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            modulkassa = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            modulkassa = IModulKassa.Stub.asInterface(service)
        }

    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            PRINT_CHECK_REQUEST_CODE -> handlePrintCheckAnswer(resultCode, data)
            SHIFT_ACTION_REQUEST_CODE -> handleShiftActionAnswer(resultCode, data)
            CREATE_MONEY_DOC_REQUEST_CODE -> handleMoneyCheckAnswer(resultCode, data)
        }
    }

    private fun handleShiftActionAnswer(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(
                this@MainActivity,
                "Запрос выполнен успешно",
                Toast.LENGTH_LONG
            ).show()
        } else {
            val resultError = modulKassaClient.shiftManager().parseShiftActionResult(data ?: Intent())
            Toast.makeText(
                this@MainActivity,
                "Activity: Ошибка: ${resultError.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handlePrintCheckAnswer(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            val check = modulKassaClient.checkManager().parsePrintCheckSuccess(data ?: Intent())
            check?.let {
                Toast.makeText(
                    this@MainActivity,
                    "Activity: Чек закрыт: id - ${check.id}, фискальная информация - ${check.fiscalInfo}",
                    Toast.LENGTH_LONG
                ).show()
            }
        } else {
            val resultError = modulKassaClient.checkManager().parsePrintCheckError(data ?: Intent())
            Toast.makeText(
                this@MainActivity,
                "Activity: Ошибка: ${resultError.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    private fun handleMoneyCheckAnswer(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            Toast.makeText(this@MainActivity, "Внесение произошло", Toast.LENGTH_LONG).show()
        } else {
            val resultError = modulKassaClient.checkManager().parsePrintCheckError(data ?: Intent())
            Toast.makeText(
                this@MainActivity,
                "Activity: Ошибка: ${resultError.message}",
                Toast.LENGTH_LONG
            ).show()
        }
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            printCheck.setOnClickListener {
                modulkassa?.let {
                    PrintCheckAction(
                        demoCheck.copy(id = UUID.randomUUID().toString())
                    ).execute(it, object : ActionCallback<FiscalInfo> {
                        override fun succeed(result: FiscalInfo?) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity, "Printed - $result",
                                    Toast.LENGTH_LONG
                                ).show()
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

            getCheckInfo.setOnClickListener {
                modulkassa?.let {
                    GetCheckInfoAction(
                        CheckInfoRequest(checkId = "")
                    ).execute(it, object : ActionCallback<Check> {
                        override fun succeed(result: Check?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "CheckInfo - $result", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            getKktDescription.setOnClickListener {
                modulkassa?.let {
                    GetKktInfoAction().execute(it, object : ActionCallback<KktDescription> {
                        override fun succeed(result: KktDescription?) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity, "$result",
                                    Toast.LENGTH_LONG
                                ).show()
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

            if (ContextCompat.checkSelfPermission(this@MainActivity, "ru.modulkassa.pos.permission.PRINT_CHECK")
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this@MainActivity, "ru.modulkassa.pos.permission.KKT_INFO")
                != PackageManager.PERMISSION_GRANTED ||
                ContextCompat.checkSelfPermission(this@MainActivity, "ru.modulkassa.pos.permission.CHECK_INFO")
                != PackageManager.PERMISSION_GRANTED
            ) {
                ActivityCompat.requestPermissions(
                    this@MainActivity,
                    arrayOf(
                        "ru.modulkassa.pos.permission.KKT_INFO",
                        "ru.modulkassa.pos.permission.CHECK_INFO",
                        "ru.modulkassa.pos.permission.PRINT_CHECK"
                    ),
                    PRINT_CHECK_PERMISSION_REQUEST
                )
            } else {
                connectToService()
            }

            printCheckViaModulKassa.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.checkManager().createPrintCheckIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString()
                        )
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            printCheckViaModulKassaByCard.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.checkManager().createPrintCheckIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString(),
                            moneyPositions = listOf(MoneyPosition(CARD, BigDecimal("300")))
                        )
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            refund.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.checkManager().createPrintCheckIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString(),
                            docType = RETURN,
                            moneyPositions = listOf(MoneyPosition(CASH, BigDecimal("300")))
                        )
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            refundByCard.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.checkManager().createPrintCheckIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString(),
                            docType = RETURN,
                            moneyPositions = listOf(MoneyPosition(CARD, BigDecimal("300")))
                        )
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            cancelByCard.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.checkManager().createPrintCheckIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString(),
                            docType = RETURN,
                            modulKassaId = "75a19823-5c88-4685-b68e-e753f8249185",
                            moneyPositions = listOf(MoneyPosition(CARD, BigDecimal("300"), true, "000010000014"))
                        )
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            printText.setOnClickListener {
                modulkassa?.let { service ->
                    PrintTextAction(TextReport(linesForPrinting)).execute(service, object : ActionCallback<Boolean> {
                        override fun succeed(result: Boolean?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Текст напечатан", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            getShiftInfo.setOnClickListener {
                modulkassa?.let { service ->
                    GetShiftInfoAction().execute(service, object : ActionCallback<Shift> {
                        override fun succeed(result: Shift?) {
                            runOnUiThread {
                                Toast.makeText(
                                    this@MainActivity, "Результат: $result",
                                    Toast.LENGTH_LONG
                                ).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            openShiftViaModulKassa.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.shiftManager().createOpenShiftIntent(Employee(name = "Иванов Иван")),
                    SHIFT_ACTION_REQUEST_CODE
                )
            }

            openShift.setOnClickListener {
                modulkassa?.let {
                    OpenShiftAction(
                        employee = Employee(name = "Иванов Иван")
                    ).execute(it, object : ActionCallback<Boolean> {
                        override fun succeed(result: Boolean?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Открытие смены выполнено", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            closeShiftViaModulKassa.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.shiftManager().createCloseShiftIntent(Employee(name = "Иванов Иван")),
                    SHIFT_ACTION_REQUEST_CODE
                )
            }

            closeShift.setOnClickListener {
                modulkassa?.let {
                    CloseShiftAction(
                        employee = Employee(name = "Иванов Иван")
                    ).execute(it, object : ActionCallback<Boolean> {
                        override fun succeed(result: Boolean?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Закрытие смены выполнено", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            xShiftReport.setOnClickListener {
                startActivityForResult(
                    modulKassaClient.shiftManager().createXReportIntent(),
                    SHIFT_ACTION_REQUEST_CODE
                )
            }

            createMoneyDocViaModulKassa.setOnClickListener {
                val moneyCheck = MoneyCheck(
                    type = INCOME,
                    amount = BigDecimal("100"),
                    text = listOf("Внесение в открытой смене"),
                    employee = Employee("Иванов И.И.")
                )

                startActivityForResult(
                    modulKassaClient.checkManager().createMoneyCheckIntent(moneyCheck),
                    CREATE_MONEY_DOC_REQUEST_CODE
                )
            }

            createMoneyDoc.setOnClickListener {
                val moneyCheck = MoneyCheck(
                    type = INCOME,
                    amount = BigDecimal("100"),
                    text = listOf("Внесение в открытой смене"),
                    employee = Employee("Иванов И.И.")
                )

                modulkassa?.let {
                    PrintMoneyCheckAction(
                        moneyCheck = moneyCheck
                    ).execute(it, object : ActionCallback<Boolean> {
                        override fun succeed(result: Boolean?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, "Внесение прошло успешно", Toast.LENGTH_LONG).show()
                            }
                        }

                        override fun failed(message: String, extra: Map<String, Any>?) {
                            runOnUiThread {
                                Toast.makeText(this@MainActivity, message, Toast.LENGTH_LONG).show()
                            }
                        }
                    })
                }
            }

            printCheckWithSelect.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.checkManager().createPrintCheckIntent(
                            demoCheck.copy(
                                id = UUID.randomUUID().toString()
                            )
                        ),
                        "Выберите приложение для печати чека"
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            printCheckWithPackageName.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.checkManager().createPrintCheckIntent(
                            check = demoCheck.copy(
                                id = UUID.randomUUID().toString()
                            ),
                            packageName = "com.avanpos.pos"
                        ),
                        "Выберите приложение для печати чека"
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            printCheckCardPayWithPackageName.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.checkManager().createPrintCheckIntent(
                            check = demoCheck.copy(
                                id = UUID.randomUUID().toString(),
                                moneyPositions = listOf(MoneyPosition(CARD, BigDecimal("300")))
                            ),
                            packageName = "com.avanpos.pos"
                        ),
                        "Выберите приложение для печати чека"
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            printCheckCardRefundWithPackageName.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.checkManager().createPrintCheckIntent(
                            check = demoCheck.copy(
                                id = UUID.randomUUID().toString(),
                                docType = RETURN,
                                moneyPositions = listOf(MoneyPosition(CARD, BigDecimal("300")))
                            ),
                            packageName = "com.avanpos.pos"
                        ),
                        "Выберите приложение для печати чека"
                    ),
                    PRINT_CHECK_REQUEST_CODE
                )
            }

            openShiftWithPackageName.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.shiftManager().createOpenShiftIntent(
                            Employee(name = "Иванов Иван"),
                            packageName = "com.avanpos.pos"
                        ),
                        "Выберите приложение для открытия смены"
                    ),
                    SHIFT_ACTION_REQUEST_CODE
                )
            }

            closeShiftWithPackageName.setOnClickListener {
                startActivityForResult(
                    Intent.createChooser(
                        modulKassaClient.shiftManager().createCloseShiftIntent(
                            Employee(name = "Иванов Иван"),
                            packageName = "com.avanpos.pos"
                        ),
                        "Выберите приложение для закрытия смены"
                    ),
                    SHIFT_ACTION_REQUEST_CODE
                )
            }
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
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

    override fun onDestroy() {
        if (modulkassa != null) {
            unbindService(connection)
        }
        super.onDestroy()
    }

    private fun connectToService() {
        val serviceIntent = ModulKassaServiceIntent()
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            startForegroundService(serviceIntent)
        } else {
            startService(serviceIntent)
        }
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
}
