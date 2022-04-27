package ru.modulkassa.pos.integration.entity.kkt

import android.os.Bundle
import org.hamcrest.CoreMatchers
import org.hamcrest.CoreMatchers.`is`
import org.hamcrest.MatcherAssert.assertThat
import org.junit.Test
import org.junit.runner.RunWith
import org.robolectric.RobolectricTestRunner
import org.robolectric.annotation.Config
import ru.modulkassa.pos.integration.entity.InvalidEntityStructureException
import ru.modulkassa.pos.integration.entity.check.Employee
import ru.modulkassa.pos.integration.entity.kkt.MoneyCheckType.INCOME
import java.math.BigDecimal
import kotlin.test.assertFails

@RunWith(RobolectricTestRunner::class)
@Config(manifest = Config.NONE)
class MoneyCheckTest {

    @Test
    fun FromBundle_EverythingIsCorrect_ConvertedCorrectly() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "12.3")
        bundle.putString(MoneyCheck.KEY_TYPE, "INCOME")
        bundle.putStringArrayList(MoneyCheck.KEY_TEXT, ArrayList<String>().also {
            it.add("first line")
        })

        val check = MoneyCheck.fromBundle(bundle)

        assertThat(check.type, `is`(INCOME))
        assertThat(check.amount, `is`(BigDecimal("12.3")))
        assertThat(check.text[0], `is`("first line"))
    }

    @Test
    fun FromBundle_NoText_ConvertedToEmptyText() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "12.3")
        bundle.putString(MoneyCheck.KEY_TYPE, "INCOME")

        val check = MoneyCheck.fromBundle(bundle)

        assertThat(check.text.size, `is`(0))
    }

    @Test
    fun FromBundle_NoEmployeeName_ConvertedToEmptyName() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "12.3")
        bundle.putString(MoneyCheck.KEY_TYPE, "INCOME")

        val check = MoneyCheck.fromBundle(bundle)

        assertThat(check.employee.name.length, `is`(0))
    }

    @Test
    fun FromBundle_NoType_Exception() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "12.3")

        val throwable = assertFails { MoneyCheck.fromBundle(bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(InvalidEntityStructureException::class.java))
    }

    @Test
    fun FromBundle_InvalidType_Exception() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "12.3")
        bundle.putString(MoneyCheck.KEY_TYPE, "--")

        val throwable = assertFails { MoneyCheck.fromBundle(bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(InvalidEntityStructureException::class.java))
    }

    @Test
    fun FromBundle_NoAmount_Exception() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_TYPE, "INCOME")

        val throwable = assertFails { MoneyCheck.fromBundle(bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(InvalidEntityStructureException::class.java))
    }

    @Test
    fun FromBundle_InvalidAmount_Exception() {
        val bundle = Bundle()
        bundle.putString(MoneyCheck.KEY_AMOUNT, "--")
        bundle.putString(MoneyCheck.KEY_TYPE, "INCOME")

        val throwable = assertFails { MoneyCheck.fromBundle(bundle) }

        assertThat(throwable, CoreMatchers.instanceOf(InvalidEntityStructureException::class.java))
    }

    @Test
    fun ToBundle_NoEmptyFields_ConvertedCorrectly() {
        val check = MoneyCheck(INCOME, BigDecimal("12.3"), listOf("first line"), Employee("Name"))

        val convertedCheck = check.toBundle()

        assertThat(convertedCheck.getString(MoneyCheck.KEY_TYPE), `is`("INCOME"))
        assertThat(convertedCheck.getString(MoneyCheck.KEY_AMOUNT), `is`("12.3"))
        assertThat(convertedCheck.getStringArrayList(MoneyCheck.KEY_TEXT)!![0], `is`("first line"))
        assertThat(convertedCheck.getString(Employee.KEY_EMPLOYEE_NAME), `is`("Name"))
    }
}