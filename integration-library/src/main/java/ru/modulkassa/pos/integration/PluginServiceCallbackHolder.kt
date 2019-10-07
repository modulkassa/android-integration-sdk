package ru.modulkassa.pos.integration

import android.content.Context
import android.content.Intent
import android.content.IntentSender
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import ru.modulkassa.pos.integration.core.rescue.RescueAnswerSenderViaIntent
import ru.modulkassa.pos.integration.core.rescue.RescueCallback
import ru.modulkassa.pos.integration.service.IPluginServiceCallback

/**
 * Для передачи ссылки на `callback` компоненту, который будет его использовать
 * Положить ссылку на `callback` в Bundle нельзя, поэтому используем `Parcelable` класс
 */
data class PluginServiceCallbackHolder(
    private val callback: IPluginServiceCallback,
    private val rescueAnswerSenderEngine: IntentSender?
) : Parcelable {

    private var applicationContext: Context? = null

    fun get(): IPluginServiceCallback {
        return RescueCallback(callback, RescueAnswerSenderViaIntent(applicationContext, rescueAnswerSenderEngine))
    }

    constructor(parcel: Parcel) : this(
        IPluginServiceCallback.Stub.asInterface(parcel.readStrongBinder()),
        IntentSender.readIntentSenderOrNullFromParcel(parcel)
    )

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStrongBinder(callback.asBinder())
        rescueAnswerSenderEngine?.writeToParcel(parcel, flags)
    }

    override fun describeContents(): Int {
        return 0
    }

    fun putToIntent(intent: Intent) {
        intent.putExtra(KEY_CALLBACK, this)
    }

    companion object CREATOR : Creator<PluginServiceCallbackHolder> {

        private const val KEY_CALLBACK = "callback"

        override fun createFromParcel(parcel: Parcel): PluginServiceCallbackHolder {
            return PluginServiceCallbackHolder(parcel)
        }

        override fun newArray(size: Int): Array<PluginServiceCallbackHolder?> {
            return arrayOfNulls(size)
        }

        fun getFromIntent(intent: Intent, applicationContext: Context?): PluginServiceCallbackHolder? {
            return intent.getParcelableExtra<PluginServiceCallbackHolder>(KEY_CALLBACK).apply {
                this@apply.applicationContext = applicationContext?.applicationContext
            }
        }

    }
}

