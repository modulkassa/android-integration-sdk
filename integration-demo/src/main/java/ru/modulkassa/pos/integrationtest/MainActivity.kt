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
import kotlinx.android.synthetic.main.activity_main.printText
import ru.modulkassa.pos.integration.core.action.ActionCallback
import ru.modulkassa.pos.integration.core.action.GetKktInfoAction
import ru.modulkassa.pos.integration.core.action.PrintCheckAction
import ru.modulkassa.pos.integration.core.action.PrintTextAction
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.DocumentType
import ru.modulkassa.pos.integration.entity.check.FiscalInfo
import ru.modulkassa.pos.integration.entity.check.InventPosition
import ru.modulkassa.pos.integration.entity.check.Measure.PCS
import ru.modulkassa.pos.integration.entity.check.MoneyPosition
import ru.modulkassa.pos.integration.entity.check.ReportLine
import ru.modulkassa.pos.integration.entity.check.ReportLineType.QR
import ru.modulkassa.pos.integration.entity.check.ReportLineType.TEXT
import ru.modulkassa.pos.integration.entity.check.TaxationMode.COMMON
import ru.modulkassa.pos.integration.entity.check.TextReport
import ru.modulkassa.pos.integration.entity.check.VatTag.TAG_1103
import ru.modulkassa.pos.integration.entity.kkt.KktDescription
import ru.modulkassa.pos.integration.entity.payment.PaymentType.CARD
import ru.modulkassa.pos.integration.intent.ModulKassaServiceIntent
import ru.modulkassa.pos.integration.intent.PrintCheckIntent
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

        printText.setOnClickListener {
            modulkassa?.let { service ->
                val lines = ArrayList<ReportLine>().apply {
                    add(ReportLine("        ООО 'Магазин-2014'        ", TEXT))
                    add(ReportLine("ИНН: 4959166101     КПП: 495901001", TEXT))
                    add(ReportLine("КАССА: 11022            СМЕНА: 693", TEXT))
                    add(ReportLine("ЧЕК: 3027   ДАТА: 13.12.2012 11:12", TEXT))
                    add(ReportLine("                                  ", TEXT))
                    add(ReportLine("http://check.egais.ru?id=88a7a3ed-39ae-45de-a3cc-644639f36f4e&dt=0910141104&" +
                        "cn=030000255555", QR))
                    add(ReportLine("                                  ", TEXT))
                    add(ReportLine("http://check.egais.ru?id=88a7a3ed-39ae-45de-a3cc-644639f36f4e&dt=0910141104&" +
                        "cn=030000255555", TEXT))
                    add(ReportLine("04 40 EA 2B C7 08 75 5D F0 43 C1 04 5C 06 96 71 69 DD BF 30 D9 2D 6B 7D F0 FE 81" +
                        " 43 F9 C4 51 21 E3 42 C9 67 63 4F 24 D5 42 B1 8B 1D 3D F8 6F 91 21 00 6D 8B DE 56 91" +
                        " CA BB ED 0D 36 11 96 B4 33", TEXT))
                }

                PrintTextAction(TextReport(lines)).execute(service, object : ActionCallback<Boolean> {
                    override fun succeed(result: Boolean?) {
                        runOnUiThread {
                            Toast.makeText(this@MainActivity, "Текст напечатан",
                                Toast.LENGTH_LONG).show()
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
        val serviceIntent = ModulKassaServiceIntent() // StagingModulKassaServiceIntent()
        startService(serviceIntent)
        bindService(serviceIntent, connection, Context.BIND_AUTO_CREATE)
    }
}
