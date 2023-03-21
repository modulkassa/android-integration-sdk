package ru.modulkassa.pos.integration.core.action

import ru.modulkassa.pos.integration.entity.check.Employee
import ru.modulkassa.pos.integration.service.IModulKassa
import ru.modulkassa.pos.integration.service.IModulKassaOperationCallback

class OpenShiftAction(
    private val employee: Employee
) : Action<Boolean> {

    companion object {
        private const val NAME = "OPEN_SHIFT"
    }

    override fun execute(kassa: IModulKassa, callback: ActionCallback<Boolean>?) {
        kassa.executeOperation(NAME, employee.toJson(), object : IModulKassaOperationCallback.Stub() {
            override fun succeed(data: String?) {
                callback?.succeed(true)
            }

            override fun failed(message: String, extraData: String?) {
                callback?.failed(message, null)
            }
        })
    }
}