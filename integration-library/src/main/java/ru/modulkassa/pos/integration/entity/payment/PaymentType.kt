package ru.modulkassa.pos.integration.entity.payment

/**
 * Тип оплаты
 */
enum class PaymentType {
    /**
     * Оплата наличными
     */
    CASH,
    /**
     * Оплата картой
     */
    CARD,
    /**
     * Предоплата (аванс)
     */
    PREPAID
}