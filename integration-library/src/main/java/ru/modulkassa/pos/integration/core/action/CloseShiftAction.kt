package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.check.Employee
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

class CloseShiftAction(
    private val employee: Employee
) : Action<Boolean> {

    private val gson = Gson()

    companion object {
        private const val NAME = "CLOSE_SHIFT"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?) {
        kassa.executeOperation(NAME, gson.toJson(employee), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(true)
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }
}