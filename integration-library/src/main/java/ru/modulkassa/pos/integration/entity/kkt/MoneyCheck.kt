package ru.modulkassa.pos.integration.entity.kkt

import java.math.BigDecimal

/**
 * Чек внесения/выема денег
 */
data class MoneyCheck(
    /**
     * Тип чека
     */
    val type: MoneyCheckType,
    /**
     * Сумма
     */
    val amount: BigDecimal,
    /**
     * Произвольный текст для печати на чеке
     */
    val text: List<String> = emptyList()
)

/**
 * Тип денежного чека
 */
enum class MoneyCheckType {
    /**
     * Внесение
     */
    INCOME,
    /**
     * Выем
     */
    OUTGO
}