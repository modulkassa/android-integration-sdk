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
     * Сумма выбранного типа оплаты. Если сумма больше, чем итог по товарам, то сдача будет посчитана автоматически.
     * Точность должна быть указана до 2х знаков [BigDecimal.setScale(2, BigDecimal.ROUND_DOWN)]
     */
    var sum: BigDecimal,

    /**
     * Включает/выключает стратегию возврата денег через отмену.
     * Чек всегда отменяется целиком
     */
    val refundThroughCancel: Boolean? = false,
    /**
     * Идентификатор оплаты из внешней системы, если чек был оплачен с помощью внешнего платежного
     * сервиса
     */
    val linkedId: String? = null,
    /**
     * Идентификатор платежной системы
     */
    val paymentSystemId: String? = null,
    /**
     * Дополнительная информация от платежного терминала. Формат зависит от драйвера работы с терминалом.
     */
    val extra: String? = null
)