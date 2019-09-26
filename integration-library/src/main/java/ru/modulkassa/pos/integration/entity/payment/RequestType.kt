package ru.modulkassa.pos.integration.entity.payment

/**
 * Тип запроса
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
    RECONCILIATION,
    /**
     * Расчет скидок и бонусов
     */
    LOYALTY
}

/**
 * Параметры сериализации для типа запроса
 */
object RequestTypeSerialization {
    const val KEY = "request_type"
}