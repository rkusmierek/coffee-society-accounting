package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.altkom.coffee.accounting.api.TransferId
import java.math.BigDecimal

data class OpenAccountCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val balance: BigDecimal = BigDecimal.ZERO
)

data class SaveAssetCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val transferId: TransferId,
        val amount: BigDecimal
)

data class SaveLiabilityCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val transferId: TransferId,
        val amount: BigDecimal
)

data class SavePaymentCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: BigDecimal
)

data class SaveWithdrawalCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: BigDecimal
)

data class TransferMoneyCommand(
        @TargetAggregateIdentifier
        val transferId: TransferId,
        val fromMemberId: String,
        val toMemberId: String,
        val amount: BigDecimal
)
