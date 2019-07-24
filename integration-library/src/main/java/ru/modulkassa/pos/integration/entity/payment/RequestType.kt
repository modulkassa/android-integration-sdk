package ru.modulkassa.pos.integration.entity.payment

/**
 * Тип запроса к платежной системе
 */
enum class RequestType {
    /**
     * Оплата
     */
    PAY,
    /**
     * Возврат
     */
    REFUND,
    /**
     * Отмена транзакции
     */
    CANCEL,
    /**
     * Сверка итогов
     */
    RECONCILIATION
}

/**
 * Параметры сериализации для типа запроса
 */
object RequestTypeSerialization {
    const val KEY = "request_type"
}