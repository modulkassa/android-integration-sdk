package ru.modulkassa.pos.integration.entity.kkt

import java.util.Date

/**
 * Информация о смене
 */
data class Shift(
    /**
     * Идентификатор смены
     */
    val id: String,
    /**
     * Номер смены в МодульКассе
     */
    val number: Int,
    /**
     * Дата и время открытия смены
     */
    val openTime: Date?,
    /**
     * Дата и время закрытия смены смены
     */
    val closeTime: Date?
)