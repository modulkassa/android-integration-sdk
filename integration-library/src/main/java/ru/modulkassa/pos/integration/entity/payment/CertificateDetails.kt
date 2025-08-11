package ru.modulkassa.pos.integration.entity.payment

import com.google.gson.annotations.SerializedName
import java.math.BigDecimal

/**
 * Информация для платежа/возврата с использованием электронного сертификата
 */
data class CertificateDetails(
    /**
     * Идентификатор сформированной корзины от НСПК
     * Формат: 24 цифры
     */
    @SerializedName("basketId")
    val basketId: String,
    /**
     * Сумма оплаты/возврата по электронному сертификату
     */
    @SerializedName("certificateAmount")
    val certificateAmount: BigDecimal
)