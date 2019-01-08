package pl.altkom.coffee.accounting.api.dto

import java.math.BigDecimal

data class PaymentRequest(val memberId: String, val amount: BigDecimal)

data class WithdrawalRequest(val memberId: String, val amount: BigDecimal)
