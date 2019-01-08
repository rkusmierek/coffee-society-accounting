package pl.altkom.coffee.accounting.domain

import org.axonframework.test.saga.SagaTestFixture
import org.jetbrains.spek.api.Spek
import org.jetbrains.spek.api.dsl.describe
import org.jetbrains.spek.api.dsl.it
import pl.altkom.coffee.accounting.api.*
import java.math.BigDecimal
import java.util.*

class TransferMoneySagaTest : Spek({
    describe("Transfer money saga tests") {

        val fromMemberId = UUID.randomUUID().toString()
        val toMemberId = UUID.randomUUID().toString()
        val operationId = OperationId("123", "TRANSFER")
        val amount = Money("25.00")

        val fixture = SagaTestFixture(TransferMoneySaga::class.java)

        it("Should start transfer money saga") {
            fixture
                    .givenAggregate(operationId.toString()).published()
                    .whenAggregate(operationId.toString()).publishes(TransferRegisteredEvent(operationId, fromMemberId, toMemberId, amount))
                    .expectActiveSagas(1)
        }

        it("Should end transfer money saga when AssetAddedEvent") {
            fixture
                    .givenAggregate(operationId.toString()).published()
                    .andThenAPublished(LiabilityAddedEvent(toMemberId, operationId, Money(BigDecimal.ZERO), amount))
                    .whenAggregate(operationId.toString()).publishes(AssetAddedEvent(toMemberId, operationId, Money(BigDecimal.ZERO), amount))
                    .expectActiveSagas(0)
        }

        it("Should end transfer money saga when LiabilityAddedEvent") {
            fixture
                    .givenAggregate(operationId.toString()).published()
                    .andThenAPublished(AssetAddedEvent(toMemberId, operationId, Money(BigDecimal.ZERO), amount))
                    .whenAggregate(operationId.toString()).publishes(LiabilityAddedEvent(toMemberId, operationId, Money(BigDecimal.ZERO), amount))
                    .expectActiveSagas(0)
        }
    }
})
