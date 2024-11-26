package ru.modulkassa.pos.integration.core.manager

import android.content.Intent
import ru.modulkassa.pos.integration.entity.ResultError
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck

/**
 * Отвечает за регистрацию чеков
 */
interface CheckManager {

    /**
     * Подготовить `Intent` для отправки чека на печать
     * @param packageName - имя пакета приложения Модулькасса для вызова
     */
    fun createPrintCheckIntent(check: Check, packageName: String? = null): Intent

    /**
     * Получение успешного результата печати чека
     */
    fun parsePrintCheckSuccess(data: Intent): Check?

    /**
     * Получение информации об ошибке при печати чека
     */
    fun parsePrintCheckError(data: Intent): ResultError

    /**
     * Подготовить `Intent` для создание чека внесения/выема
     * @param packageName - имя пакета приложения Модулькасса для вызова
     */
    fun createMoneyCheckIntent(check: MoneyCheck, packageName: String? = null): Intent

    /**
     * Получение информации об ошибке при создании чека внесения/выема
     */
    fun parseMoneyCheckError(data: Intent): ResultError
}