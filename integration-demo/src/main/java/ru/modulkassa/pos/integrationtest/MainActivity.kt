package ru.modulkassa.pos.integrationtest

import android.content.ComponentName
import android.content.Context
import android.content.ServiceConnection
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.IBinder
import android.support.v4.app.ActivityCompat
import android.support.v4.content.ContextCompat
import android.support.v7.app.AppCompatActivity
import android.widget.Toast
import kotlinx.android.synthetic.main.activity_main.getKktDescription
import kotlinx.android.synthetic.main.activity_main.printCheck
import kotlinx.android.synthetic.main.activity_main.printCheckViaModulKassa
import ru.modulkassa.pos.integration.core.action.ActionCallback
import ru.modulkassa.pos.integration.core.action.GetKktInfoAction
import ru.modulkassa.pos.integration.core.action.PrintCheckAction
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.DocumentType
import ru.modulkassa.pos.integration.entity.check.FiscalInfo
import ru.modulkassa.pos.integration.entity.check.InventPosition
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.TaxationMode.COMMON
import ru.modulkassa.pos.integration.entity.check.VatTag.TAG_1103
import ru.modulkassa.pos.integration.entity.kkt.KktDescription
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import ru.modulkassa.pos.integration.intent.PrintCheckIntent
import ru.modulkassa.pos.integration.intent.StagingModulKassaServiceIntent
import ru.modulkassa.pos.integration.service.IModulKassa
import java.math.BigDecimal
import java.util.UUID

class MainActivity : AppCompatActivity() {

    companion object {
        private const val PRINT_CHECK_PERMISSION_REQUEST = 1
    }

    private var modulkassa: IModulKassa? = null

    private val connection = object : ServiceConnection {
        override fun onServiceDisconnected(name: ComponentName?) {
            modulkassa = null
        }

        override fun onServiceConnected(name: ComponentName?, service: IBinder?) {
            modulkassa = IModulKassa.Stub.asInterface(service)
        }

    }

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
                vatTag = TAG_1103,
                quantity = BigDecimal.ONE,
                measure = PCS,
                inventCode = "2880000023757"
            ),
            InventPosition(
                name = "Жесткий ™™™™™™ диск «вфыдл» ©®°µ£¢@¦ ",
                price = BigDecimal("100"),
                barcode = "2880000023757",
                vatTag = TAG_1103,
                quantity = BigDecimal.ONE,
                measure = PCS,
                inventCode = "2880000023757"
            )

        ),
        moneyPositions = listOf(MoneyPosition(
            paymentType = CARD,
            sum = BigDecimal("300")
        )),
        taxMode = COMMON,
        textToPrint = "Текст для дополнительной\nпечати на чеке"
    )

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_main)

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

        if (ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.PRINT_CHECK")
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.KKT_INFO")
            != PackageManager.PERMISSION_GRANTED ||
            ContextCompat.checkSelfPermission(this, "ru.modulkassa.pos.permission.CHECK_INFO")
            != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(this,
                arrayOf("ru.modulkassa.pos.permission.KKT_INFO",
                    "ru.modulkassa.pos.permission.CHECK_INFO",
                    "ru.modulkassa.pos.permission.PRINT_CHECK"),
                PRINT_CHECK_PERMISSION_REQUEST)
        } else {
            connectToService()
        }

        printCheckViaModulKassa.setOnClickListener {
            val intent = PrintCheckIntent(
                check = baseCheck.copy(
                    id = UUID.randomUUID().toString(),
                    moneyPositions = emptyList()
                ),
                employeeName = "Кассир 1",
                pin = "")
            startActivity(intent)
        }
    }

    override fun onRequestPermissionsResult(requestCode: Int,
                                            permissions: Array<String>, grantResults: IntArray) {
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
        val serviceIntent = StagingModulKassaServiceIntent() // ModulKassaServiceIntent()
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
}
