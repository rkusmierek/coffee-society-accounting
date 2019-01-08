package pl.altkom.coffee.accounting.api


data class AccountOpenedEvent(
        val memberId: String,
        val balance: BigDecimalWrapper
)

data class AssetAddedEvent(
        val memberId: String,
        val transferId: TransferId,
        val balance: BigDecimalWrapper,
        val amount: BigDecimalWrapper
)

data class LiabilityAddedEvent(
        val memberId: String,
        val transferId: TransferId,
        val balance: BigDecimalWrapper,
        val amount: BigDecimalWrapper
)

data class PaymentAddedEvent(
        val memberId: String,
        val balance: BigDecimalWrapper,
        val amount: BigDecimalWrapper
)

data class WithdrawalAddedEvent(
        val memberId: String,
        val balance: BigDecimalWrapper,
        val amount: BigDecimalWrapper
)

data class TransferRegisteredEvent(
        val transferId: TransferId,
        val fromMemberId: String,
        val toMemberId: String,
        val amount: BigDecimalWrapper
)
