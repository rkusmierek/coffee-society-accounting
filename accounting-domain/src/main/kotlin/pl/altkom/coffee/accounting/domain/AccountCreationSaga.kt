package pl.altkom.coffee.accounting.domain

import org.axonframework.messaging.responsetypes.InstanceResponseType
import org.axonframework.modelling.saga.EndSaga
import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import pl.altkom.coffee.accounting.api.AccountOpenedEvent
import pl.altkom.coffee.accounting.query.AccountByMemberIdQuery
import pl.altkom.coffee.accounting.query.AccountEntry
import pl.altkom.coffee.members.api.MemberCreatedEvent

@Saga
class AccountCreationSaga : AbstractAccountSaga() {

    @StartSaga
    @SagaEventHandler(associationProperty = "memberId")
    fun handle(event: MemberCreatedEvent) {
        logger.info("Account creation saga started")
        with(event) {
            if(queryGateway.query(AccountByMemberIdQuery(memberId), InstanceResponseType(AccountEntry::class.java)).get() == null) {
                commandGateway.send<Void>(OpenAccountCommand(memberId))
            } else {
                throw MemberAlreadyHasAccountException()
            }
        }
    }

    @EndSaga
    @SagaEventHandler(associationProperty = "memberId")
    fun handle(event: AccountOpenedEvent) {
        logger.info("Account creation saga finished")
        SagaLifecycle.end()
    }
}
