package ru.modulkassa.pos.integration.entity.check

/**
 * Единицы измерения
 */
enum class Measure {
    /**
     * Штуки
     */
    PCS,
    /**
     * Миллилитры
     */
    MLTR,
    /**
     * Литры
     */
    LTR,
    /**
     * Граммы
     */
    G,
    /**
     * Килограммы
     */
    KG,
    /**
     * Тонны
     */
    T,
    /**
     * Сантиметры
     */
    CM,
    /**
     * Дециметры
     */
    DM,
    /**
     * Метры
     */
    M,
    /**
     * Кв. сантиметры
     */
    CM2,
    /**
     * Кв. дециметры
     */
    DM2,
    /**
     * Кв. метры
     */
    M2,
    /**
     * Куб. метры
     */
    M3,
    /**
     * Секунда
     */
    SECOND,
    /**
     * Минута
     */
    MINUTE,
    /**
     * Час
     */
    HOUR,
    /**
     * Сутки
     */
    DAYS,
    /**
     * Килобайты
     */
    KBYTE,
    /**
     * Мегабайты
     */
    MBYTE,
    /**
     * Гигабайты
     */
    GBYTE,
    /**
     * Терабайты
     */
    TBYTE,
    /**
     * Гигакалории
     */
    GCAL,
    /**
     * Киловатт час
     */
    KWH,
    /**
     * Другое
     */
    OTHER;
}