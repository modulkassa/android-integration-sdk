package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner

@RunWith(RobolectricTestRunner::class)
class CreateOrderToCrmResultTest {

    @Test
    fun ToBundle_WithPromouter_SavesToBundle() {
        val createOrderToCrmResult = CreateOrderToCrmResult(
            orderId = 12345,
            promouterId = 654,
            phone = "+70008887766"
        )

        val bundle = createOrderToCrmResult.toBundle()

        assertThat(bundle.getInt("orderId"), equalTo(12345))
        assertThat(bundle.getInt("promouterId"), equalTo(654))
        assertThat(bundle.getString("phone"), equalTo("+70008887766"))
    }

    @Test
    fun ToBundle_WithoutPromouter_SavesToBundle() {
        val createOrderToCrmResult = CreateOrderToCrmResult(
            orderId = 12345,
            promouterId = null,
            phone = "+70008887766"
        )

        val bundle = createOrderToCrmResult.toBundle()

        assertThat(bundle.getInt("orderId"), equalTo(12345))
        assertThat(bundle.getInt("promouterId"), equalTo(0))
        assertThat(bundle.getString("phone"), equalTo("+70008887766"))
    }

    @Test
    fun FromBundle_WithPromouter_CreatesResult() {
        val bundle = Bundle().apply {
            putInt("orderId", 12345)
            putInt("promouterId", 654)
            putString("phone", "+70008887766")
        }

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(createOrderToCrmResult.orderId, equalTo(12345))
        assertThat(createOrderToCrmResult.promouterId, equalTo(654))
        assertThat(createOrderToCrmResult.phone, equalTo("+70008887766"))
    }

    @Test
    fun FromBundle_WithoutPromouterId_CreatesResult() {
        val bundle = Bundle().apply {
            putInt("orderId", 12345)
            putString("phone", "+70008887766")
        }

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(createOrderToCrmResult.orderId, equalTo(12345))
        assertThat(createOrderToCrmResult.promouterId, nullValue())
        assertThat(createOrderToCrmResult.phone, equalTo("+70008887766"))
    }

    @Test
    fun FromBundleToBundle_WithoutPromouterId_CreatesResult() {
        val origin = CreateOrderToCrmResult(
            orderId = 12345,
            promouterId = null,
            phone = "+70008887766"
        )

        val bundle = origin.toBundle()

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(createOrderToCrmResult.orderId, equalTo(12345))
        assertThat(createOrderToCrmResult.promouterId, nullValue())
        assertThat(createOrderToCrmResult.phone, equalTo("+70008887766"))
    }

}
