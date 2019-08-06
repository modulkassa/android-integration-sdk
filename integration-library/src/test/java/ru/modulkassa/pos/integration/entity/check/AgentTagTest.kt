package ru.modulkassa.pos.integration.entity.check

import org.hamcrest.CoreMatchers.equalTo
import org.junit.Assert.*
import org.junit.Test
import ru.modulkassa.pos.integration.entity.check.AgentTag.AGENT
import ru.modulkassa.pos.integration.entity.check.AgentTag.ATTORNEY
import ru.modulkassa.pos.integration.entity.check.AgentTag.BANK_PAY_AGENT
import ru.modulkassa.pos.integration.entity.check.AgentTag.BANK_PAY_SUBAGENT
import ru.modulkassa.pos.integration.entity.check.AgentTag.COMMISSION_AGENT
import ru.modulkassa.pos.integration.entity.check.AgentTag.PAY_AGENT
import ru.modulkassa.pos.integration.entity.check.AgentTag.PAY_SUBAGENT

class AgentTagTest {

    @Test
    fun ConvertToAgentTags_WrongValues_ReturnEmptyList() {
        assertThat(AgentTag.convertToAgentTags(0).isEmpty(), equalTo(true))
        assertThat(AgentTag.convertToAgentTags(128).isEmpty(), equalTo(true))
    }

    @Test
    fun ConvertToAgentTags_ValueMatchesCode_ReturnSingleAgentType() {
        assertThat(AgentTag.convertToAgentTags(1), equalTo(listOf(BANK_PAY_AGENT)))
        assertThat(AgentTag.convertToAgentTags(2), equalTo(listOf(BANK_PAY_SUBAGENT)))
        assertThat(AgentTag.convertToAgentTags(4), equalTo(listOf(PAY_AGENT)))
        assertThat(AgentTag.convertToAgentTags(8), equalTo(listOf(PAY_SUBAGENT)))
        assertThat(AgentTag.convertToAgentTags(16), equalTo(listOf(ATTORNEY)))
        assertThat(AgentTag.convertToAgentTags(32), equalTo(listOf(COMMISSION_AGENT)))
        assertThat(AgentTag.convertToAgentTags(64), equalTo(listOf(AGENT)))
    }

    @Test
    fun ConvertToAgentTAgs_ValueContainsFewCode_ReturnFewAgentTypes() {
        assertThat(AgentTag.convertToAgentTags(7), equalTo(listOf(BANK_PAY_AGENT, BANK_PAY_SUBAGENT, PAY_AGENT)))
        assertThat(AgentTag.convertToAgentTags(48), equalTo(listOf(ATTORNEY, COMMISSION_AGENT)))
    }
}