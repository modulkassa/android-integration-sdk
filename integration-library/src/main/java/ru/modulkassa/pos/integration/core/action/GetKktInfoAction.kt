package ru.modulkassa.pos.integration.core.action

import com.google.gson.Gson
import com.google.gson.reflect.TypeToken
import ru.modulkassa.pos.integration.entity.kkt.KktDescription
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

/**
 * Получение информации о ККТ
 */
class GetKktInfoAction : Action<KktDescription> {

    private val gson = Gson()

    override fun execute(kassa: IModulKassa, callback: ActionCallback<KktDescription>?) {
        kassa.executeOperation("get_kkt_info", "{}", object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String) {
                callback?.succeed(gson.fromJson(data, KktDescription::class.java))
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