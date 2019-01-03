package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.api.MemberCreatedEvent

@Saga
class AccountCreationSaga : AbstractAccountSaga() {

    @StartSaga
    @SagaEventHandler(associationProperty = "memberId")
    fun handle(event: MemberCreatedEvent) {
        with(event) {
            commandGateway.send<Void>(OpenAccountCommand(memberId))
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "memberId")
    fun handle(event: AccountOpenedEvent) {
        logger.info("Account creation saga finished")
    }
}
