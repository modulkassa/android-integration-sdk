package ru.modulkassa.pos.integration.entity.kkt

import android.os.Bundle
import com.google.gson.Gson
import com.google.gson.JsonSyntaxException
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.InvalidEntityStructureException
import ru.modulkassa.pos.integration.entity.check.Employee
import java.math.BigDecimal

/**
 * Чек внесения/выема денег
 */
data class MoneyCheck(
    /**
     * Тип чека
     */
    val type: MoneyCheckType,
    /**
     * Сумма
     */
    val amount: BigDecimal,
    /**
     * Описание операции
     */
    val text: List<String> = emptyList(),
    /**
     * Данные кассира
     */
    val employee: Employee
) : Bundable {
    companion object {
        const val KEY_TYPE = "integration.entity.moneycheck.type"
        const val KEY_AMOUNT = "integration.entity.moneycheck.amount"
        const val KEY_TEXT = "integration.entity.moneycheck.text"
        private val gson = Gson()

        @Throws(IllegalArgumentException::class)
        fun fromBundle(bundle: Bundle): MoneyCheck {
            val typeText = bundle.getString(KEY_TYPE) ?: ""
            val amountText = bundle.getString(KEY_AMOUNT, "")
            val text = bundle.getStringArrayList(KEY_TEXT) ?: emptyList<String>()
            val employee = Employee.fromBundle(bundle) ?: Employee("")
            return try {
                MoneyCheck(MoneyCheckType.valueOf(typeText), BigDecimal(amountText), text, employee)
            } catch (error: IllegalArgumentException) {
                throw InvalidEntityStructureException("Некорректный тип операции", error)
            } catch (error: NumberFormatException) {
                throw InvalidEntityStructureException("Некорректная сумма внесения/выема", error)
            }
        }

        fun fromJson(json: String): MoneyCheck {
            try {
                val moneyCheck = gson.fromJson(json, MoneyCheck::class.java)
                return moneyCheck ?: throw IllegalStateException("Чек не может быть пустым")
            } catch (error: JsonSyntaxException) {
                throw InvalidEntityStructureException("Некорректная структура чека внесения/выема", error)
            }
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(KEY_TYPE, type.toString())
            putString(KEY_AMOUNT, amount.toPlainString())
            putStringArrayList(KEY_TEXT, ArrayList(text))
            putAll(employee.toBundle())
        }
    }

    fun toJson(): String = gson.toJson(this)

}

/**
 * Тип денежного чека
 */
enum class MoneyCheckType {
    /**
     * Внесение
     */
    INCOME,
    /**
     * Выем
     */
    OUTGO
}