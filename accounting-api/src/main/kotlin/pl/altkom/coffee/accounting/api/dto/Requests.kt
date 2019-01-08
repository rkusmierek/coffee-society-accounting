package pl.altkom.coffee.accounting.api.dto

import java.math.BigDecimal

data class PaymentRequest(val memberId: String, val amount: BigDecimal)

data class WithdrawalRequest(val memberId: String, val amount: BigDecimal)

data class TransferMoneyRequest(val memberId: String, val fromMemberId: String, val toMemberId: String, val amount: BigDecimal)
