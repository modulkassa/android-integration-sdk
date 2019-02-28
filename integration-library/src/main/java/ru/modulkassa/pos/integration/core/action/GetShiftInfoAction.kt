package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.kkt.ShiftDescription
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда получения информации о смене
 */
class GetShiftInfoAction : Action<ShiftDescription> {

    private val gson: Gson = Gson()

    companion object {
        private const val NAME = "GET_SHIFT_INFO"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<ShiftDescription>?) {
        kassa.executeOperation(NAME, "", object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(gson.fromJson(data, ShiftDescription::class.java))
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }

}