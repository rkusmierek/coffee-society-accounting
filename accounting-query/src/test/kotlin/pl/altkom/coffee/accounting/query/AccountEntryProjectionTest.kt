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
        val transferId = TransferId("123")
        val repository = Mockito.mock(AccountEntryRepository::class.java)
        var handler = AccountEntryProjection(repository)

        it("Should add asset to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal.ZERO)
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            val expected = BigDecimalWrapper("10.00")

            handler.on(AssetAddedEvent(memberId, transferId, expected, expected))

            Assert.assertEquals(BigDecimal("10.00"), entry.balance)
        }

        it("Should subtract liability to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal("25.00"))
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            handler.on(LiabilityAddedEvent(memberId, transferId, BigDecimalWrapper("15.00"), BigDecimalWrapper("10.00")))

            Assert.assertEquals(BigDecimal("15.00"), entry.balance)
        }

        it("Should add payment to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal.ZERO)
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            val expected = BigDecimalWrapper("10.00")

            handler.on(PaymentAddedEvent(memberId, expected, expected))

            Assert.assertEquals(BigDecimal("10.00"), entry.balance)
        }

        it("Should subtract withdrawal to entry balance") {
            val entry = AccountEntry(memberId, BigDecimal("25.00"))
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString())).thenReturn(entry)

            handler.on(WithdrawalAddedEvent(memberId, BigDecimalWrapper("15.00"), BigDecimalWrapper("10.00")))

            Assert.assertEquals(BigDecimal("15.00"), entry.balance)
        }
    }
})
