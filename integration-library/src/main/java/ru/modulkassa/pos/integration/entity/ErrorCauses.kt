package ru.modulkassa.pos.integration.entity

/**
 * Причины ошибок
 */
class ErrorCauses {
    companion object {
        /**
         * Длительность смены превысила разрешенное значение. Необходимо закрыть смену
         */
        const val SHIFT_TOO_LONG = "SHIFT_TOO_LONG"
        /**
         * Смена закрыта. Для выполнения операции откройте смену
         */
        const val SHIFT_CLOSED = "SHIFT_CLOSED"

        /**
         * Ошибка произошла на этапе регистрации чека в ФН
         */
        const val CHECK_REGISTRATION_FAILED = "CHECK_REGISTRATION_FAILED"

        /**
         * Ошибка произошла на этапе работы с платежным терминалом
         */
        const val PAYMENT_FAILED = "PAYMENT_FAILED"
    }
}