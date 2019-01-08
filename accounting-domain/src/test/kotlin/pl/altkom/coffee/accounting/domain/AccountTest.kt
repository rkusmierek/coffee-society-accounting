package pl.altkom.coffee.accounting.domain

import org.axonframework.messaging.responsetypes.InstanceResponseType
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.test.aggregate.AggregateTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.mockito.ArgumentMatchers.any
import org.mockito.Mockito
import pl.altkom.coffee.accounting.api.*
import pl.altkom.coffee.accounting.query.AccountExistsForMemberIdQuery
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

        whenAccountExistsMock(queryGateway, false)

        it("Should create new Account") {
            fixture
                    .`when`(OpenAccountCommand(memberId))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(AccountOpenedEvent(memberId, BigDecimalWrapper(BigDecimal.ZERO)))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(BigDecimal.ZERO, it.balance.value)
                    }
        }

        it("Should throw MemberAlreadyHasAccountException if account exist") {
            whenAccountExistsMock(queryGateway, true)

            fixture
                    .`when`(OpenAccountCommand(memberId))
                    .expectException(MemberAlreadyHasAccountException::class.java)
        }
    }

    describe("Save asset") {

        val fixture = AggregateTestFixture(Account::class.java)
        val queryGateway = Mockito.mock(QueryGateway::class.java)
        val memberId = UUID.randomUUID().toString()
        val transferId = TransferId("123")
        fixture.registerInjectableResource(queryGateway)

        whenAccountExistsMock(queryGateway, false)

        it("Should save new asset") {

            val amount = BigDecimalWrapper("10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveAssetCommand(memberId, transferId, amount))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(AssetAddedEvent(memberId, transferId, amount, amount))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(amount, it.balance)
                    }
        }

        it("Should throw IllegalAmountException if asset amount < 0") {

            val amount = BigDecimalWrapper("-10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveAssetCommand(memberId, transferId, amount))
                    .expectException(IllegalAmountException::class.java)
        }
    }

    describe("Save liability") {

        val fixture = AggregateTestFixture(Account::class.java)
        val queryGateway = Mockito.mock(QueryGateway::class.java)
        val memberId = UUID.randomUUID().toString()
        val transferId = TransferId("123")
        fixture.registerInjectableResource(queryGateway)

        whenAccountExistsMock(queryGateway, false)

        it("Should save new liability") {

            val amount = BigDecimalWrapper("10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveLiabilityCommand(memberId, transferId, amount))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(LiabilityAddedEvent(memberId, transferId, amount.negate(), amount))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(BigDecimalWrapper("10.00").negate(), it.balance)
                    }
        }

        it("Should throw IllegalAmountException if liability amount < 0") {

            val amount = BigDecimalWrapper("-10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveLiabilityCommand(memberId, transferId, amount))
                    .expectException(IllegalAmountException::class.java)
        }
    }

    describe("Save payment") {

        val fixture = AggregateTestFixture(Account::class.java)
        val queryGateway = Mockito.mock(QueryGateway::class.java)
        val memberId = UUID.randomUUID().toString()
        fixture.registerInjectableResource(queryGateway)

        whenAccountExistsMock(queryGateway, false)

        it("Should save new payment") {

            val amount = BigDecimalWrapper("10.00")

            fixture
                    .given(AccountOpenedEvent(memberId, BigDecimalWrapper(BigDecimal.ZERO)))
                    .`when`(SavePaymentCommand(memberId, amount))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(PaymentAddedEvent(memberId, amount, amount))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(amount, it.balance)
                    }
        }

        it("Should throw IllegalAmountException if payment amount < 0") {

            val amount = BigDecimalWrapper("-10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SavePaymentCommand(memberId, amount))
                    .expectException(IllegalAmountException::class.java)
        }
    }

    describe("Save withdrawal") {

        val fixture = AggregateTestFixture(Account::class.java)
        val queryGateway = Mockito.mock(QueryGateway::class.java)
        val memberId = UUID.randomUUID().toString()
        fixture.registerInjectableResource(queryGateway)

        whenAccountExistsMock(queryGateway, false)

        it("Should save new withdrawal") {

            val amount = BigDecimalWrapper("10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveWithdrawalCommand(memberId, amount))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(WithdrawalAddedEvent(memberId, amount.negate(), amount))
                    .expectState {
                        assertEquals(memberId, it.memberId)
                        assertEquals(BigDecimalWrapper("10.00").negate(), it.balance)
                    }
        }

        it("Should throw IllegalAmountException if withdrawal amount < 0") {

            val amount = BigDecimalWrapper("-10.00")

            fixture
                    .givenCommands(OpenAccountCommand(memberId))
                    .`when`(SaveWithdrawalCommand(memberId, amount))
                    .expectException(IllegalAmountException::class.java)
        }
    }
})

private fun whenAccountExistsMock(queryGateway : QueryGateway, exists : Boolean) {
    Mockito.`when`(queryGateway.query(any<AccountExistsForMemberIdQuery>(), any<InstanceResponseType<Boolean>>()))
            .thenReturn(CompletableFuture.completedFuture(exists))
}
