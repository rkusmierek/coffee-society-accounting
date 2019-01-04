package pl.altkom.coffee.accounting.query

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import org.mockito.ArgumentMatchers
import org.mockito.Mockito
import java.util.*

class AccountQueryHandlerTest : Spek({
    describe("Account entry query tests") {

        val memberId = UUID.randomUUID().toString()
        val repository = Mockito.mock(AccountEntryRepository::class.java)
        var handler = AccountQueryHandler(repository)

        it("Should find account entry by memberId") {
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString()))
                    .thenReturn(AccountEntry(memberId))

            Assert.assertEquals(memberId, handler.getAccountByMemberId(AccountByMemberIdQuery(memberId))!!.memberId)
        }

        it("Should not find account entry by memberId") {
            Mockito.`when`(repository.findByMemberId(ArgumentMatchers.anyString()))
                    .thenReturn(null)

            Assert.assertNull(handler.getAccountByMemberId(AccountByMemberIdQuery(memberId)))
        }
    }
})
