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
    val extra: String? = null,
    /**
     * Применимо только к электронному типу оплаты (карта и тд)
     * Сообщает МодульКассе о том, что платеж уже обработан (вне приложения МодульКасса) и требуется только его
     * регистрация.
     * Если платеж еще не был обработан, то МодульКасса начнет процесс оплаты через доступные эквайринги.
     * `true` - платеж обработан
     * `false` - требуется оплата через один из доступных эквайрингов
     */
    var paymentProcessed: Boolean = false,
    /**
     * Код авторизации (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val authorizationCode: String? = null,
    /**
     * Номер транзакции (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val transactionNumber: String? = null,
    /**
     * Маскированный номер карты (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val maskedCardNumber: String? = null,
    /**
     * Дата окончания срока действия карты (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val cardExpiryDate: String? = null,
    /**
     * Дата и время транзакции в формате ISO8601 (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val operationDateTime: String? = null,
    /**
     * Номер терминала (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val terminalNumber: String? = null,
    /**
     * Название платежного приложения: visa, mastercard и пр. (для оплаты с помощью внешнего платежного сервиса)
     * Данные могут отсутствовать, если платежный сервис не предоставляет эту информацию.
     */
    val paymentSystemName: String? = null,
    /**
     * Идентификатор мерчанта (для оплаты через терминал)
     * Применимо только к электронному типу оплаты (карта и тд).
     * Параметр является необязательным.
     * В одном чеке может быть только одна безналичная оплата с одним идентификатором мерчанта.
     * На стороне МодульКассы проверки на соответствие ID мерчанта и состава чека нет.
     */
    val merchantId: String? = null
)