package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

class PrintMoneyCheckAction(
    private val moneyCheck: MoneyCheck
) : Action<Boolean> {

    private val gson = Gson()

    companion object {
        // TODO: сделать это публичным и использовать в executor-е МодульКассы
        private const val NAME = "PRINT_MONEY_CHECK"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?){
        kassa.executeOperation(NAME, gson.toJson(moneyCheck), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(true)
            }

            override fun failed(message: String, extraData: String?) {
                @Suppress("EmptyClassBlock")
                val type = object : TypeToken<Map<String, String>>() {
                }.type
                callback?.failed(message, gson.fromJson(extraData, type))
            }
        })
    }

}