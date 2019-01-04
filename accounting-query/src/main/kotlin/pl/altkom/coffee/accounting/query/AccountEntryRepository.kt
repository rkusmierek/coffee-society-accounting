package pl.altkom.coffee.accounting.query

import org.springframework.data.elasticsearch.repository.ElasticsearchCrudRepository

interface AccountEntryRepository : ElasticsearchCrudRepository<AccountEntry, String> {

    fun findByMemberId(memberId: String): AccountEntry?
}
