package pl.altkom.coffee.accounting.web.controller

import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.security.access.prepost.PreAuthorize
import org.springframework.web.bind.annotation.PostMapping
import org.springframework.web.bind.annotation.RequestBody
import org.springframework.web.bind.annotation.RequestMapping
import org.springframework.web.bind.annotation.RestController
import pl.altkom.coffee.accounting.api.Money
import pl.altkom.coffee.accounting.api.OperationId
import pl.altkom.coffee.accounting.api.dto.PaymentRequest
import pl.altkom.coffee.accounting.api.dto.TransferMoneyRequest
import pl.altkom.coffee.accounting.api.dto.WithdrawalRequest
import pl.altkom.coffee.accounting.domain.SavePaymentCommand
import pl.altkom.coffee.accounting.domain.SaveWithdrawalCommand
import pl.altkom.coffee.accounting.domain.TransferMoneyCommand
import reactor.core.publisher.Mono

@RestController
@RequestMapping("/api/accounting")
class AccountingController(private val commandGateway: CommandGateway) {

    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    @PostMapping("/savePayment")
    fun savePayment(@RequestBody request: PaymentRequest) : Mono<Void> {
        return Mono.fromFuture(commandGateway.send<Void>(
                SavePaymentCommand(request.memberId, Money(request.amount))))
    }

    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    @PostMapping("/saveWithdrawal")
    fun saveWithdrawal(@RequestBody request: WithdrawalRequest) : Mono<Void> {
        return Mono.fromFuture(commandGateway.send<Void>(
                SaveWithdrawalCommand(request.memberId, Money(request.amount))))
    }

    @PreAuthorize("hasAuthority('ACCOUNTANT')")
    @PostMapping("/transferMoney")
    fun transferMoney(@RequestBody request: TransferMoneyRequest) : Mono<Void> {
        return Mono.fromFuture(commandGateway.send<Void>(
                TransferMoneyCommand(OperationId(request.operationId, TRANSFER), request.fromMemberId, request.toMemberId, Money(request.amount))))
    }

    companion object {
        const val TRANSFER = "TRANSFER"
    }
}
