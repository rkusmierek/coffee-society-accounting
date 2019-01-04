package pl.altkom.coffee.accounting.query

import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class AccountQueryHandler(private val repository: AccountEntryRepository) {

    @QueryHandler
    fun getAccountByMemberId(query: AccountByMemberIdQuery) : AccountEntry? {
        return repository.findByMemberId(query.memberId)
    }
}
