package pl.altkom.coffee.accounting.query

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import pl.altkom.coffee.accounting.api.dto.AccountExistsForMemberIdQuery
import java.math.BigDecimal
import java.util.*

class AccountQueryHandlerTest : Spek({
    describe("Account entry query tests") {

        val memberId = UUID.randomUUID().toString()
        val repository = Mockito.mock(AccountEntryRepository::class.java)
        var handler = AccountQueryHandler(repository)

        it("Should find account entry by memberId") {
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString()))
                    .thenReturn(AccountEntry(memberId, BigDecimal.ZERO))

            Assert.assertTrue(handler.checkIfAccountExistsByMemberId(AccountExistsForMemberIdQuery(memberId)))
        }

        it("Should not find account entry by memberId") {
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString()))
                    .thenReturn(null)

            Assert.assertFalse(handler.checkIfAccountExistsByMemberId(AccountExistsForMemberIdQuery(memberId)))
        }
    }
})
