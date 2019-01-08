package pl.altkom.coffee.accounting.api

import org.axonframework.common.IdentifierFactory
import java.io.Serializable
import java.math.BigDecimal
import java.math.MathContext

data class TransferId(val identifier: String = IdentifierFactory.getInstance().generateIdentifier()) : Serializable {

    companion object {
        private const val serialVersionUID = -5267104328616955617L
    }
}

data class BigDecimalWrapper(val value: BigDecimal) {

    constructor(valueString: String) : this(BigDecimal(valueString))

    private val mc = MathContext(2)

    fun add(augend: BigDecimalWrapper) : BigDecimalWrapper {
        return BigDecimalWrapper(value.add(augend.value))
    }

    fun subtract(subtrahend: BigDecimalWrapper) : BigDecimalWrapper {
        return BigDecimalWrapper(value.subtract(subtrahend.value, mc))
    }

    fun negate() : BigDecimalWrapper {
        return BigDecimalWrapper(value.negate(mc))
    }
}
