package ru.modulkassa.pos.integration.core.action

/**
 * Результат выполнения команды
 */
interface ActionCallback<in T> {
    /**
     * Команда успешно выполнилась
     */
    fun succeed(result: T?)

    /**
     * Ошибка выполнения команды
     */
    fun failed(message: String, extra: Map<String, Any>?)
}