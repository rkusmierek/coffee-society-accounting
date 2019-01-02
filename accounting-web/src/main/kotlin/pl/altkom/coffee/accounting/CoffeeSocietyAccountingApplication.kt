package pl.altkom.coffee.accounting

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class CoffeeSocietyAccountingApplication

fun main(args: Array<String>) {
    runApplication<CoffeeSocietyAccountingApplication>(*args)
}
