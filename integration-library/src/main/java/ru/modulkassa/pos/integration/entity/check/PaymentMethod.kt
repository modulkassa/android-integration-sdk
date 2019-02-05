package ru.modulkassa.pos.integration.entity.check

/**
 * Признак способа расчета (тег 1214)
 */
enum class PaymentMethod {
    /**
     * Неопределенное значение
     */
    UNDEFINED,
    /**
     * Предоплата 100%
     */
    FULL_PREPAYMENT,
    /**
     * Предоплата
     */
    PREPAYMENT,
    /**
     * Аванс
     */
    ADVANCE,
    /**
     * Полный расчет
     */
    FULL_PAYMENT,
    /**
     * Частичный расчет и кредит
     */
    PARTIAL_PAYMENT,
    /**
     * Передача в кредит
     */
    CREDIT,
    /**
     * Оплата кредита
     */
    CREDIT_PAYMENT
}