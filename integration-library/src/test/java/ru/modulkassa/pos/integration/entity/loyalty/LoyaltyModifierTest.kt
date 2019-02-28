package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal
import kotlin.test.assertFails

@RunWith(RobolectricTestRunner::class)
class LoyaltyModifierTest {

    @Test
    fun FromBundle_ByDefault_ReturnsData() {
        val bundle = Bundle().apply {
            putString("modifier/id/name", "name")
            putString("modifier/id/price", "0.01")
            putString("modifier/id/quantity", "0.001")
        }

        val modifier = LoyaltyModifier.fromBundle("id", bundle)

        assertThat(modifier.name, equalTo("name"))
        assertThat(modifier.price, equalTo(BigDecimal("0.01")))
        assertThat(modifier.quantity, equalTo(BigDecimal("0.001")))
    }

    @Test
    fun FromBundle_WithoutName_ThrowsException() {
        val bundle = Bundle().apply {
            putString("modifier/id/price", "0.01")
            putString("modifier/id/quantity", "0.001")
        }

        val throwable = assertFails { LoyaltyModifier.fromBundle("id", bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
        assertThat(throwable.message, equalTo("Отсутствует обязательный параметр"))
    }

    @Test
    fun FromBundle_WithoutPrice_ThrowsException() {
        val bundle = Bundle().apply {
            putString("modifier/id/name", "name")
            putString("modifier/id/quantity", "0.001")
        }

        val throwable = assertFails { LoyaltyModifier.fromBundle("id", bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
        assertThat(throwable.message, equalTo("Отсутствует обязательный параметр"))
    }

    @Test
    fun FromBundle_InvalidQuantity_ThrowsException() {
        val bundle = Bundle().apply {
            putString("modifier/id/name", "name")
            putString("modifier/id/price", "0.01")
            putString("modifier/id/quantity", "")
        }

        val throwable = assertFails { LoyaltyModifier.fromBundle("id", bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
        assertThat(throwable.message, equalTo("Некорректный формат числового значения"))
    }

}