package fund.cyber.pump

import fund.cyber.cassandra.migration.Migratory
import fund.cyber.node.common.Chain
import fund.cyber.node.model.CyberSearchItem


interface Blockchain {
    val chain: Chain
}


interface BlockchainInterface<out T : BlockBundle> : Blockchain {
    fun lastNetworkBlock(): Long
    fun blockBundleByNumber(number: Long): T
}

interface BlockBundle {
    val chain: Chain
    val hash: String
    val parentHash: String
    val number: Long

    fun elementsMap(): Map<Class<CyberSearchItem>, List<CyberSearchItem>> = emptyMap()
}


interface TxPoolInterface<out T: CyberSearchItem> {
    fun onNewTransaction(action: (T)->Unit)
}
