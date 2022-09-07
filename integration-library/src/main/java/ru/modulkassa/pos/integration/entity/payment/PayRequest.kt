package ru.modulkassa.pos.integration.entity.payment

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.RequestType.PAY
import java.math.BigDecimal

/**
 * Информация о платеже, который необходимо провести
 */
data class PayRequest(
    /**
     * Идентификатор чека
     */
    val checkId: String,
    /**
     * Сумма к оплате
     */
    val amount: BigDecimal,
    /**
     * Описание платежа
     */
    val description: String,
    /**
     * Идентификатор мерчанта
     */
    val merchantId: String? = null,
    /**
     * Упрощенный список позиций
     */
    val inventPositions: List<PayRequestPosition>? = null
) : Bundable, PaymentRequest {

    companion object {
        private const val KEY_CHECK_ID = "check_id"
        private const val KEY_AMOUNT = "amount"
        private const val KEY_DESCRIPTION = "description"
        private const val KEY_MERCHANT_ID = "merchant_id"
        private const val KEY_POSITIONS = "positions"
        private val gson = Gson()

        fun fromBundle(bundle: Bundle): PayRequest {
            return PayRequest(
                checkId = bundle.getString(KEY_CHECK_ID, ""),
                amount = BigDecimal(bundle.getString(KEY_AMOUNT, "0")),
                description = bundle.getString(KEY_DESCRIPTION, ""),
                merchantId = bundle.getString(KEY_MERCHANT_ID),
                inventPositions = gson.fromJson<List<PayRequestPosition>>(
                    bundle.getString(KEY_POSITIONS),
                    object : TypeToken<List<PayRequestPosition>>() {}.type
                )
            )
        }
    }

    override val requestType: RequestType
        get() = PAY

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_CHECK_ID, checkId)
            putString(KEY_AMOUNT, amount.toPlainString())
            putString(KEY_DESCRIPTION, description)
            putString(KEY_MERCHANT_ID, merchantId)
            putString(RequestTypeSerialization.KEY, requestType.name)
            putString(KEY_POSITIONS, gson.toJson(inventPositions))
        }
    }

}

/**
 * Информация о позиции чека в платеже
 */
data class PayRequestPosition(
    /**
     * Наименование товара
     */
    var name: String? = null,
    /**
     * Цена товара
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var price: BigDecimal? = null,
    /**
     * Количество товара
     */
    var quantity: BigDecimal? = null
)