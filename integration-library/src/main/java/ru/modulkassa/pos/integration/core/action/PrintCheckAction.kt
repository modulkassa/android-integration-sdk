package ru.modulkassa.pos.integration.core.action

import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback
import ru.modulkassa.pos.integration.entity.check.Check
import ru.modulkassa.pos.integration.entity.check.FiscalInfo
import com.google.gson.Gson
import com.google.gson.reflect.TypeToken

/**
 * Команда печати чека
 */
class PrintCheckAction(
    private val check: Check
) : Action<FiscalInfo> {

    private val gson = Gson()

    override fun execute(kassa: IModulKassa, callback: ActionCallback<FiscalInfo>?) {
        kassa.executeOperation("PRINT_CHECK", gson.toJson(check),
            object : IModulKassaOperationCallback.Stub() {
                override fun succeed(data: String) {
                    callback?.succeed(gson.fromJson(data, FiscalInfo::class.java))
                }

                override fun failed(message: String, extraData: String?) {
                    val type = object : TypeToken<Map<String, String>>() {
                    }.type
                    callback?.failed(message, gson.fromJson(extraData, type))
                }
            })
    }
}