package ru.modulkassa.pos.integration.entity.check

/**
 * Тег налоговой ставки
 */
enum class VatTag(val tagValue: Int) {
    /**
     * 1102 – НДС 20%
     */
    TAG_1102(1102),
    /**
     * 1103 – НДС 10%
     */
    TAG_1103(1103),
    /**
     * 1104 – НДС 0%
     */
    TAG_1104(1104),
    /**
     * 1105 – НДС не облагается
     */
    TAG_1105(1105),
    /**
     * 1106 – НДС с рассч. ставкой 20/120
     */
    TAG_1106(1106),
    /**
     * 1107 – НДС с рассч. ставкой 10/110
     */
    TAG_1107(1107),
    /**
     * 1109 – НДС с 5%
     */
    TAG_1109(1109),
    /**
     * 1110 – НДС 7%
     */
    TAG_1110(1110),
    /**
     * 1111 – НДС с рассч. ставкой 5/105
     */
    TAG_1111(1111),
    /**
     * 1112 – НДС с рассч. ставкой 7/107
     */
    TAG_1112(1112);
}