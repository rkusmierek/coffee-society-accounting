package pl.altkom.coffee.accounting.api

import java.math.BigDecimal

data class AccountOpenedEvent(
        val memberId: String,
        val balance: BigDecimal
)
