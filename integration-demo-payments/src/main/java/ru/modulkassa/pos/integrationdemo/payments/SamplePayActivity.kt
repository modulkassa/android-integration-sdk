package ru.modulkassa.pos.integrationdemo.payments

import android.os.Bundle
import android.os.Handler
import androidx.appcompat.app.AppCompatActivity
import kotlinx.android.synthetic.main.activity_sample_pay.amount
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.entity.payment.PayRequest
import ru.modulkassa.pos.integration.entity.payment.PayResult
import java.util.UUID

class SamplePayActivity : AppCompatActivity() {

    companion object {
        const val KEY_DATA = "data"
        const val TIMEOUT: Long = 3000
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_sample_pay)

        intent.getBundleExtra(KEY_DATA)?.let {
            val payRequest = PayRequest.fromBundle(it)
            amount.text = payRequest.amount.toString()

            // Ответим через 3 сек, чтобы показать длительное выполнение запроса
            Handler().postDelayed(
                {
                    PluginServiceCallbackHolder.getFromIntent(intent, applicationContext)?.get()?.succeeded(
                        PayResult(UUID.randomUUID().toString(), listOf()).toBundle()
                    )

                    // после завершения обработки нужно закрыть активити
                    finish()
                },
                TIMEOUT
            )
        }

    }
}
