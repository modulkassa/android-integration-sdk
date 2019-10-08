package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import java.math.BigDecimal

@RunWith(RobolectricTestRunner::class)
class LoyaltyResultTest {

    @Test
    fun ToBundle_EmptyResult_SavesToBundle() {
        val loyaltyResult = LoyaltyResult()

        val bundle = loyaltyResult.toBundle()

        assertThat(bundle.getString("data"), nullValue())
        assertThat(bundle.getString("printable_data"), nullValue())
        assertThat(bundle.getStringArrayList("impacts"), equalTo(ArrayList(listOf())))
    }

    @Test
    fun ToBundle_ByDefault_SavesToBundle() {
        val loyaltyResult = LoyaltyResult(
            data = "data",
            printableData = "printable-data",
            impacts = listOf(
                LoyaltyPositionImpact(id = "first")
            )
        )

        val bundle = loyaltyResult.toBundle()

        assertThat(bundle.getString("data"), equalTo("data"))
        assertThat(bundle.getString("printable_data"), equalTo("printable-data"))
        assertThat(bundle.getStringArrayList("impacts"), equalTo(ArrayList(listOf("first"))))
    }

    @Test
    fun ToBundle_WithImpacts_SavesToBundle() {
        val loyaltyResult = LoyaltyResult(
            impacts = listOf(
                LoyaltyPositionImpact(
                    id = "first",
                    positionId = "one",
                    price = BigDecimal("1.11"),
                    quantity = BigDecimal("0.112")
                )
            )
        )

        val bundle = loyaltyResult.toBundle()

        assertThat(bundle.getString("impact/first/position_id"), equalTo("one"))
        assertThat(bundle.getString("impact/first/price"), equalTo("1.11"))
        assertThat(bundle.getString("impact/first/quantity"), equalTo("0.112"))
    }

    @Test
    fun FromBundle_ByDefaut_CreatesResult() {
        val bundle = Bundle().apply {
            putString("data", "data")
            putString("printable_data", "printable-data")
            putStringArrayList("impacts", ArrayList())
        }

        val loyaltyResult = LoyaltyResult.fromBundle(bundle)

        assertThat(loyaltyResult.data, equalTo("data"))
        assertThat(loyaltyResult.printableData, equalTo("printable-data"))
        assertThat(loyaltyResult.impacts, equalTo(listOf()))
    }

    @Test
    fun FromBundle_WithoutData_CreatesResult() {
        val bundle = Bundle().apply {
            putStringArrayList("impacts", ArrayList())
        }

        val loyaltyResult = LoyaltyResult.fromBundle(bundle)

        assertThat(loyaltyResult.data, nullValue())
        assertThat(loyaltyResult.printableData, nullValue())
        assertThat(loyaltyResult.impacts, equalTo(listOf()))
    }

    @Test
    fun FromBundle_WithImpacts_CreatesResult() {
        val bundle = Bundle().apply {
            putStringArrayList("impacts", ArrayList(listOf("id")))
            addImpactAttrToBundle(id = "id", positionId = "item-id", price = "1.23", quantity = "4.567")
        }

        val loyaltyResult = LoyaltyResult.fromBundle(bundle)

        assertThat(loyaltyResult.impacts.size, equalTo(1))
        assertThat(loyaltyResult.impacts[0].id, equalTo("id"))
        assertThat(loyaltyResult.impacts[0].positionId, equalTo("item-id"))
        assertThat(loyaltyResult.impacts[0].price, equalTo(BigDecimal("1.23")))
        assertThat(loyaltyResult.impacts[0].quantity, equalTo(BigDecimal("4.567")))
    }

    @Test
    fun FromBundle_EmptyBundle_CreatesResult() {
        val bundle = Bundle.EMPTY

        val loyaltyResult = LoyaltyResult.fromBundle(bundle)

        assertThat(loyaltyResult.impacts.isEmpty(), equalTo(true))
    }

    @Test
    fun FromBundle_ManyImpacts_CreatesImpactsInSameOrder() {
        val bundle = Bundle().apply {
            putStringArrayList("impacts", ArrayList(listOf("id-z", "id-a")))
            addImpactAttrToBundle(id = "id-a")
            addImpactAttrToBundle(id = "id-z")
        }

        val loyaltyResult = LoyaltyResult.fromBundle(bundle)

        assertThat(loyaltyResult.impacts.size, equalTo(2))
        assertThat(loyaltyResult.impacts[0].id, equalTo("id-z"))
        assertThat(loyaltyResult.impacts[1].id, equalTo("id-a"))
    }

    private fun Bundle.addImpactAttrToBundle(
        id: String = "id",
        positionId: String = "position-id",
        price: String = "0.00",
        quantity: String = "0.000"
    ) {
        putString("impact/$id/position_id", positionId)
        putString("impact/$id/price", price)
        putString("impact/$id/quantity", quantity)
    }

}