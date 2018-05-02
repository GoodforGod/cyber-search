package fund.cyber.dump.ethereum.graph.config

import com.arangodb.ArangoDB
import com.arangodb.springframework.config.AbstractArangoConfiguration

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 02.05.2018
 */
open class ArangoConfig : AbstractArangoConfiguration() {

    override fun arango(): ArangoDB.Builder {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return ArangoDB.Builder()
    }

    override fun database(): String {
        //TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
        return "";
    }
}