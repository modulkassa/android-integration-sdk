package ru.modulkassa.pos.integration.entity.crm

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

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
    fun ToBundle_WithPositions_SavesToBundle() {
        val createOrderToCrmResult = CreateOrderToCrmResult(
            orderId = 12345,
            phone = "+70008887766",
            positions = listOf(CrmPosition("barcode", BigDecimal.ONE, BigDecimal.ONE, listOf()))
        )

        val bundle = createOrderToCrmResult.toBundle()

        assertThat(
            bundle.getString("positions"),
            equalTo("[{\"barcode\":\"barcode\",\"quantity\":1,\"price\":1,\"modifiers\":[]}]")
        )
    }

    @Test
    fun ToBundle_WithoutPositions_SavesToBundle() {
        val createOrderToCrmResult = CreateOrderToCrmResult(
            orderId = 12345,
            positions = null,
            phone = "+70008887766"
        )

        val bundle = createOrderToCrmResult.toBundle()

        assertThat(bundle.getString("positions"), equalTo("null"))
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
    fun FromBundle_WithPositions_CreatesResult() {
        val bundle = Bundle().apply {
            putInt("orderId", 12345)
            putString("phone", "+70008887766")
            putString("positions", "[{\"barcode\":\"barcode\",\"quantity\":1,\"price\":1,\"modifiers\":[]}]")
        }

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(
            createOrderToCrmResult.positions,
            equalTo(listOf(CrmPosition("barcode", BigDecimal.ONE, BigDecimal.ONE, listOf())))
        )
    }

    @Test
    fun FromBundle_WithoutPositions_CreatesResult() {
        val bundle = Bundle().apply {
            putInt("orderId", 12345)
            putString("phone", "+70008887766")
            putString("positions", "null")
        }

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(createOrderToCrmResult.positions, nullValue())
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

    @Test
    fun FromBundleToBundle_WithoutPositions_CreatesResult() {
        val origin = CreateOrderToCrmResult(
            orderId = 12345,
            promouterId = null,
            phone = "+70008887766",
            positions = null
        )

        val bundle = origin.toBundle()

        val createOrderToCrmResult = CreateOrderToCrmResult.fromBundle(bundle)

        assertThat(createOrderToCrmResult.orderId, equalTo(12345))
        assertThat(createOrderToCrmResult.promouterId, nullValue())
        assertThat(createOrderToCrmResult.phone, equalTo("+70008887766"))
        assertThat(createOrderToCrmResult.positions, nullValue())
    }

}
