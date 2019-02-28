package ru.modulkassa.pos.integration.core.handler

import android.content.Context
import com.nhaarman.mockito_kotlin.anyOrNull
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.eq
import com.nhaarman.mockito_kotlin.mock
import com.nhaarman.mockito_kotlin.verify
import org.junit.Test
import ru.modulkassa.pos.integration.PluginServiceCallbackHolder
import ru.modulkassa.pos.integration.entity.loyalty.LoyaltyRequest
import ru.modulkassa.pos.integration.lib.R
import ru.modulkassa.pos.integration.service.IPluginServiceCallback

class LoyaltyOperationHandlerTest {

    @Test
    fun Handle_InvalidRequest_CallsFailed() {
        val context = mock<Context> {
            on { getString(R.string.loyalty_request_is_invalid) } doReturn "request is invalid"
        }
        val handler = object : LoyaltyOperationHandler(context) {
            override fun handleLoyaltyRequest(loyaltyRequest: LoyaltyRequest, callback: PluginServiceCallbackHolder) {
            }
        }
        val callback = mock<IPluginServiceCallback>()
        val callbackHolder = mock<PluginServiceCallbackHolder> {
            on { get() } doReturn callback
        }

        handler.handle(mock(), callbackHolder)

        verify(callback).failed(eq("request is invalid"), anyOrNull())
    }

}