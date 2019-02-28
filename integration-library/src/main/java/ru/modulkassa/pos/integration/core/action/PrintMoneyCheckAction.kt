package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда внесения/выема денег в ККТ
 */
class PrintMoneyCheckAction(
    private val moneyCheck: MoneyCheck
) : Action<Boolean> {

    private val gson: Gson = Gson()

    companion object {
        private const val NAME = "PRINT_MONEY_CHECK"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?) {
        kassa.executeOperation(NAME, gson.toJson(moneyCheck), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(true)
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }
}