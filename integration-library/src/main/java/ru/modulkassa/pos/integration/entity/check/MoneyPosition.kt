package ru.modulkassa.pos.integration.entity.check

import ru.modulkassa.pos.integration.entity.payment.PaymentType
import java.math.BigDecimal

/**
 * Тип оплаты
 */
data class MoneyPosition(
    /**
     * Тип оплаты
     */
    var paymentType: PaymentType,
    /**
     * Сумма выбранного типа оплаты
     */
    var sum: BigDecimal,
    /**
     * Идентификатор оплаты из внешней системы, если чек был оплачен с помощью внешнего платежного
     * сервиса
     */
    val linkedId: String? = null
)