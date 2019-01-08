package pl.altkom.coffee.accounting.api


data class AccountOpenedEvent(
        val memberId: String,
        val balance: Money
)

data class AssetAddedEvent(
        val memberId: String,
        val operationId: OperationId,
        val balance: Money,
        val amount: Money
)

data class LiabilityAddedEvent(
        val memberId: String,
        val operationId: OperationId,
        val balance: Money,
        val amount: Money
)

data class PaymentAddedEvent(
        val memberId: String,
        val balance: Money,
        val amount: Money
)

data class WithdrawalAddedEvent(
        val memberId: String,
        val balance: Money,
        val amount: Money
)

data class TransferRegisteredEvent(
        val operationId: OperationId,
        val fromMemberId: String,
        val toMemberId: String,
        val amount: Money
)
