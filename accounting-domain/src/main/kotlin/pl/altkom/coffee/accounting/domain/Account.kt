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
import pl.altkom.coffee.accounting.query.AccountExistsForMemberIdQuery
import java.math.BigDecimal
import java.math.MathContext

@Aggregate
class Account {

    @AggregateIdentifier
    lateinit var memberId: String
    lateinit var balance: BigDecimal

    companion object : KLogging()

    constructor()

    @CommandHandler
    constructor(command: OpenAccountCommand, queryGateway: QueryGateway) {
        logger.debug("OpenAccountCommand handler: $command")

        with(command) {
            if(queryGateway.query(AccountExistsForMemberIdQuery(memberId), InstanceResponseType(Boolean::class.java)).get()) {
                throw MemberAlreadyHasAccountException()
            } else {
                AggregateLifecycle.apply(AccountOpenedEvent(memberId, balance))
            }
        }
    }

    @CommandHandler
    fun on(command: SaveAssetCommand) {
        logger.debug("SaveAssetCommand handler: $command")
        if(command.amount < BigDecimal.ZERO)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(AssetAddedEvent(memberId, transferId, balance.add(amount), amount))
        }
    }

    @CommandHandler
    fun on(command: SaveLiabilityCommand) {
        logger.debug("SaveLiabilityCommand handler: $command")
        if(command.amount < BigDecimal.ZERO)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(LiabilityAddedEvent(memberId, transferId, balance.subtract(amount, MathContext(2)), amount))
        }
    }

    @CommandHandler
    fun on(command: SavePaymentCommand) {
        logger.debug("SavePaymentCommand handler: $command")
        if(command.amount < BigDecimal.ZERO)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(PaymentAddedEvent(memberId, balance.add(amount), amount))
        }
    }

    @CommandHandler
    fun on(command: SaveWithdrawalCommand) {
        logger.debug("SaveWithdrawalCommand handler: $command")
        if(command.amount < BigDecimal.ZERO)
            throw IllegalAmountException()

        with(command) {
            AggregateLifecycle.apply(WithdrawalAddedEvent(memberId, balance.subtract(amount, MathContext(2)), amount))
        }
    }

    @EventSourcingHandler
    fun handle(event: AccountOpenedEvent) {
        logger.debug("AccountOpenedEvent handler: $event")
        balance = event.balance
        memberId = event.memberId
    }

    @EventSourcingHandler
    fun handle(event: AssetAddedEvent) {
        logger.debug("AccountOpenedEvent handler: $event")
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: LiabilityAddedEvent) {
        logger.debug("LiabilityAddedEvent handler: $event")
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: PaymentAddedEvent) {
        logger.debug("PaymentAddedEvent handler: $event")
        balance = event.balance
    }

    @EventSourcingHandler
    fun handle(event: WithdrawalAddedEvent) {
        logger.debug("WithdrawalAddedEvent handler: $event")
        balance = event.balance
    }
}
