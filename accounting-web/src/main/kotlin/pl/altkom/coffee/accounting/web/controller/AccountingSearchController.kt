package pl.altkom.coffee.accounting.web.controller

import org.axonframework.queryhandling.QueryGateway
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounting")
class AccountingSearchController(private val queryGateway: QueryGateway) {

}
