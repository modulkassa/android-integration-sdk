package ru.modulkassa.pos.integration.entity.payment;

/**
 * Класс, содержащий строку-разделитиль для слипов
 * Используется для формирования слип-документа, когда терминал возвращает оба слипа (для кассира и для покупателя)
 */
class Slip {
    companion object {
        /**
         * Строка-разделитель для слипов
         */
        const val DELIMITER_VALUE = "\t\t"
    }
}