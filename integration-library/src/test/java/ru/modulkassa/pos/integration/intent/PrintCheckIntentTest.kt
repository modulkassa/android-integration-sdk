package ru.modulkassa.pos.integration.intent

import android.content.Intent
import com.google.gson.JsonSyntaxException
import com.nhaarman.mockito_kotlin.doReturn
import com.nhaarman.mockito_kotlin.mock
import org.hamcrest.CoreMatchers.equalTo
import org.hamcrest.CoreMatchers.instanceOf
import org.hamcrest.CoreMatchers.nullValue
import org.junit.Assert.assertThat
import org.junit.Test
import ru.modulkassa.pos.integration.entity.check.Check
import kotlin.test.assertFailsWith

class PrintCheckIntentTest {

    @Test
    fun CheckFromIntent_EmptyObject_ReturnsCheck() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn "{}"
        }

        val check = PrintCheckIntent.checkFromIntent(intent)

        assertThat(check, instanceOf(Check::class.java))
    }

    @Test
    fun CheckFromIntent_BlankString_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn ""
        }

        val exception = assertFailsWith<InvalidCheckBodyException> { PrintCheckIntent.checkFromIntent(intent) }

        assertThat(exception.body, equalTo(""))
        assertThat(exception.cause, instanceOf(IllegalStateException::class.java))
    }

    @Test
    fun CheckFromIntent_NullString_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn "null"
        }

        val exception = assertFailsWith<InvalidCheckBodyException> { PrintCheckIntent.checkFromIntent(intent) }

        assertThat(exception.body, equalTo("null"))
        assertThat(exception.cause, instanceOf(IllegalStateException::class.java))
    }

    @Test
    fun CheckFromIntent_InvalidStructure_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn "{"
        }

        val exception = assertFailsWith<InvalidCheckBodyException> { PrintCheckIntent.checkFromIntent(intent) }

        assertThat(exception.body, equalTo("{"))
        assertThat(exception.cause, instanceOf(JsonSyntaxException::class.java))
    }

    @Test
    fun CheckFromIntent_UnexpectedStructure_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn "[]"
        }

        val exception = assertFailsWith<InvalidCheckBodyException> { PrintCheckIntent.checkFromIntent(intent) }

        assertThat(exception.body, equalTo("[]"))
        assertThat(exception.cause, instanceOf(JsonSyntaxException::class.java))
    }

    @Test
    fun CheckFromIntent_NullValue_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("serialized_check") } doReturn null as String?
        }

        val exception = assertFailsWith<InvalidCheckBodyException> { PrintCheckIntent.checkFromIntent(intent) }

        assertThat(exception.body, nullValue())
        assertThat(exception.cause, instanceOf(IllegalStateException::class.java))
    }

    @Test
    fun EmployeeNameFromIntent_NullValue_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("employee_name") } doReturn null as String?
        }

        assertFailsWith<EmployeeNameNotFoundException> {
            PrintCheckIntent.employeeNameFromIntent(intent)
        }
    }

    @Test
    fun PinFromIntent_NullValue_ThrowsInvalidCheckBodyException() {
        val intent = mock<Intent> {
            on { getStringExtra("pin") } doReturn null as String?
        }

        assertFailsWith<PinNotFoundException> {
            PrintCheckIntent.pinFromIntent(intent)
        }
    }

}