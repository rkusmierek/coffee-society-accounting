package pl.altkom.coffee.accounting.query

import org.axonframework.queryhandling.QueryHandler
import org.springframework.stereotype.Component

@Component
class AccountQueryHandler(private val repository: AccountEntryRepository) {

    @QueryHandler
    fun checkIfAccountExistsByMemberId(query: AccountExistsForMemberIdQuery) : Boolean {
        return repository.findByMemberId(query.memberId) != null
    }
}
