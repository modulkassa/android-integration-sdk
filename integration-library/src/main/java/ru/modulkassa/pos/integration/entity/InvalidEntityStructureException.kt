package ru.modulkassa.pos.integration.entity

/**
 * Некорректная структура сущности обмена между Модулькассой и внешним приложением
 */
class InvalidEntityStructureException(
    /**
     * Текстовое сообщение об ошибке
     */
    message: String,
    /**
     * Причина
     */
    cause: Throwable
) : Exception(message, cause)