package ru.modulkassa.pos.integration.entity

/**
 * Типы ошибок
 */
enum class ErrorType {
    /**
     * Данные запроса невалидны
     */
    INVALID_DATA,
    /**
     * Запрос не может быть выполнен в текущем состоянии. Например, регистрация чека при закрытой смене
     */
    UNACCEPTED_STATE,
    /**
     * Ошибка выполнения операции
     */
    EXECUTION_FAILED,
    /**
     * Операция была отменена кассиром
     */
    CANCELLED,
    /**
     * Неизвестный тип
     */
    UNKNOWN
}