package pl.altkom.coffee.accounting.api

import java.math.BigDecimal

data class AccountOpenedEvent(
        val memberId: String,
        val balance: BigDecimal
)

data class AssetAddedEvent(
        val memberId: String,
        val balance: BigDecimal,
        val amount: BigDecimal
)

data class LiabilityAddedEvent(
        val memberId: String,
        val balance: BigDecimal,
        val amount: BigDecimal
)

data class PaymentAddedEvent(
        val memberId: String,
        val balance: BigDecimal,
        val amount: BigDecimal
)

data class WithdrawalAddedEvent(
        val memberId: String,
        val balance: BigDecimal,
        val amount: BigDecimal
)
