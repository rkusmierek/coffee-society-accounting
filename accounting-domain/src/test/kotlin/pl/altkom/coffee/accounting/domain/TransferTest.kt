package pl.altkom.coffee.accounting.domain

import org.axonframework.test.aggregate.AggregateTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import pl.altkom.coffee.accounting.api.Money
import pl.altkom.coffee.accounting.api.OperationId
import pl.altkom.coffee.accounting.api.TransferRegisteredEvent
import kotlin.test.assertEquals

class TransferTest : Spek({
    describe("Transfer money") {

        val fixture = AggregateTestFixture(Transfer::class.java)
        val operationId = OperationId("123", "TRANSFER")
        val fromMemberId = "321"
        val toMemberId = "xyz"
        val amount = Money("100.00")

        it("Should register transfer money") {
            fixture
                    .`when`(TransferMoneyCommand(operationId, fromMemberId, toMemberId, amount))
                    .expectSuccessfulHandlerExecution()
                    .expectEvents(TransferRegisteredEvent(operationId, fromMemberId, toMemberId, amount))
                    .expectState {
                        assertEquals(operationId, it.operationId)
                        assertEquals(fromMemberId, it.fromMemberId)
                        assertEquals(toMemberId, it.toMemberId)
                        assertEquals(amount.value, it.amount.value)
                    }
        }

        it("Should throw IllegalAmountException") {
            val negativeAmount = Money("-20.00")

            fixture
                    .`when`(TransferMoneyCommand(operationId, fromMemberId, toMemberId, negativeAmount))
                    .expectException(IllegalAmountException::class.java)
        }
    }
})
