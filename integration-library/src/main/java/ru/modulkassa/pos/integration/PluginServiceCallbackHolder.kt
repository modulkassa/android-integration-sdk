package ru.modulkassa.pos.integration

import android.content.Intent
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import ru.modulkassa.pos.integration.service.IPluginServiceCallback

/**
 * Для передачи ссылки на `callback` компоненту, который будет его использовать
 * Положить ссылку на `callback` в Bundle нельзя, поэтому используем `Parcelable` класс
 */
data class PluginServiceCallbackHolder(
    private val callback: IPluginServiceCallback
) : Parcelable {

    fun get(): IPluginServiceCallback = RescueCallbackWrapper(callback, RescueAnswerReceiverHolder.receiver)

    constructor(parcel: Parcel) : this(
        IPluginServiceCallback.Stub.asInterface(parcel.readStrongBinder()))

    override fun writeToParcel(parcel: Parcel, flags: Int) {
        parcel.writeStrongBinder(callback.asBinder())
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

        fun getFromIntent(intent: Intent): PluginServiceCallbackHolder? {
            return intent.getParcelableExtra(KEY_CALLBACK)
        }

    }
}

