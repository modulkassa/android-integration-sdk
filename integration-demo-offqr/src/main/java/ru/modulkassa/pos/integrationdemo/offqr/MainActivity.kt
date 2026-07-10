package ru.modulkassa.pos.integrationdemo.offqr

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.ACTION_OFF_QR
import ru.modulkassa.pos.integration.core.ModulKassaApi.Companion.KEY_CASH_DOCUMENT_TYPE
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.off_qr.OffQrResultError
import ru.modulkassa.pos.integration.entity.off_qr.OffQrResultSuccess
import ru.modulkassa.pos.integrationdemo.offqr.databinding.ActivityMainBinding
import java.util.UUID

class MainActivity : AppCompatActivity() {

    private lateinit var binding: ActivityMainBinding

    companion object {
        private const val OFF_QR_REQUEST_CODE = 2
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        binding = ActivityMainBinding.inflate(layoutInflater)
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

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        when (requestCode) {
            OFF_QR_REQUEST_CODE -> handleOffQRAnswer(resultCode, data)
        }
    }

    private fun createOffQrIntent(check: Check): Intent {
        return Intent().apply {
            action = ACTION_OFF_QR
            putExtra(KEY_CASH_DOCUMENT_TYPE, check.docType.name)
            putExtras(check.toBundle())
        }
    }

    private fun handleOffQRAnswer(resultCode: Int, data: Intent?) {
        if (resultCode == Activity.RESULT_OK) {
            /**
             * Обратить внимание, в успешном ответе приходит не сам чек, как при стандартной оплате,
             * потому что результат фискализации чека на этом этапе неизвестен
             *
             * Приходит отдельный формат успешного ответа
             */
            val offQrResultSuccess = data?.extras?.let { OffQrResultSuccess.fromBundle(it) }
            binding.result.text = getString(R.string.result_success, offQrResultSuccess)

        } else {
            /**
             * На ошибку также приходит отдельный формат ошибочного ответа
             */
            val offQrResultError = data?.extras?.let { OffQrResultError.fromBundle(it) }
            binding.result.text = getString(R.string.result_error, offQrResultError)
        }
    }
}