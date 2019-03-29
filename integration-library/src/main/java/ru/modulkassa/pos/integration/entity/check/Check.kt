package ru.modulkassa.pos.integration.entity.check

import android.os.Bundle
import com.google.gson.Gson
import ru.modulkassa.pos.integration.ModulKassaClient
import ru.modulkassa.pos.integration.entity.Bundable
import java.math.BigDecimal

/**
 * Документ из другого приложения, который необходимо фискализировать
 */
data class Check(
    /**
     * Идентификатор документа
     */
    var id: String,
    /**
     * Тип документа
     */
    var docType: DocumentType,
    /**
     * Печатать чек или нет
     */
    var printReceipt: Boolean = false,
    /**
     * Адрес электронной почты или телефона покупателя
     *
     * Допустимые форматы:
     * +{цифры}
     * {символы}@{символы}
     * Допустимые символы:
     * Цифры от 0 до 9.
     * Буквы латинского алфавита в любом регистре.
     * Общие разделители и специальные символы.
     */
    var email: String?,
    /**
     * Позиции документа/чека
     */
    var inventPositions: List<InventPosition>,
    /**
     * Оплаты
     */
    var moneyPositions: List<MoneyPosition>,
    /**
     * ФИО кассира. Согласно ФЗ-54 необходимо указывать ФИО работника и его должность
     */
    var employee: String = "",
    /**
     * Тип СНО
     */
    var taxMode: TaxationMode,
    /**
     * Идентификатор чека в рамках МодульКассы. Устанавливать не обязательно. Будет заполняться,
     * если чек приходит от приложения МодульКассы
     */
    var modulKassaId: String? = null,
    /**
     * Текст, который должен быть распечатан на чеке
     */
    var textToPrint: String? = null,
    /**
     * Информация о зарегистрированном чеке из ФН
     */
    var fiscalInfo: FiscalInfo? = null,
    /**
     * Информация о платежном агенте
     */
    var agentInformation: CheckLevelAgentInformation? = null
) : Bundable {

    companion object {
        const val KEY_SERIALIZED_CHECK = "integration.entity.check.serialized_check"
        private val gson = Gson()

        fun fromBundle(data: Bundle): Check? {
            return gson.fromJson(data.getString(KEY_SERIALIZED_CHECK), Check::class.java)
        }
    }

    override fun toBundle(): Bundle {
        return Bundle().also {
            val serializedCheck = gson.toJson(Check@this)
            it.putString(KEY_SERIALIZED_CHECK, serializedCheck)
        }
    }


}