package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.members.api.MemberCreatedEvent
import java.math.BigDecimal

@Aggregate
class Account {

    @AggregateIdentifier
    lateinit var memberId: String
    lateinit var balance: BigDecimal

    constructor()

    @CommandHandler
    constructor(command: OpenAccountCommand) {
        logger.debug("OpenAccountCommand handler: {}", command.toString())
        with(command) {
            AggregateLifecycle.apply(AccountOpenedEvent(memberId, balance))
        }
    }

    @EventSourcingHandler
    fun handle(event: MemberCreatedEvent) {
        logger.debug("MemberCreatedEvent handler: {}", event.toString())
        memberId = event.memberId
    }

    @EventSourcingHandler
    fun handle(event: AccountOpenedEvent) {
        logger.debug("AccountOpenedEvent handler: {}", event.toString())
        balance = event.balance
        memberId = event.memberId
    }

    companion object : KLogging()
}
