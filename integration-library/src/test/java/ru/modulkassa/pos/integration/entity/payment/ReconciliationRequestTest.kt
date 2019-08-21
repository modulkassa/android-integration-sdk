package ru.modulkassa.pos.integration.entity.payment

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config

@RunWith(RobolectricTestRunner::class)
@Config(manifest=Config.NONE)
class ReconciliationRequestTest {

    @Test
    fun ToBundle_ByDefault_SavesRequestType() {
        val request = ReconciliationRequest()

        val bundle = request.toBundle()

        assertThat(bundle.getString(RequestTypeSerialization.KEY), equalTo("RECONCILIATION"))
    }

}