package ru.modulkassa.pos.integration.entity.check

import java.math.BigDecimal

/**
 * Фискальная информация документа
 */
data class FiscalInfo(
    /**
     * Номер чека
     */
    val checkNumber: Int,
    /**
     * Дата и время закрытия чека в формате ISO 8601
     */
    val date: String,
    /**
     * Метка документа из ФН
     */
    val fnDocMark: BigDecimal,
    /**
     * Номер документа из ФН
     */
    val fnDocNumber: Int,
    /**
     * Номер ФН
     */
    val fnNumber: String,
    /**
     * Номер ККТ
     */
    val kktNumber: String,
    /**
     * Данные для QR-кода
     */
    val qr: String,
    /**
     * Номер смены
     */
    val shiftNumber: Int,
    /**
     * Сумма
     */
    val sum: BigDecimal
)