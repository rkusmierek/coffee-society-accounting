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
