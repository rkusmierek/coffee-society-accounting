package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.Money
import pl.altkom.coffee.accounting.api.OperationId
import pl.altkom.coffee.accounting.api.TransferRegisteredEvent
import java.math.BigDecimal

@Aggregate
class Transfer {

    @AggregateIdentifier
    lateinit var operationId: OperationId
    lateinit var fromMemberId: String
    lateinit var toMemberId: String
    lateinit var amount: Money

    companion object : KLogging()

    constructor()

    @CommandHandler
    constructor(command: TransferMoneyCommand) {
        logger.debug("TransferMoneyCommand handler: {}", command.toString())
        if(command.amount.value < BigDecimal.ZERO)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(TransferRegisteredEvent(operationId, fromMemberId, toMemberId, amount))
        }
    }

    @EventSourcingHandler
    fun handle(event: TransferRegisteredEvent) {
        logger.debug("MemberCreatedEvent handler: {}", event.toString())
        operationId = event.operationId
        fromMemberId = event.fromMemberId
        toMemberId = event.toMemberId
        amount = event.amount
    }

}
