package pl.altkom.coffee.accounting.query

import org.axonframework.eventhandling.EventHandler
import org.springframework.stereotype.Component
import pl.altkom.coffee.accounting.api.AccountOpenedEvent

@Component
class AccountEntryProjection(private val repository: AccountEntryRepository) {

    @EventHandler
    fun on(event: AccountOpenedEvent) {
        repository.save(AccountEntry(event.memberId, event.balance))
    }
}
