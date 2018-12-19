package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.check.TextReport
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Команда печати текста
 */
class PrintTextAction(
    private val report: TextReport
) : Action<Boolean> {

    private val gson = Gson()

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?) {
        kassa.executeOperation("PRINT_TEXT", gson.toJson(report), object : IModulKassaOperationCallback.Stub() {
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