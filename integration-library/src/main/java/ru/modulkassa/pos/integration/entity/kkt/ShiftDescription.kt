package ru.modulkassa.pos.integration.entity.kkt

/**
 * Описание текущего состояния смены ККТ
 */
data class ShiftDescription(
    /**
     * Номер смены
     */
    val shiftNumber: Int = 0,
    /**
     * Номер последнего фискального чека
     */
    val lastCheckNumber: Int = 0,
    /**
     * Статус смены
     */
    val shiftStatus: ShiftStatus
)

/**
 * Статус смены
 */
enum class ShiftStatus {
    /**
     * Закрыта
     */
    CLOSED,
    /**
     * Открыта
     */
    OPENED,
    /**
     * Истекла (превысила 24 часа)
     */
    EXPIRED
}
