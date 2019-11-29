package ru.modulkassa.pos.integration

import android.content.Intent
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test

class PluginServiceCallbackHolderTest {

    @Test
    fun GetFromIntent_CallbackIsAbsent_ReturnsNullValue() {
        val intent = mock<Intent>()

        val callbackHolder = PluginServiceCallbackHolder.getFromIntent(intent, mock())

        assertThat(callbackHolder, nullValue())
    }

}