package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.ParameterizedRobolectricTestRunner
import kotlin.test.assertFails

@RunWith(ParameterizedRobolectricTestRunner::class)
class LoyaltyPositionTest_MandatoryFieldIsAbsent(
    private val invalidFieldKey: String
) {

    companion object {
        @JvmStatic
        @ParameterizedRobolectricTestRunner.Parameters(name = "Key \"{0}\" is absent")
        fun data() = listOf(
            arrayOf("position/id/invent_code"),
            arrayOf("position/id/barcode"),
            arrayOf("position/id/name"),
            arrayOf("position/id/type"),
            arrayOf("position/id/measure"),
            arrayOf("position/id/price"),
            arrayOf("position/id/quantity")
        )
    }

    @Test
    fun FromBundle_WithoutMandatoryField_ThrowsException() {
        val bundle = Bundle().apply {
            putString("position/id/invent_code", "code")
            putString("position/id/barcode", "barcode")
            putString("position/id/name", "name")
            putString("position/id/type", "INVENTORY")
            putString("position/id/measure", "PCS")
            putString("position/id/price", "1.23")
            putString("position/id/quantity", "0.123")
        }
        bundle.remove(invalidFieldKey)

        val throwable = assertFails { LoyaltyPosition.fromBundle("id", bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
        assertThat(throwable.message, equalTo("Отсутствует обязательный параметр"))
    }

}