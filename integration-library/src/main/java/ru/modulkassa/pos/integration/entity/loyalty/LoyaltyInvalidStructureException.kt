package ru.modulkassa.pos.integration.entity.loyalty

/**
 * Некорректная структура запроса или ответа системы лояльности
 */
class LoyaltyInvalidStructureException(
    message: String,
    cause: Throwable
) : Exception(message, cause)
