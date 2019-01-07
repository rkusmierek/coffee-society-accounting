package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.command.TargetAggregateIdentifier
import java.math.BigDecimal

data class OpenAccountCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val balance: BigDecimal
) {
    constructor(memberId: String) : this(memberId, BigDecimal.ZERO)
}

data class SaveAssetCommand(
        @TargetAggregateIdentifier
        val memberId: String,
        val amount: BigDecimal
)

data class SaveLiabilityCommand(
        @TargetAggregateIdentifier
        val memberId: String,
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
