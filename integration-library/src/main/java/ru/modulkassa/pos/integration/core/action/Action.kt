package ru.modulkassa.pos.integration.core.action

import ru.modulkassa.pos.integration.service.IModulKassa

/**
 * Действие/команда, которое должна выполнить МодульКасса
 */
interface Action<out T> {
    /**
     * Выполнить команду
     */
    fun execute(kassa: IModulKassa, callback: ActionCallback<T>?)
}