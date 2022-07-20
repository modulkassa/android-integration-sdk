package ru.modulkassa.pos.integration.entity.check

/**
 * Тип документа
 */
enum class DocumentType {
    /**
     * Продажа
     */
    SALE,
    /**
     * Возврат
     */
    RETURN,
    /**
     * Коррекция прихода
     */
    SALE_CORRECTION,
    /**
     * Коррекция возврата прихода
     */
    SALE_RETURN_CORRECTION;
}