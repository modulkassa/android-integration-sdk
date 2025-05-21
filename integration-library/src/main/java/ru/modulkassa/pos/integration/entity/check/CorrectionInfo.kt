package ru.modulkassa.pos.integration.entity.check

data class CorrectionInfo(
    /**
     * Причина коррекции
     */
    val reason: CorrectionReason,
    /**
     * Дата документа основания в формате ISO 8601
     * Например: 2022-05-19T10:25:10
     */
    val documentDate: String,
    /**
     * Номер документа основания
     */
    val documentNum: String?,
    /**
     * Фискальный признак документа основания
     */
    val fiscalSign: String?
)
