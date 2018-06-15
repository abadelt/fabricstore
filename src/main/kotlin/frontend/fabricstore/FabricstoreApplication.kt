package frontend.fabricstore

import org.springframework.boot.autoconfigure.SpringBootApplication
import org.springframework.boot.runApplication

@SpringBootApplication
class FabricstoreApplication

fun main(args: Array<String>) {
    runApplication<FabricstoreApplication>(*args)
}
