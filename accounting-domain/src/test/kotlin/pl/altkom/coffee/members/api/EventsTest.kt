package pl.altkom.coffee.members.api

import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import org.junit.Assert
import java.util.*

class EventsTest : Spek({
    describe("MemberCreatedEvent Test") {

        val memberId = UUID.randomUUID().toString()

        it("Should create MemberCreatedEvent object") {
            val actual = MemberCreatedEvent(memberId)
            Assert.assertEquals(actual.memberId, memberId)
        }
    }
})
