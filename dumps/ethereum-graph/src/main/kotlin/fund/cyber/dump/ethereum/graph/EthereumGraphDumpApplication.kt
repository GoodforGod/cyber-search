package fund.cyber.dump.ethereum.graph

import org.springframework.boot.SpringApplication
import org.springframework.boot.autoconfigure.SpringBootApplication

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 30.04.2018
 */
@SpringBootApplication
open class EthereumGraphDumpApplication {

    companion object {

        @JvmStatic
        fun main(args: Array<String>) {
            SpringApplication(EthereumGraphDumpApplication::class.java).run(*args)
        }
    }
}