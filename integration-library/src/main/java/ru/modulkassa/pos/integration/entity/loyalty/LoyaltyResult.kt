package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.payment.RequestType
import ru.modulkassa.pos.integration.entity.payment.RequestTypeSerialization

/**
 * Результат обращения к системе лояльности
 */
data class LoyaltyResult(
    /**
     * Набор воздействий над списком товаров
     * Воздействия можно применять к одному, нескольким или всем товарам из исходного чека.
     */
    val impacts: List<LoyaltyPositionImpact> = emptyList(),
    /**
     * Данные системы лояльности (информация хранится в БД МодульКассы)
     * Максимальная длина 32000.
     */
    val data: String? = null,
    /**
     * Информация для печати на чеке.
     * Список строк разделенных символом перевода строки `\n`. Если длина строки превышает ширину на чековой ленте,
     * строки переносятся средствами ККТ. Не может состоять из пробельных символов.
     * Максимальная длина строки 32000.
     */
    val printableData: String? = null
) : Bundable {

    companion object {
        const val DATA_KEY = "data"
        const val PRINTABLE_DATA_KEY = "printable_data"
        const val IMPACTS_KEY = "impacts"

        @Throws(LoyaltyInvalidStructureException::class)
        fun fromBundle(bundle: Bundle): LoyaltyResult {
            return LoyaltyResult(
                data = bundle.getString(DATA_KEY),
                printableData = bundle.getString(PRINTABLE_DATA_KEY),
                impacts = (bundle.getStringArrayList(IMPACTS_KEY) ?: arrayListOf())
                    .map { LoyaltyPositionImpact.fromBundle(it, bundle) }
            )
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(DATA_KEY, data)
            putString(PRINTABLE_DATA_KEY, printableData)
            putString(RequestTypeSerialization.KEY, RequestType.LOYALTY.name)

            putStringArrayList(IMPACTS_KEY, ArrayList(impacts.map { it.id }))
            impacts.forEach { putAll(it.toBundle()) }
        }
    }
}