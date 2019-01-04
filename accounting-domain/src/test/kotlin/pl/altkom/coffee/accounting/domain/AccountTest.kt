package pl.altkom.coffee.accounting.domain

import org.axonframework.messaging.responsetypes.InstanceResponseType
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.test.aggregate.AggregateTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.query.AccountByMemberIdQuery
import pl.altkom.coffee.accounting.query.AccountEntry
import java.math.BigDecimal
import java.util.*
import java.util.concurrent.CompletableFuture
import kotlin.test.assertEquals

class AccountTest : Spek({
    describe("Account creation") {

        val fixture = AggregateTestFixture(Account::class.java)
        val queryGateway = Mockito.mock(QueryGateway::class.java)
        val memberId = UUID.randomUUID().toString()
        fixture.registerInjectableResource(queryGateway)

        it("Should create new Account") {
            Mockito.`when`(queryGateway.query(any(AccountByMemberIdQuery::class.java), any(InstanceResponseType::class.java)))
                    .thenReturn(CompletableFuture.completedFuture(null))
            fixture
                    .`when`(OpenAccountCommand(memberId))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(AccountOpenedEvent(memberId, BigDecimal.ZERO))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(BigDecimal.ZERO, it.balance)
                    }
        }

        it("Should throw MemberAlreadyHasAccountException if account exist") {
            Mockito.`when`(queryGateway.query(any(AccountByMemberIdQuery::class.java), any(InstanceResponseType::class.java)))
                    .thenReturn(CompletableFuture.completedFuture(AccountEntry(memberId)))

            fixture
                    .`when`(OpenAccountCommand(memberId))
                    .expectException(MemberAlreadyHasAccountException::class.java)
        }
    }
})
