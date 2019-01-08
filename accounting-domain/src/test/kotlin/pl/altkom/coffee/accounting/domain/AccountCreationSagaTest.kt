package pl.altkom.coffee.accounting.domain

import org.axonframework.test.saga.SagaTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.api.Money
import pl.altkom.coffee.members.api.MemberCreatedEvent
import java.math.BigDecimal
import java.util.*

class AccountCreationSagaTest : Spek({
    describe("Account creation saga tests") {

        val memberId = UUID.randomUUID().toString()
        val fixture = SagaTestFixture(AccountCreationSaga::class.java)

        it("Should start account creation saga") {
            fixture
                    .givenAggregate(memberId).published()
                    .whenAggregate(memberId).publishes(MemberCreatedEvent(memberId))
                    .expectActiveSagas(1)
        }

        it("Should end account creation saga") {
            fixture
                    .givenAggregate(memberId).published()
                    .whenAggregate(memberId).publishes(AccountOpenedEvent(memberId, Money(BigDecimal.ZERO)))
                    .expectActiveSagas(0)
        }
    }
})
