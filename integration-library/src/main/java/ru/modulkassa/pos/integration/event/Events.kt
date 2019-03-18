package ru.modulkassa.pos.integration.event

/**
 * События, которые могут получать другие приложения
 */
class Events {
    companion object {
        /**
         * Чек изменен
         */
        const val CHECK_CHANGED = "ru.modulkassa.pos.events.CHECK_MODIFIED"
        /**
         * Чек отменен
         */
        const val CHECK_CANCELLED = "ru.modulkassa.pos.events.CHECK_CANCELLED"
        /**
         * Фискализация чека завершена
         */
        const val CHECK_CLOSED = "ru.modulkassa.pos.events.CHECK_CLOSED"
        /**
         * Переход пользователя на основной экран кассы, после завершения фискализации чека
         */
        const val UI_RETURN_TO_MAIN_SCREEN = "ru.modulkassa.pos.events.UI_RETURN_TO_MAIN_SCREEN"
    }
}