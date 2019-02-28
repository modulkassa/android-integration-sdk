package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.check.Employee
import ru.modulkassa.pos.integration.entity.kkt.ShiftDescription
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда открытия смены
 */
class OpenShiftAction(
    private val employee: Employee
) : Action<ShiftDescription> {

    private val gson: Gson = Gson()

    companion object {
        private const val NAME = "OPEN_SHIFT"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<ShiftDescription>?) {
        kassa.executeOperation(NAME, gson.toJson(employee), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String) {
                callback?.succeed(gson.fromJson(data, ShiftDescription::class.java))
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }

}