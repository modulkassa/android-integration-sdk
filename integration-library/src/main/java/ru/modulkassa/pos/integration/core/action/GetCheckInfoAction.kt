package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.CheckInfoRequest
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда получения информации о чеке, зарегистрированного через команду [PrintCheckAction]
 */
class GetCheckInfoAction(
    private val request: CheckInfoRequest
): Action<Check> {

    private val gson = Gson()

    companion object {
        private const val NAME = "GET_CHECK_INFO"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Check>?) {
        kassa.executeOperation(NAME, gson.toJson(request), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String) {
                callback?.succeed(gson.fromJson(data, Check::class.java))
            }

            override fun failed(message: String, extraData: String?) {
                val type = object : TypeToken<Map<String, String>>() {
                }.type
                callback?.failed(message, gson.fromJson(extraData, type))
            }
        })
    }
}