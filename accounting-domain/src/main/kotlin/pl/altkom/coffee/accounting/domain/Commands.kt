package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.altkom.coffee.accounting.api.Money
import pl.altkom.coffee.accounting.api.OperationId
import java.math.BigDecimal

data class OpenAccountCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val balance: Money = Money(BigDecimal.ZERO)
)

data class SaveAssetCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val operationId: OperationId,
        val amount: Money
)

data class SaveLiabilityCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val operationId: OperationId,
        val amount: Money
)

data class SavePaymentCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: Money
)

data class SaveWithdrawalCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: Money
)

data class TransferMoneyCommand(
        @TargetAggregateIdentifier
        val operationId: OperationId,
        val fromMemberId: String,
        val toMemberId: String,
        val amount: Money
)
