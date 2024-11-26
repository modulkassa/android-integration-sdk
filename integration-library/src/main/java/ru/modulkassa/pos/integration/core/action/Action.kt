package ru.modulkassa.pos.integration.core.action

import ru.modulkassa.pos.integration.service.IModulKassa

/**
 * Действие/команда, которое должна выполнить Модулькасса
 */
interface Action<out T> {
    /**
     * Выполнить команду
     */
    fun execute(kassa: IModulKassa, callback: ActionCallback<T>?)
}