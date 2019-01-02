package pl.altkom.coffee.accounting.web.controller

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController

@RestController
@RequestMapping("/api/accounting")
class AccountingController(private val commandGateway: CommandGateway) {


}
