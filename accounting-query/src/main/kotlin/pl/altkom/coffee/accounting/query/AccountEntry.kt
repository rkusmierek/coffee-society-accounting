package pl.altkom.coffee.accounting.query

import org.springframework.data.annotation.Id
import org.springframework.data.elasticsearch.annotations.Document
import java.math.BigDecimal

@Document(indexName = "account", type = "account")
data class AccountEntry(var memberId: String? = null, var balance: BigDecimal? = null) {
    @Id
    private val accountId: String? = null
}
