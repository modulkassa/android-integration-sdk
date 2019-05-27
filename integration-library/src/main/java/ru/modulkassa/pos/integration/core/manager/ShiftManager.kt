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
     */
    fun createOpenShiftIntent(employee: Employee): Intent

    /**
     * Подготовить `Intent` для закрытия смены
     */
    fun createCloseShiftIntent(employee: Employee): Intent

    /**
     * Получение информации об ошибке запроса
     */
    fun parseShiftActionResult(data: Intent): ResultError

    /**
     * Подготовить `Intent` для печати x-отчета
     */
    fun createXReportIntent(): Intent

}