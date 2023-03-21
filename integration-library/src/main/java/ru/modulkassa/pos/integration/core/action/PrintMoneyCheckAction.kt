package ru.modulkassa.pos.integration.core.action

import ru.modulkassa.pos.integration.entity.kkt.MoneyCheck
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

class PrintMoneyCheckAction(
    private val moneyCheck: MoneyCheck
) : Action<Boolean> {

    companion object {
        private const val NAME = "PRINT_MONEY_CHECK"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?) {
        kassa.executeOperation(NAME, moneyCheck.toJson(), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(true)
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }

}