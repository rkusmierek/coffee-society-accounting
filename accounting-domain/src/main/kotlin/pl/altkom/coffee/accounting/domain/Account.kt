package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.CommandHandler
import org.axonframework.eventsourcing.EventSourcingHandler
import org.axonframework.messaging.responsetypes.InstanceResponseType
import org.axonframework.modelling.command.AggregateIdentifier
import org.axonframework.modelling.command.AggregateLifecycle
import org.axonframework.queryhandling.QueryGateway
import org.axonframework.spring.stereotype.Aggregate
import pl.altkom.coffee.accounting.api.*
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
            AggregateLifecycle.apply(AssetAddedEvent(memberId, balance.add(amount), amount))
        }
    }

    @CommandHandler
    fun on(command: SaveLiabilityCommand) {
        logger.debug("SaveLiabilityCommand handler: {}", command.toString())
        if(command.amount.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(LiabilityAddedEvent(memberId, balance.subtract(amount, MathContext(2)), amount))
        }
    }

    @CommandHandler
    fun on(command: SavePaymentCommand) {
        logger.debug("SavePaymentCommand handler: {}", command.toString())
        if(command.amount.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(PaymentAddedEvent(memberId, balance.add(amount), amount))
        }
    }

    @CommandHandler
    fun on(command: SaveWithdrawalCommand) {
        logger.debug("SaveWithdrawalCommand handler: {}", command.toString())
        if(command.amount.compareTo(BigDecimal.ZERO) < 0)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(WithdrawalAddedEvent(memberId, balance.subtract(amount, MathContext(2)), amount))
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
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: LiabilityAddedEvent) {
        logger.debug("LiabilityAddedEvent handler: {}", event.toString())
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: PaymentAddedEvent) {
        logger.debug("PaymentAddedEvent handler: {}", event.toString())
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: WithdrawalAddedEvent) {
        logger.debug("WithdrawalAddedEvent handler: {}", event.toString())
        balance = event.balance
    }

    companion object : KLogging()
}
