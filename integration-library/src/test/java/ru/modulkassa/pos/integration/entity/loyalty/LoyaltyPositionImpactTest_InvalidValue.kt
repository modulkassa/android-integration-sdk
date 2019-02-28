package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import kotlin.test.assertFails

@RunWith(ParameterizedRobolectricTestRunner::class)
class LoyaltyPositionImpactTest_InvalidValue(
    private val invalidFieldKey: String,
    private val invaludValue: String
) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "Invalid value \"{1}\" for key \"{0}\"")
        fun data() = listOf(
            arrayOf("impact/id/price", ""),
            arrayOf("impact/id/price", "text"),
            arrayOf("impact/id/quantity", ""),
            arrayOf("impact/id/quantity", "text")
        )
    }

    @Test
    fun FromBundle_FieldIsAbsent_ThrowsException() {
        val bundle = Bundle().apply {
            putString("impact/id/position_id", "pos-id")
            putString("impact/id/price", "0.01")
            putString("impact/id/quantity", "0.001")
        }
        bundle.putString(invalidFieldKey, invaludValue)

        val throwable = assertFails { LoyaltyPositionImpact.fromBundle("id", bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(LoyaltyInvalidStructureException::class.java))
    }

}