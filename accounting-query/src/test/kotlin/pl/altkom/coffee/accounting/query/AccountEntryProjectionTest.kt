package pl.altkom.coffee.accounting.query

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import pl.altkom.coffee.accounting.api.*
import java.math.BigDecimal
import java.util.*

class AccountEntryProjectionTest : Spek({
    describe("Account entry projection tests") {

        val memberId = UUID.randomUUID().toString()
        val operationId = OperationId("123", "TRANSFER")
        val repository = Mockito.mock(AccountEntryRepository::class.java)
        var handler = AccountEntryProjection(repository)

        it("Should add asset to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal.ZERO)
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            val expected = Money("10.00")

            handler.on(AssetAddedEvent(memberId, operationId, expected, expected))

            Assert.assertEquals(BigDecimal("10.00"), entry.balance)
        }

        it("Should subtract liability to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal("25.00"))
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            handler.on(LiabilityAddedEvent(memberId, operationId, Money("15.00"), Money("10.00")))

            Assert.assertEquals(BigDecimal("15.00"), entry.balance)
        }

        it("Should add payment to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal.ZERO)
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            val expected = Money("10.00")

            handler.on(PaymentAddedEvent(memberId, expected, expected))

            Assert.assertEquals(BigDecimal("10.00"), entry.balance)
        }

        it("Should subtract withdrawal to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal("25.00"))
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            handler.on(WithdrawalAddedEvent(memberId, Money("15.00"), Money("10.00")))

            Assert.assertEquals(BigDecimal("15.00"), entry.balance)
        }
    }
})
