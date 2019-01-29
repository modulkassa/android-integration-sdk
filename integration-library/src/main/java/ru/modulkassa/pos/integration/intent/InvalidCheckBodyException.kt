package ru.modulkassa.pos.integration.intent

/**
 * Некорректное тело чека
 */
class InvalidCheckBodyException(
    /**
     * Полученное тело чека
     */
    val body: String?,
    /**
     * Причина ошибки
     */
    cause: Throwable
): Exception(cause)
