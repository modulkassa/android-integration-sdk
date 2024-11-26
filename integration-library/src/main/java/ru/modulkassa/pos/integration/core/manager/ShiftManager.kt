package ru.modulkassa.pos.integration.core.manager

import android.content.Intent
import ru.modulkassa.pos.integration.entity.ResultError
import ru.modulkassa.pos.integration.entity.check.Employee

/**
 * Отвечает за открытие и закрытие смены
 */
interface ShiftManager {

    /**
     * Подготовить `Intent` для открытия смены
     * @param packageName - имя пакета приложения Модулькасса для вызова
     */
    fun createOpenShiftIntent(employee: Employee, packageName: String? = null): Intent

    /**
     * Подготовить `Intent` для закрытия смены
     * @param packageName - имя пакета приложения Модулькасса для вызова
     */
    fun createCloseShiftIntent(employee: Employee, packageName: String? = null): Intent

    /**
     * Получение информации об ошибке запроса
     */
    fun parseShiftActionResult(data: Intent): ResultError

    /**
     * Подготовить `Intent` для печати x-отчета
     * @param packageName - имя пакета приложения Модулькасса для вызова
     */
    fun createXReportIntent(packageName: String? = null): Intent

}