package ru.modulkassa.pos.integration

import android.content.Intent
import android.os.Bundle
import android.os.DeadObjectException
import android.os.Parcel
import android.os.Parcelable
import android.os.Parcelable.Creator
import ru.modulkassa.pos.integration.service.IPluginServiceCallback
import timber.log.Timber
import java.lang.ref.WeakReference

/**
 * Для передачи ссылки на `callback` компоненту, который будет его использовать
 * Положить ссылку на `callback` в Bundle нельзя, поэтому используем `Parcelable` класс
 */
data class PluginServiceCallbackHolder(
    private val callback: IPluginServiceCallback
) : Parcelable {

    fun get() = CallbackWrapper(callback, rescueAnswerReceiver?.get())

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

        var rescueAnswerReceiver: WeakReference<RescueAnswerReceiver>? = null
    }
}

class CallbackWrapper(
    private val origin: IPluginServiceCallback,
    private val rescueAnswerReceiver: RescueAnswerReceiver?
) : IPluginServiceCallback by origin {

    override fun succeeded(data: Bundle?) {
        Timber.i("CallbackWrapper.succeeded($data)")
        try {
            origin.succeeded(data)
        } catch (e: DeadObjectException) {
            Timber.i("CallbackWrapper DeadObjectException")
            rescueAnswerReceiver?.succeeded(data)
        }
    }

    override fun failed(message: String?, extraData: Bundle?) {
        try {
            origin.failed(message, extraData)
        } catch (e: DeadObjectException) {
            rescueAnswerReceiver?.failed(message, extraData)
        }
    }

    override fun cancelled() {
        try {
            origin.cancelled()
        } catch (e: DeadObjectException) {
            rescueAnswerReceiver?.cancelled()
        }
    }

}

interface RescueAnswerReceiver {
    fun succeeded(data: Bundle?)
    fun failed(message: String?, extraData: Bundle?)
    fun cancelled()
}