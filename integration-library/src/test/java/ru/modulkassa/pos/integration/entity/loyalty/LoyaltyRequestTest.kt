package ru.modulkassa.pos.integration.entity.loyalty

import android.os.Bundle
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.junit.Assert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import ru.modulkassa.pos.integration.entity.check.InventType.SERVICE
import ru.modulkassa.pos.integration.entity.check.InventType.TOBACCO
import ru.modulkassa.pos.integration.entity.check.Measure.KG
import ru.modulkassa.pos.integration.entity.check.Measure.M2
import java.math.BigDecimal
import kotlin.test.assertFails

@RunWith(RobolectricTestRunner::class)
class LoyaltyRequestTest {

    @Test
    fun ToBundle_ByDefault_SavesRequest() {
        val request = LoyaltyRequest(
            id = "id",
            retailPointId = "store-id",
            userId = "user-id",
            positions = listOf()
        )

        val bundle = request.toBundle()

        assertThat(bundle.getString("request_id"), equalTo("id"))
        assertThat(bundle.getString("retail_point_id"), equalTo("store-id"))
        assertThat(bundle.getString("user_id"), equalTo("user-id"))
        assertThat(bundle.getStringArrayList("positions"), equalTo(ArrayList(listOf())))
    }

    @Test
    fun ToBundle_ByDefault_SavesPositions() {
        val request = LoyaltyRequest(
            id = "id",
            positions = listOf(
                LoyaltyPosition(
                    id = "pos-id",
                    inventCode = "invent-code",
                    barcode = "barcode",
                    name = "name",
                    type = TOBACCO,
                    measure = KG,
                    price = BigDecimal("12.34"),
                    quantity = BigDecimal("1.234")
                )
            )
        )

        val bundle = request.toBundle()

        assertThat(bundle.getStringArrayList("positions"), equalTo(ArrayList(listOf("pos-id"))))
        assertThat(bundle.getString("position/pos-id/invent_code"), equalTo("invent-code"))
        assertThat(bundle.getString("position/pos-id/barcode"), equalTo("barcode"))
        assertThat(bundle.getString("position/pos-id/name"), equalTo("name"))
        assertThat(bundle.getString("position/pos-id/type"), equalTo("TOBACCO"))
        assertThat(bundle.getString("position/pos-id/measure"), equalTo("KG"))
        assertThat(bundle.getString("position/pos-id/price"), equalTo("12.34"))
        assertThat(bundle.getString("position/pos-id/quantity"), equalTo("1.234"))
        assertThat(bundle.getStringArrayList("position/pos-id/modifiers"), equalTo(ArrayList(listOf())))
    }

    @Test
    fun ToBundle_ByDefault_SavesModifiers() {
        val request = LoyaltyRequest(
            id = "id",
            positions = listOf(
                LoyaltyPosition(id = "first-pos-id", inventCode = "first-invent-code"),
                LoyaltyPosition(id = "second-pos-id", inventCode = "second-invent-code", modifiers = listOf(
                    LoyaltyModifier(
                        id = "id",
                        name = "name",
                        price = BigDecimal("123.45"),
                        quantity = BigDecimal("9.876")
                    )
                ))
            )
        )

        val bundle = request.toBundle()

        assertThat(bundle.getStringArrayList("positions"), equalTo(ArrayList(listOf("first-pos-id", "second-pos-id"))))
        assertThat(bundle.getString("position/first-pos-id/invent_code"), equalTo("first-invent-code"))
        assertThat(bundle.getStringArrayList("position/first-pos-id/modifiers"), equalTo(ArrayList(listOf())))

        assertThat(bundle.getString("position/second-pos-id/invent_code"), equalTo("second-invent-code"))
        assertThat(bundle.getStringArrayList("position/second-pos-id/modifiers"), equalTo(ArrayList(listOf("id"))))

        assertThat(bundle.getString("modifier/id/name"), equalTo("name"))
        assertThat(bundle.getString("modifier/id/price"), equalTo("123.45"))
        assertThat(bundle.getString("modifier/id/quantity"), equalTo("9.876"))
    }

    @Test
    fun FromBundle_ByDefault_CreatesRequest() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(id = "id", retailPointId = "retail-point-id", userId = "user-id")
        }

        val loyaltyRequest = LoyaltyRequest.fromBundle(bundle)

        assertThat(loyaltyRequest.id, equalTo("id"))
        assertThat(loyaltyRequest.retailPointId, equalTo("retail-point-id"))
        assertThat(loyaltyRequest.userId, equalTo("user-id"))
        assertThat(loyaltyRequest.positions, equalTo(listOf()))
    }

    @Test
    fun FromBundle_IdIsAbsent_ThrowsException() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(id = "id", retailPointId = "retail-point-id", userId = "user-id")
        }
        bundle.remove("request_id")

        val throwable = assertFails { LoyaltyRequest.fromBundle(bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
    }

    @Test
    fun FromBundle_RetailPointIdIsAbsent_ThrowsException() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(id = "id", retailPointId = "retail-point-id", userId = "user-id")
        }
        bundle.remove("retail_point_id")

        val throwable = assertFails { LoyaltyRequest.fromBundle(bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
    }

    @Test
    fun FromBundle_UserIdIsAbsent_ThrowsException() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(id = "id", retailPointId = "retail-point-id", userId = "user-id")
        }
        bundle.remove("user_id")

        val throwable = assertFails { LoyaltyRequest.fromBundle(bundle) }

        assertThat(throwable, instanceOf(LoyaltyInvalidStructureException::class.java))
    }

    @Test
    fun FromBundle_WithPosition_CreatesPosition() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(positions = listOf("item-id"))
            addPositionAttrsToBundle(id = "item-id", inventCode = "invent-code", barcode = "barcode", name = "name",
                type = "SERVICE", measure = "M2", price = "123.45", quantity = "9.876")
        }

        val loyaltyRequest = LoyaltyRequest.fromBundle(bundle)

        assertThat(loyaltyRequest.positions.size, equalTo(1))
        val position = loyaltyRequest.positions[0]
        assertThat(position.id, equalTo("item-id"))
        assertThat(position.inventCode, equalTo("invent-code"))
        assertThat(position.barcode, equalTo("barcode"))
        assertThat(position.name, equalTo("name"))
        assertThat(position.type, equalTo(SERVICE))
        assertThat(position.measure, equalTo(M2))
        assertThat(position.price, equalTo(BigDecimal("123.45")))
        assertThat(position.quantity, equalTo(BigDecimal("9.876")))
        assertThat(position.modifiers, equalTo(listOf()))
    }

    @Test
    fun FromBundle_ManyPositions_CreatesPositionsOnSameOrder() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(positions = listOf("item-id", "second-item-id"))

            addPositionAttrsToBundle(id = "item-id", inventCode = "first")
            addPositionAttrsToBundle(id = "second-item-id", inventCode = "second")
        }

        val loyaltyRequest = LoyaltyRequest.fromBundle(bundle)

        assertThat(loyaltyRequest.positions.size, equalTo(2))
        assertThat(loyaltyRequest.positions[0].id, equalTo("item-id"))
        assertThat(loyaltyRequest.positions[0].inventCode, equalTo("first"))

        assertThat(loyaltyRequest.positions[1].id, equalTo("second-item-id"))
        assertThat(loyaltyRequest.positions[1].inventCode, equalTo("second"))
    }

    @Test
    fun FromBundle_WithModifiers_CreatesModifier() {
        val bundle = Bundle().apply {
            addRequestAttrsToBundle(positions = listOf("second-item-id"))
            addPositionAttrsToBundle(id = "second-item-id", inventCode = "second", modifiers = listOf("modifier-id"))
            addModifierAttrsToBundle(id = "modifier-id", name = "name", price = "1.45", quantity = "1.345")
        }

        val loyaltyRequest = LoyaltyRequest.fromBundle(bundle)

        val modifier = loyaltyRequest.positions[0].modifiers[0]
        assertThat(modifier.id, equalTo("modifier-id"))
        assertThat(modifier.name, equalTo("name"))
        assertThat(modifier.price, equalTo(BigDecimal("1.45")))
        assertThat(modifier.quantity, equalTo(BigDecimal("1.345")))
    }

    private fun Bundle.addRequestAttrsToBundle(id: String = "id", retailPointId: String = "retail-poit-id",
                                               userId: String = "user-id", positions: List<String> = listOf()) {
        putString("request_id", id)
        putString("retail_point_id", retailPointId)
        putString("user_id", userId)
        putStringArrayList("positions", ArrayList(positions))
    }

    private fun Bundle.addPositionAttrsToBundle(
        id: String,
        inventCode: String = "invent-code",
        barcode: String = "barcode",
        name: String = "name",
        type: String = "INVENTORY",
        measure: String = "PCS",
        price: String = "0.00",
        quantity: String = "0.000",
        modifiers: List<String> = listOf()
    ) {
        putString("position/%s/invent_code".format(id), inventCode)
        putString("position/%s/barcode".format(id), barcode)
        putString("position/%s/name".format(id), name)
        putString("position/%s/type".format(id), type)
        putString("position/%s/measure".format(id), measure)
        putString("position/%s/price".format(id), price)
        putString("position/%s/quantity".format(id), quantity)
        putStringArrayList("position/%s/modifiers".format(id), ArrayList(modifiers))
    }

    private fun Bundle.addModifierAttrsToBundle(
        id: String,
        name: String = "name",
        price: String = "0.00",
        quantity: String = "0.000"
    ) {
        putString("modifier/%s/name".format(id), name)
        putString("modifier/%s/price".format(id), price)
        putString("modifier/%s/quantity".format(id), quantity)
    }

}