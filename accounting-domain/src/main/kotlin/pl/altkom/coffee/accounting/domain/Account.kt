package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.messaging.responsetypes.InstanceResponseType
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.api.AssetAddedEvent
import pl.altkom.coffee.accounting.api.LiabilityAddedEvent
import pl.altkom.coffee.accounting.query.AccountByMemberIdQuery
import pl.altkom.coffee.accounting.query.AccountEntry
import pl.altkom.coffee.members.api.MemberCreatedEvent
import java.math.BigDecimal
import java.math.MathContext

@Aggregate
class Account {

    @AggregateIdentifier
    lateinit var memberId: String
    lateinit var balance: BigDecimal
    lateinit var amount: BigDecimal

    constructor()

    @CommandHandler
    constructor(command: OpenAccountCommand, queryGateway: QueryGateway) {
        logger.debug("OpenAccountCommand handler: {}", command.toString())
        with(command) {
            if(queryGateway.query(AccountByMemberIdQuery(memberId), InstanceResponseType(AccountEntry::class.java)).get() == null) {
                AggregateLifecycle.apply(AccountOpenedEvent(memberId, balance))
            } else {
                throw MemberAlreadyHasAccountException()
            }
        }
    }

    @CommandHandler
    fun on(command: SaveAssetCommand) {
        logger.debug("SaveAssetCommand handler: {}", command.toString())
        if(command.amount.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(AssetAddedEvent(memberId, amount))
        }
    }

    @CommandHandler
    fun on(command: SaveLiabilityCommand) {
        logger.debug("SaveLiabilityCommand handler: {}", command.toString())
        if(command.amount.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(LiabilityAddedEvent(memberId, amount))
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

    @EventSourcingHandler
    fun handle(event: AssetAddedEvent) {
        logger.debug("AccountOpenedEvent handler: {}", event.toString())
        balance = balance.add(event.amount)
    }

    @EventSourcingHandler
    fun handle(event: LiabilityAddedEvent) {
        logger.debug("LiabilityAddedEvent handler: {}", event.toString())
        balance = balance.subtract(event.amount, MathContext(2))
    }

    companion object : KLogging()
}
