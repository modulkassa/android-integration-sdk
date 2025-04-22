package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.Bundable
import ru.modulkassa.pos.integration.entity.GsonFactory
import ru.modulkassa.pos.integration.entity.check.Employee
import java.math.BigDecimal

/**
 * Запрос на создание заказа во внешней crm системе
 */
data class CreateOrderToCrmRequest(
    /**
     * Идентификатор торговой точки
     */
    val retailPointId: String,
    /**
     * Список позиций
     */
    val positions: List<CrmPosition>,
    /**
     * Является ли сотрудник, открывший чек, администратором
     */
    val isEmployeeAdmin: Boolean,
) : Bundable, CrmRequest {

    companion object {
        private const val KEY_RETAIL_POINT_ID = "retailPointId"
        private const val KEY_POSITIONS = "positions"
        private const val KEY_IS_ADMIN = "isEmployeeAdmin"


        private val gson = GsonFactory.provide()

        fun fromBundle(bundle: Bundle): CreateOrderToCrmRequest {
            return CreateOrderToCrmRequest(
                retailPointId = bundle.getString(KEY_RETAIL_POINT_ID) ?: "",
                positions = gson.fromJson(
                    bundle.getString(KEY_POSITIONS),
                    object : TypeToken<List<CrmPosition>>() {}.type
                ),
                isEmployeeAdmin = bundle.getBoolean(KEY_IS_ADMIN)
            )
        }
    }

    override val requestType: CrmRequestType
        get() = CrmRequestType.CREATE

    override fun toBundle(): Bundle {
        return Bundle().apply {
            putString(CrmRequestTypeSerialization.KEY, requestType.name)
            putString(KEY_RETAIL_POINT_ID, retailPointId)
            putString(KEY_POSITIONS, gson.toJson(positions))
            putBoolean(KEY_IS_ADMIN, isEmployeeAdmin)
        }
    }
}

data class CrmPosition(
    /**
     * Штрихкод товара
     */
    var barcode: String,
    /**
     * Количество товара
     */
    var quantity: BigDecimal,
    /**
     * Цена товара
     */
    var price: BigDecimal,
    /**
     * Список модификаторов
     */
    val modifiers: List<CrmModifier>
)

data class CrmModifier(
    /**
     * Наименование модификатора
     */
    val name: String,
    /**
     * Цена модификатора
     */
    val price: BigDecimal
)