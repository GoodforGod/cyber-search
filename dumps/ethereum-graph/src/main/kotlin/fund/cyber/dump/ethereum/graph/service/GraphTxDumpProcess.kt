package fund.cyber.dump.ethereum.graph.service

import fund.cyber.search.model.ethereum.EthereumTx
import fund.cyber.search.model.events.PumpEvent
import org.apache.kafka.clients.consumer.ConsumerRecord
import org.springframework.kafka.listener.BatchMessageListener

/**
 * "default comment"
 *
 * @author GoodforGod
 * @since 02.05.2018
 */
open class GraphTxDumpProcess()
    : BatchMessageListener<PumpEvent, EthereumTx> {

    override fun onMessage(records: List<ConsumerRecord<PumpEvent, EthereumTx>>) {
        TODO("not implemented") //To change body of created functions use File | Settings | File Templates.
    }
}