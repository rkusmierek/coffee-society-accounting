package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import pl.altkom.coffee.accounting.api.BigDecimalWrapper
import pl.altkom.coffee.accounting.api.TransferId
import java.math.BigDecimal

data class OpenAccountCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val balance: BigDecimalWrapper = BigDecimalWrapper(BigDecimal.ZERO)
)

data class SaveAssetCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val transferId: TransferId,
        val amount: BigDecimalWrapper
)

data class SaveLiabilityCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val transferId: TransferId,
        val amount: BigDecimalWrapper
)

data class SavePaymentCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: BigDecimalWrapper
)

data class SaveWithdrawalCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: BigDecimalWrapper
)

data class TransferMoneyCommand(
        @TargetAggregateIdentifier
        val transferId: TransferId,
        val fromMemberId: String,
        val toMemberId: String,
        val amount: BigDecimalWrapper
)
