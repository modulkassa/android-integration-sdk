package ru.modulkassa.pos.integration.entity.payment

import java.math.BigDecimal

/**
 * Информация для платежа/возврата с использованием электронного сертификата
 */
data class CertificateDetails(
    /**
     * Идентификатор сформированной корзины от НСПК
     * Формат: 24 цифры
     */
    val basketId: String,
    /**
     * Сумма оплаты/возврата по электронному сертификату
     */
    val certificateAmount: BigDecimal
)