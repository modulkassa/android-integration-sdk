package ru.modulkassa.pos.integrationtest

import android.os.Bundle
import androidx.appcompat.app.AppCompatActivity
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyPositionImpact
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyResult
import ru.modulkassa.pos.integrationtest.databinding.ActivityLoyaltySampleBinding
import java.math.BigDecimal
import java.util.UUID

class SampleLoyaltyActivity : AppCompatActivity() {

    companion object {
        const val LOYALTY_DATA = "LOYALTY_DATA"
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        val binding = ActivityLoyaltySampleBinding.inflate(layoutInflater)
        setContentView(binding.root)

        binding.success.setOnClickListener {

            intent.getBundleExtra(LOYALTY_DATA)?.let {
                val loyaltyRequest = LoyaltyRequest.fromBundle(it)
                val impacts = loyaltyRequest.positions.mapIndexed { index, position ->
                    LoyaltyPositionImpact(
                        id = UUID.randomUUID().toString(),
                        positionId = position.id,
                        price = BigDecimal.TEN.multiply(BigDecimal.valueOf(index.toLong() + 1)),
                        quantity = position.quantity
                    )
                }

                PluginServiceCallbackHolder.getFromIntent(intent, applicationContext)?.get()?.succeeded(
                    LoyaltyResult(
                        data = "SampleLoyalty",
                        impacts = impacts,
                        printableData = "Дополнительная информация для печати после фискального чека от системы лояльности"
                    ).toBundle()
                )
            }
            // после завершения обработки нужно закрыть активити
            finish()
        }

        binding.failed.setOnClickListener {
            PluginServiceCallbackHolder.getFromIntent(intent, applicationContext)?.get()?.failed(
                "Some error!",
                Bundle.EMPTY
            )
            finish()
        }

        binding.cancelled.setOnClickListener {
            PluginServiceCallbackHolder.getFromIntent(intent, applicationContext)?.get()?.cancelled()
            finish()
        }
    }

}
