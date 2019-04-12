package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import ru.modulkassa.pos.integration.entity.kkt.Shift
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда получения информации о последней смене
 */
class GetShiftInfoAction : Action<Shift> {

    private val gson: Gson = Gson()

    companion object {
        private const val NAME = "GET_SHIFT_INFO"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Shift>?) {
        kassa.executeOperation(NAME, "", object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                if (data == null) {
                    callback?.succeed(null)
                } else {
                    callback?.succeed(gson.fromJson(data, Shift::class.java))
                }
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }

}