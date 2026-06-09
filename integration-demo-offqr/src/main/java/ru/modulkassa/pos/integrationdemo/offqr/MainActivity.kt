package ru.modulkassa.pos.integrationdemo.offqr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.widget.Toast
import androidx.appcompat.app.AppCompatActivity
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.ACTION_OFF_QR
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.KEY_CASH_DOCUMENT_TYPE
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integrationdemo.offqr.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {

    companion object {
        private const val OFF_QR_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityMainBinding.inflate(layoutInflater)
        setContentView(binding.root)

        with(binding) {
            payOffline.setOnClickListener {
                startActivityForResult(
                    createOffQrIntent(
                        demoCheck.copy(
                            id = UUID.randomUUID().toString()
                        )
                    ),
                    OFF_QR_REQUEST_CODE
                )
            }
        }
    }

    // todo переделать механизм
    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OFF_QR_REQUEST_CODE -> handleOffQRAnswer(resultCode, data)
        }
    }

    private fun createOffQrIntent(check: Check): Intent {
        return Intent().apply {
            //putExtras(clientInfo.toBundle()) todo надо ли?
        }.apply {
            action = ACTION_OFF_QR
            putExtra(KEY_CASH_DOCUMENT_TYPE, check.docType.name) // todo по нему определять продажа или возврат
            putExtras(check.toBundle())
        }
    }

    private fun handleOffQRAnswer(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
//            val check = modulKassaClient.checkManager().parsePrintCheckSuccess(data ?: Intent())
//            check?.let {
            Toast.makeText(
                this@MainActivity,
                "Успех",
                Toast.LENGTH_LONG
            ).show()
//            }
        } else {
//            val resultError = modulKassaClient.checkManager().parsePrintCheckError(data ?: Intent())
            Toast.makeText(
                this@MainActivity,
                "Не успех",
                Toast.LENGTH_LONG
            ).show()
        }
    }
}