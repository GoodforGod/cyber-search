package fund.cyber.dump.ethereum.graph.service

import fund.cyber.search.model.ethereum.EthereumContractAddress
import fund.cyber.search.model.events.PumpEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.BatchMessageListener

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 02.05.2018
 */
open class GraphContractDumpProcess()
    : BatchMessageListener<PumpEvent, EthereumContractAddress> {

    override fun onMessage(records: List<ConsumerRecord<PumpEvent, EthereumContractAddress>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}