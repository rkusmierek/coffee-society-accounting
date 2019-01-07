package pl.altkom.coffee.accounting.domain

import mu.KLogging
import org.axonframework.commandhandling.gateway.CommandGateway
import org.springframework.beans.factory.annotation.Autowired
import java.io.Serializable

abstract class AbstractAccountSaga : Serializable {

    @Autowired
    @Transient
    lateinit var commandGateway: CommandGateway

    companion object : KLogging()
}
