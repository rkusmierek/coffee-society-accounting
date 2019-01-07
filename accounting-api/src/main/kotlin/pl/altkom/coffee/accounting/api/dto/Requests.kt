package pl.altkom.coffee.accounting.api.dto

data class PaymentRequest(
        val memberId: String, val amount: String
)

data class WithdrawalRequest(
        val memberId: String, val amount: String
)
