package ru.modulkassa.pos.integration.entity.check

/**
 * Текст для печати на принтере
 */
data class TextReport(
    /**
     * Список строк отчета
     */
    val lines: ArrayList<ReportLine>
)

/**
 * Строка текстового отчета
 */
data class ReportLine(
    /**
     * Данные для печати
     */
    val data: String,
    /**
     * Тип строки (обычный текст, Qr-code)
     */
    val type: ReportLineType
)

/**
 * Тип строки отчета
 */
enum class ReportLineType {
    /**
     * Напечатать данные текстом
     */
    TEXT,
    /**
     * Напечатать данные в виде QR-code
     */
    QR
}