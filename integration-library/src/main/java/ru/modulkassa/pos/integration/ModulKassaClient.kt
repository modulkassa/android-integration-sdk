package ru.modulkassa.pos.integration

import android.content.Context
import ru.modulkassa.pos.integration.core.manager.CheckManager
import ru.modulkassa.pos.integration.core.manager.IntentFactory
import ru.modulkassa.pos.integration.core.manager.RealCheckManager
import ru.modulkassa.pos.integration.core.manager.RealShiftManager
import ru.modulkassa.pos.integration.core.manager.ShiftManager

/**
 * Клиент для работы с приложением Модулькасса
 */
class ModulKassaClient(
    context: Context
) {

    private val appContext = context.applicationContext

    private val intentFactory = IntentFactory(appContext)
    private val checkManager = RealCheckManager(intentFactory)
    private val shiftManager = RealShiftManager(intentFactory)

    /**
     * Возвращает [CheckManager] для регистрации чеков
     */
    fun checkManager(): CheckManager = checkManager

    /**
     * Возвращает [ShiftManager] для открытия/закрытия смен
     */
    fun shiftManager(): ShiftManager = shiftManager
}