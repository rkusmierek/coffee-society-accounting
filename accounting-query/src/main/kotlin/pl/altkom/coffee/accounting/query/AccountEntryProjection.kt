package pl.altkom.coffee.accounting.query

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.api.AssetAddedEvent
import pl.altkom.coffee.accounting.api.LiabilityAddedEvent

@Component
class AccountEntryProjection(private val repository: AccountEntryRepository) {

    @EventHandler
    fun on(event: AccountOpenedEvent) {
        repository.save(AccountEntry(event.memberId, event.balance))
    }

    @EventHandler
    fun on(event: AssetAddedEvent) {
        var accountEntry = repository.findByMemberId(event.memberId)
        accountEntry!!.balance = accountEntry.balance!!.add(event.amount)
        repository.save(accountEntry)
    }

    @EventHandler
    fun on(event: LiabilityAddedEvent) {
        var accountEntry = repository.findByMemberId(event.memberId)
        accountEntry!!.balance = accountEntry.balance!!.subtract(event.amount)
        repository.save(accountEntry)
    }
}
