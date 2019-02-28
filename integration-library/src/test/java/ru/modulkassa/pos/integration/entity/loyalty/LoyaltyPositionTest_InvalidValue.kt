package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import kotlin.test.assertFails

@RunWith(ParameterizedRobolectricTestRunner::class)
class LoyaltyPositionTest_InvalidValue(
    private val invalidFieldKey: String,
    private val errorMessage: String
) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "Invalid value for key \"{0}\"")
        fun data() = listOf(
            arrayOf("position/id/type", "Некорректное значение"),
            arrayOf("position/id/measure", "Некорректное значение"),
            arrayOf("position/id/price", "Некорректный формат числового значения"),
            arrayOf("position/id/quantity", "Некорректный формат числового значения")
        )
    }

    @Test
    fun FromBundle_InvalidValue_ThrowsException() {
        val bundle = Bundle().apply {
            putString("position/id/invent_code", "code")
            putString("position/id/barcode", "barcode")
            putString("position/id/name", "name")
            putString("position/id/type", "INVENTORY")
            putString("position/id/measure", "PCS")
            putString("position/id/price", "1.23")
            putString("position/id/quantity", "0.123")
        }
        bundle.putString(invalidFieldKey, "")

        val throwable = assertFails { LoyaltyPosition.fromBundle("id", bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(LoyaltyInvalidStructureException::class.java))
        assertThat(throwable.message, equalTo(errorMessage))
    }

}