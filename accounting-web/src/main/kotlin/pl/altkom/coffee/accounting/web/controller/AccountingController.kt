package pl.altkom.coffee.accounting.web.controller

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.altkom.coffee.accounting.api.BigDecimalWrapper
import pl.altkom.coffee.accounting.api.dto.PaymentRequest
import pl.altkom.coffee.accounting.api.dto.WithdrawalRequest
import pl.altkom.coffee.accounting.domain.SavePaymentCommand
import pl.altkom.coffee.accounting.domain.SaveWithdrawalCommand
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/accounting")
class AccountingController(private val commandGateway: CommandGateway) {

    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    @PostMapping("/savePayment")
    fun savePayment(@RequestBody request: PaymentRequest) : Mono<Void> {
        return Mono.fromFuture(commandGateway.send<Void>(
                SavePaymentCommand(request.memberId, BigDecimalWrapper(request.amount))))
    }

    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    @PostMapping("/saveWithdrawal")
    fun saveWithdrawal(@RequestBody request: WithdrawalRequest) : Mono<Void> {
        return Mono.fromFuture(commandGateway.send<Void>(
                SaveWithdrawalCommand(request.memberId, BigDecimalWrapper(request.amount))))
    }
}
