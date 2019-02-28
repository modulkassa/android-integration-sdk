package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import kotlin.test.assertFails

@RunWith(ParameterizedRobolectricTestRunner::class)
class LoyaltyPositionImpactTest_FieldIsAbsent(
    private val invalidFieldKey: String
) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "Key \"{0}\" is absent")
        fun data() = listOf(
            arrayOf("impact/id/position_id"),
            arrayOf("impact/id/price"),
            arrayOf("impact/id/quantity")
        )
    }

    @Test
    fun FromBundle_FieldIsAbsent_ThrowsException() {
        val bundle = Bundle().apply {
            putString("impact/id/position_id", "pos-id")
            putString("impact/id/price", "0.01")
            putString("impact/id/quantity", "0.001")
        }
        bundle.remove(invalidFieldKey)

        val throwable = assertFails { LoyaltyPositionImpact.fromBundle("id", bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
    }

}