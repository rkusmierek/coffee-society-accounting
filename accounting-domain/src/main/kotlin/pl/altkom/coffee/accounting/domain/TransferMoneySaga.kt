package pl.altkom.coffee.accounting.domain

import org.axonframework.modelling.saga.SagaEventHandler
import org.axonframework.modelling.saga.SagaLifecycle
import org.axonframework.modelling.saga.StartSaga
import org.axonframework.spring.stereotype.Saga
import pl.altkom.coffee.accounting.api.AssetAddedEvent
import pl.altkom.coffee.accounting.api.LiabilityAddedEvent
import pl.altkom.coffee.accounting.api.TransferRegisteredEvent

@Saga
class TransferMoneySaga : AbstractSaga() {

    private var assetAddedEventCompleted = false
    private var liabilityAddedEventCompleted = false

    @StartSaga
    @SagaEventHandler(associationProperty = "operationId")
    fun handle(event: TransferRegisteredEvent) {
        logger.info("Transfer money saga started")

        with(event) {
            commandGateway.send<Void>(SaveLiabilityCommand(fromMemberId, operationId, amount))
            commandGateway.send<Void>(SaveAssetCommand(toMemberId, operationId, amount))
        }
    }

    @SagaEventHandler(associationProperty = "operationId")
    fun handle(assetEvent: AssetAddedEvent) {
        logger.info("Account creation saga finished by AssetAddedEvent")

        assetAddedEventCompleted = true
        if(liabilityAddedEventCompleted)
            SagaLifecycle.end()
    }

    @SagaEventHandler(associationProperty = "operationId")
    fun handle(liabilityEvent: LiabilityAddedEvent) {
        logger.info("Account creation saga finished by LiabilityAddedEvent")

        liabilityAddedEventCompleted = true
        if(assetAddedEventCompleted)
            SagaLifecycle.end()
    }
}
