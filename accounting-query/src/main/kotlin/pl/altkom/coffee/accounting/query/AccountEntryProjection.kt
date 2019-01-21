package pl.altkom.coffee.accounting.query

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import pl.altkom.coffee.accounting.api.*
import java.math.BigDecimal

@Component
class AccountEntryProjection(private val repository: AccountEntryRepository) {

    @EventHandler
    fun on(event: AccountOpenedEvent) {
        repository.save(AccountEntry(event.memberId, BigDecimal.ZERO))
    }

    @EventHandler
    fun on(event: AssetAddedEvent) {
        with(event) {
            updateAccountEntry(memberId, balance.value)
        }
    }

    @EventHandler
    fun on(event: LiabilityAddedEvent) {
        with(event) {
            updateAccountEntry(memberId, balance.value)
        }
    }

    @EventHandler
    fun on(event: PaymentAddedEvent) {
        with(event) {
            updateAccountEntry(memberId, balance.value)
        }
    }

    @EventHandler
    fun on(event: WithdrawalAddedEvent) {
        with(event) {
            updateAccountEntry(memberId, balance.value)
        }
    }

    private fun updateAccountEntry(memberId: String, balance: BigDecimal) {
        val accountEntry = repository.findByMemberId(memberId)
        accountEntry!!.balance = balance

        repository.save(accountEntry)
    }
}
