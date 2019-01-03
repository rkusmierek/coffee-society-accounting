package pl.altkom.coffee.accounting.domain

import org.axonframework.eventhandling.GenericEventMessage
import org.axonframework.test.aggregate.AggregateTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.members.api.MemberCreatedEvent
import java.math.BigDecimal
import kotlin.test.assertEquals

class AccountTest : Spek({
    describe("Account creation") {

        val fixture = AggregateTestFixture(Account::class.java)
        val memberId = "123"

        it("should create new Account") {

            fixture
                    .given(GenericEventMessage.asEventMessage<MemberCreatedEvent>(MemberCreatedEvent(memberId)))
                    .`when`(OpenAccountCommand(memberId))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(AccountOpenedEvent(memberId, BigDecimal.ZERO))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(BigDecimal.ZERO, it.balance)
                    }
        }
    }
})
