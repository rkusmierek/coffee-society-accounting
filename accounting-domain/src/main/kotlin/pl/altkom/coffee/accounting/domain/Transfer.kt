package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.BigDecimalWrapper
import pl.altkom.coffee.accounting.api.TransferId
import pl.altkom.coffee.accounting.api.TransferRegisteredEvent
import java.math.BigDecimal

@Aggregate
class Transfer {

    @AggregateIdentifier
    lateinit var transferId: TransferId
    lateinit var fromMemberId: String
    lateinit var toMemberId: String
    lateinit var amount: BigDecimalWrapper

    companion object : KLogging()

    constructor()

    @CommandHandler
    constructor(command: TransferMoneyCommand) {
        logger.debug("TransferMoneyCommand handler: {}", command.toString())
        if(command.amount.value.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(TransferRegisteredEvent(transferId, fromMemberId, toMemberId, amount))
        }
    }

    @EventSourcingHandler
    fun handle(event: TransferRegisteredEvent) {
        logger.debug("MemberCreatedEvent handler: {}", event.toString())
        transferId = event.transferId
        fromMemberId = event.fromMemberId
        toMemberId = event.toMemberId
        amount = event.amount
    }

}
