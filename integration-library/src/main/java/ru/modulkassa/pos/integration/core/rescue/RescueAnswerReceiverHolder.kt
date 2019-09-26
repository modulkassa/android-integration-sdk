package ru.modulkassa.pos.integration.core.rescue

import java.lang.ref.WeakReference

object RescueAnswerReceiverHolder {

    private var receiverReference: WeakReference<RescueAnswerReceiver>? = null

    var receiver: RescueAnswerReceiver?
    set(value) {
        if (value != null) {
            receiverReference = WeakReference(value)
        }
    }
    get() = receiverReference?.get()

}